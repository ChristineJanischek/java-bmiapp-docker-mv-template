from __future__ import annotations

from pathlib import Path
import re

from grader.models import Rule, RuleResult


def _find_java_files(source_root: Path) -> list[Path]:
    return sorted(source_root.rglob("*.java"))


def _read_text(file_path: Path) -> str:
    return file_path.read_text(encoding="utf-8", errors="ignore")


def _rule_file_exists(source_root: Path, rule: Rule) -> RuleResult:
    pattern = rule.config.get("glob")
    if not pattern:
        return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: glob")

    matches = list(source_root.rglob(pattern))
    if matches:
        found = ", ".join(str(path.relative_to(source_root)) for path in matches[:3])
        return RuleResult(rule=rule, achieved_points=rule.points, passed=True, note=f"Gefunden: {found}")

    return RuleResult(rule=rule, achieved_points=0.0, passed=False, note=f"Nicht gefunden: {pattern}")


def _rule_contains_regex(source_root: Path, java_files: list[Path], rule: Rule) -> RuleResult:
    pattern = rule.config.get("regex")
    if not pattern:
        return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: regex")

    compiled = re.compile(pattern, re.MULTILINE)
    target_glob = rule.config.get("target_glob")

    files = java_files
    if target_glob:
        files = [path for path in java_files if path.match(f"**/{target_glob}")]

    for file_path in files:
        text = _read_text(file_path)
        if compiled.search(text):
            rel = file_path.relative_to(source_root)
            return RuleResult(rule=rule, achieved_points=rule.points, passed=True, note=f"Treffer in {rel}")

    return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Kein Treffer im Quellcode")


def _rule_not_contains_regex(source_root: Path, java_files: list[Path], rule: Rule) -> RuleResult:
    pattern = rule.config.get("regex")
    if not pattern:
        return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: regex")

    compiled = re.compile(pattern, re.MULTILINE)
    for file_path in java_files:
        text = _read_text(file_path)
        if compiled.search(text):
            rel = file_path.relative_to(source_root)
            return RuleResult(rule=rule, achieved_points=0.0, passed=False, note=f"Unerwuenschtes Muster in {rel}")

    return RuleResult(rule=rule, achieved_points=rule.points, passed=True, note="Kein unerwuenschtes Muster gefunden")


def _rule_min_occurrences_regex(source_root: Path, java_files: list[Path], rule: Rule) -> RuleResult:
    pattern = rule.config.get("regex")
    min_count = int(rule.config.get("min_count", 1))
    if not pattern:
        return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: regex")

    compiled = re.compile(pattern, re.MULTILINE)
    count = 0
    for file_path in java_files:
        text = _read_text(file_path)
        count += len(compiled.findall(text))

    passed = count >= min_count
    points = rule.points if passed else 0.0
    note = f"Treffer gesamt: {count}, benoetigt: {min_count}"
    return RuleResult(rule=rule, achieved_points=points, passed=passed, note=note)


def _rule_min_files_glob(source_root: Path, rule: Rule) -> RuleResult:
    pattern = rule.config.get("glob")
    min_count = int(rule.config.get("min_count", 1))
    if not pattern:
        return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: glob")

    matches = list(source_root.rglob(pattern))
    count = len(matches)
    passed = count >= min_count
    points = rule.points if passed else 0.0
    note = f"Dateien gefunden: {count}, benoetigt: {min_count} ({pattern})"
    return RuleResult(rule=rule, achieved_points=points, passed=passed, note=note)


def _evaluate_rule(source_root: Path, java_files: list[Path], rule: Rule) -> RuleResult:
    if rule.kind == "file_exists":
        return _rule_file_exists(source_root, rule)
    if rule.kind == "contains_regex":
        return _rule_contains_regex(source_root, java_files, rule)
    if rule.kind == "not_contains_regex":
        return _rule_not_contains_regex(source_root, java_files, rule)
    if rule.kind == "min_occurrences_regex":
        return _rule_min_occurrences_regex(source_root, java_files, rule)
    if rule.kind == "min_files_glob":
        return _rule_min_files_glob(source_root, rule)
    if rule.kind == "any_of":
        option_rules = rule.config.get("rules", [])
        if not isinstance(option_rules, list) or not option_rules:
            return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: any_of.rules")

        option_results: list[RuleResult] = []
        for idx, option in enumerate(option_rules, start=1):
            option_rule = Rule(
                id=f"{rule.id}.option{idx}",
                title=option.get("title", f"Option {idx}"),
                description=option.get("description", ""),
                points=rule.points,
                kind=option.get("kind", ""),
                config=option.get("config", {}),
            )
            option_results.append(_evaluate_rule(source_root, java_files, option_rule))

        for idx, option_result in enumerate(option_results, start=1):
            if option_result.passed:
                return RuleResult(
                    rule=rule,
                    achieved_points=rule.points,
                    passed=True,
                    note=f"Option {idx} erfuellt: {option_result.note}",
                )

        note_parts = [f"Option {idx} nicht erfuellt: {result.note}" for idx, result in enumerate(option_results, start=1)]
        return RuleResult(
            rule=rule,
            achieved_points=0.0,
            passed=False,
            note=" | ".join(note_parts),
        )

    if rule.kind == "all_of":
        sub_rules = rule.config.get("rules", [])
        if not isinstance(sub_rules, list) or not sub_rules:
            return RuleResult(rule=rule, achieved_points=0.0, passed=False, note="Fehlende Konfiguration: all_of.rules")

        sub_results: list[RuleResult] = []
        for idx, sub in enumerate(sub_rules, start=1):
            sub_rule = Rule(
                id=f"{rule.id}.sub{idx}",
                title=sub.get("title", f"Teilregel {idx}"),
                description=sub.get("description", ""),
                points=rule.points,
                kind=sub.get("kind", ""),
                config=sub.get("config", {}),
            )
            sub_results.append(_evaluate_rule(source_root, java_files, sub_rule))

        failed = [result for result in sub_results if not result.passed]
        if failed:
            note_parts = [f"Teilregel nicht erfuellt: {result.note}" for result in failed]
            return RuleResult(
                rule=rule,
                achieved_points=0.0,
                passed=False,
                note=" | ".join(note_parts),
            )

        return RuleResult(
            rule=rule,
            achieved_points=rule.points,
            passed=True,
            note="Alle Teilregeln erfuellt",
        )

    return RuleResult(
        rule=rule,
        achieved_points=0.0,
        passed=False,
        note=f"Unbekannter Regeltyp: {rule.kind}",
    )


def evaluate_rules(source_root: Path, rules: list[Rule]) -> list[RuleResult]:
    java_files = _find_java_files(source_root)
    results: list[RuleResult] = []

    for rule in rules:
        result = _evaluate_rule(source_root, java_files, rule)
        results.append(result)

    return results
