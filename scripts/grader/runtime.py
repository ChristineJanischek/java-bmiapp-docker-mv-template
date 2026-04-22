from __future__ import annotations

import importlib.util
import subprocess
import sys


_REQUIRED_MODULE_TO_PACKAGE = {
    "docx": "python-docx",
}


def ensure_runtime_requirements() -> None:
    missing_packages: list[str] = []
    for module_name, package_name in _REQUIRED_MODULE_TO_PACKAGE.items():
        if importlib.util.find_spec(module_name) is None:
            missing_packages.append(package_name)

    if not missing_packages:
        return

    subprocess.check_call(
        [sys.executable, "-m", "pip", "install", *missing_packages],
    )
