from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path


@dataclass(frozen=True)
class Rule:
    id: str
    title: str
    description: str
    points: float
    kind: str
    config: dict


@dataclass(frozen=True)
class RuleResult:
    rule: Rule
    achieved_points: float
    passed: bool
    note: str


@dataclass(frozen=True)
class GradeScale:
    grade_best: float
    grade_worst: float


@dataclass(frozen=True)
class GradingProfile:
    profile_name: str
    project_type: str
    expected_root: str
    grade_scale: GradeScale
    rules: list[Rule]


@dataclass(frozen=True)
class GradingOutcome:
    profile: GradingProfile
    zip_path: Path
    source_root: Path
    results: list[RuleResult]
    total_points: float
    max_points: float
    grade: float


@dataclass(frozen=True)
class RunConfig:
    zip_path: Path
    profile_path: Path
    rubric_docx_path: Path
    output_docx_path: Path
    output_md_path: Path
    output_html_path: Path
    student_name: str
    teacher_note: str | None
