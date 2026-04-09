from __future__ import annotations

from datetime import datetime

from docx import Document
from docx.shared import Pt

from grader.models import GradingOutcome


def _add_heading(document: Document, text: str) -> None:
    heading = document.add_paragraph()
    run = heading.add_run(text)
    run.bold = True
    run.font.size = Pt(14)


def _build_action_todos(outcome: GradingOutcome) -> list[str]:
    todos: list[str] = []
    failed = [result for result in outcome.results if not result.passed]

    for result in failed:
        rule_id = result.rule.id
        if rule_id.startswith("F"):
            todos.append("Formales ueberarbeiten: Syntax pruefen, Architektur klarer strukturieren und Testbarkeit sicherstellen (main-Methode oder Testklasse).")
        elif rule_id.startswith("FU"):
            todos.append("Funktionalitaet nachbessern: GUI-Interaktionen, Verzweigungen und Schleifen mit realistischen Testfaellen pruefen.")
        elif rule_id.startswith("D"):
            todos.append("Dokumentation ergaenzen: Klassenkommentare und nachvollziehbare Inline-Hinweise zu zentralen Logikschritten ergaenzen.")
        elif rule_id.startswith("K"):
            todos.append("Kapselung verbessern: Attribute konsequent private halten und kontrollierte Zugriffsmethoden anbieten.")
        elif rule_id.startswith("T"):
            todos.append("Testumgebung erweitern: Main.java als Testtreiber ausbauen oder Test.java/*Test.java mit vergleichbaren Pruefungen anlegen.")
        elif rule_id.startswith("I"):
            todos.append("Projektstruktur staerken: MVC-Rollen klarer trennen und fachliche Klassen sauber modularisieren.")

    deduped: list[str] = []
    for item in todos:
        if item not in deduped:
            deduped.append(item)

    if not deduped:
        deduped = [
            "Qualitaet halten: bestehende Struktur beibehalten und nur gezielte Codeverbesserungen mit kleinen Commits vornehmen.",
            "Zusatztests ergaenzen: mindestens einen weiteren Testfall je Kernfunktion dokumentieren und nachvollziehbar ausfuehren.",
        ]

    deduped.append("Deadline Juni-Abgabe: Alle offenen Punkte bis spaetestens 30.06.2026 abschliessen und final pruefen.")
    return deduped


def write_report_docx(
    source_docx_path,
    output_docx_path,
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
) -> None:
    document = Document(str(source_docx_path))

    document.add_page_break()
    _add_heading(document, "Automatischer Bewertungsreport")

    meta = document.add_paragraph()
    meta.add_run("Datum: ").bold = True
    meta.add_run(datetime.now().strftime("%Y-%m-%d %H:%M"))
    meta.add_run("\nSchueler/in: ").bold = True
    meta.add_run(student_name)
    meta.add_run("\nProfil: ").bold = True
    meta.add_run(outcome.profile.profile_name)

    score = document.add_paragraph()
    score.add_run("Erreichte Punkte: ").bold = True
    score.add_run(f"{outcome.total_points:.2f} / {outcome.max_points:.2f}")
    score.add_run("\nNote (linear): ").bold = True
    score.add_run(str(outcome.grade))

    _add_heading(document, "Regelbasierte Einzelbewertung")
    for result in outcome.results:
        status = "OK" if result.passed else "FEHLT"
        p = document.add_paragraph()
        p.add_run(f"[{status}] {result.rule.title} ").bold = True
        p.add_run(f"({result.achieved_points:.2f}/{result.rule.points:.2f} Punkte)")

        if result.rule.description:
            info = document.add_paragraph()
            info.paragraph_format.left_indent = Pt(16)
            info.add_run("Kriterium: ").bold = True
            info.add_run(result.rule.description)

        note = document.add_paragraph()
        note.paragraph_format.left_indent = Pt(16)
        note.add_run("Anmerkung: ").bold = True
        note.add_run(result.note)

    _add_heading(document, "Handlungsempfehlung (Juni-Abgabe)")
    for todo in _build_action_todos(outcome):
        p = document.add_paragraph()
        p.add_run("[ ] ").bold = True
        p.add_run(todo)

    if teacher_note:
        _add_heading(document, "Lehrkraft-Notiz")
        document.add_paragraph(teacher_note)

    document.save(str(output_docx_path))
