from __future__ import annotations

from pathlib import Path
import zipfile


def _safe_extract_path(target_dir: Path, member_name: str) -> Path:
    resolved = (target_dir / member_name).resolve()
    if target_dir.resolve() not in resolved.parents and resolved != target_dir.resolve():
        raise ValueError(f"Unsafe path in zip entry: {member_name}")
    return resolved


def extract_zip_secure(zip_path: Path, target_dir: Path) -> None:
    with zipfile.ZipFile(zip_path) as archive:
        for member in archive.infolist():
            member_path = _safe_extract_path(target_dir, member.filename)
            if member.is_dir():
                member_path.mkdir(parents=True, exist_ok=True)
                continue
            member_path.parent.mkdir(parents=True, exist_ok=True)
            with archive.open(member, "r") as src, member_path.open("wb") as dst:
                dst.write(src.read())
