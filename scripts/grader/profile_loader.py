from __future__ import annotations

import json
from pathlib import Path

from grader.models import GradeScale, GradingProfile, Rule


def load_profile(profile_path: Path) -> GradingProfile:
    raw = json.loads(profile_path.read_text(encoding="utf-8"))

    rules = [
        Rule(
            id=item["id"],
            title=item["title"],
            description=item.get("description", ""),
            points=float(item["points"]),
            kind=item["kind"],
            config=item.get("config", {}),
        )
        for item in raw["rules"]
    ]

    grade_scale = GradeScale(
        grade_best=float(raw.get("grade_scale", {}).get("best", 1.0)),
        grade_worst=float(raw.get("grade_scale", {}).get("worst", 6.0)),
    )

    return GradingProfile(
        profile_name=raw["profile_name"],
        project_type=raw.get("project_type", "generic"),
        expected_root=raw.get("expected_root", "src"),
        grade_scale=grade_scale,
        rules=rules,
    )
