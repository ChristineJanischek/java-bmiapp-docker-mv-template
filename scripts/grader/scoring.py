from __future__ import annotations

from grader.models import GradeScale


def calculate_points(results) -> tuple[float, float]:
    total = sum(item.achieved_points for item in results)
    maximum = sum(item.rule.points for item in results)
    return total, maximum


def calculate_linear_grade(total_points: float, max_points: float, scale: GradeScale) -> float:
    if max_points <= 0:
        return scale.grade_worst

    ratio = min(max(total_points / max_points, 0.0), 1.0)
    grade = scale.grade_best + (1.0 - ratio) * (scale.grade_worst - scale.grade_best)
    return round(grade, 2)
