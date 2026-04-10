from __future__ import annotations

from dataclasses import dataclass

from grader.models import GradingOutcome


@dataclass(frozen=True)
class ActionPlan:
    focus_todos: list[str]
    extension_todos: list[str]
    timeline_todos: list[str]


_EXTENSION_CATALOG = {
    "vererbung": "Vererbung: Fuehre eine kleine Basisklasse (z. B. Aktivitaet) und mindestens eine abgeleitete Klasse ein; zeige Ueberschreiben einer Methode.",
    "dateispeicher": "Dateispeicher: Speichere und lade die wichtigsten Projektdaten als JSON-Datei (einlesen, validieren, wieder anzeigen).",
    "datenbank": "Datenbank: Ergaenze eine kleine SQLite-Anbindung mit einer Tabelle und den Operationen Speichern + Laden fuer Kerndaten.",
    "datensicherheit": "Datensicherheit: Ergaenze striktere Eingabevalidierung plus saubere Fehlerbehandlung und vermeide unsichere Standardwerte.",
}


def _failed_prefixes(outcome: GradingOutcome) -> set[str]:
    prefixes: set[str] = set()
    for result in outcome.results:
        if result.passed:
            continue
        prefix = ""
        for ch in result.rule.id:
            if ch.isalpha():
                prefix += ch
            else:
                break
        if prefix:
            prefixes.add(prefix)
    return prefixes


def _focus_todos(prefixes: set[str]) -> list[str]:
    todos: list[str] = []
    if "F" in prefixes:
        todos.append("Formales priorisieren: Syntax checken, Architektur sichtbar machen (klare Rollen), Testbarkeit ueber main/Testklasse absichern.")
    if "FU" in prefixes:
        todos.append("Funktionalitaet stabilisieren: GUI-Events, Verzweigungen und Schleifen mit 3 realistischen Testfaellen absichern.")
    if "D" in prefixes:
        todos.append("Dokumentation nachziehen: pro Kernklasse 1 Kurzkommentar + pro zentraler Methode 1 Zweckbeschreibung.")
    if "K" in prefixes:
        todos.append("Kapselung verbessern: Felder private halten und kontrollierte Getter/Methoden fuer Zugriff einsetzen.")
    if "T" in prefixes:
        todos.append("Testumgebung ausbauen: Main.java als Testtreiber oder Test.java/*Test.java mit vergleichbaren Pruefungen liefern.")
    if "I" in prefixes:
        todos.append("Projektstruktur schaerfen: MVC-Rollen klar trennen und fachliche Klassen konsistent aufteilen.")

    if not todos:
        todos.append("Qualitaet halten: nur gezielte Verbesserungen mit kleinen, nachvollziehbaren Commits vornehmen.")

    return todos


def _pick_two_extensions(prefixes: set[str], student_name: str) -> list[str]:
    desired: list[str] = []

    # Erst fachlich passend nach Defiziten priorisieren.
    if "I" in prefixes or "K" in prefixes:
        desired.append("vererbung")
    if "F" in prefixes or "T" in prefixes:
        desired.append("datensicherheit")
    if "D" in prefixes:
        desired.append("dateispeicher")
    if "FU" in prefixes:
        desired.append("datenbank")

    # Rest mit leichter Personalisierung (stabil pro Name) auffuellen.
    all_keys = ["vererbung", "dateispeicher", "datenbank", "datensicherheit"]
    name_score = sum(ord(ch) for ch in student_name.lower())
    rotated = all_keys[name_score % len(all_keys):] + all_keys[: name_score % len(all_keys)]

    combined = desired + rotated
    unique: list[str] = []
    for key in combined:
        if key not in unique:
            unique.append(key)
        if len(unique) == 2:
            break

    return [_EXTENSION_CATALOG[key] for key in unique]


def _timeline_todos() -> list[str]:
    return [
        "Bis 24.04.2026: Scope festziehen, 2 konkrete Erweiterungen auswaehlen, technische Skizze (max. 1 Seite) erstellen.",
        "Bis 15.05.2026: Erweiterung 1 komplett umsetzen und kurz testen (Happy Path + 2 Randfaelle).",
        "Bis 29.05.2026: Erweiterung 2 umsetzen, Dokumentation finalisieren, offene Bugs schliessen.",
        "Bis 03.06.2026: Finale Abgabeversion erstellen und 5-min Projektverteidigung vorbereiten (Ablauf, Entscheidungen, Demo).",
    ]


def generate_personal_action_plan(outcome: GradingOutcome, student_name: str) -> ActionPlan:
    prefixes = _failed_prefixes(outcome)
    return ActionPlan(
        focus_todos=_focus_todos(prefixes),
        extension_todos=_pick_two_extensions(prefixes, student_name),
        timeline_todos=_timeline_todos(),
    )
