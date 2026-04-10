# Tests - Struktur und Naming

Dieses Verzeichnis enthaelt thematische Kurztests fuer den Unterricht.

## Zielstruktur

- Ein Unterverzeichnis pro Thema (kleingeschrieben, mit Unterstrichen).
- Einheitliche Dateimuster pro Thema:
  - `Kurztest_<Thema>_<Nr>.md`
  - `Kurztest_<Thema>_<Nr>_LOESUNG.md`
- Optional je Thema:
  - `Syntaxhilfe_<Thema>.md`

## Aktuelle Namensregeln

- Verzeichnisnamen: fachlich klar, keine Abkuerzungen wenn vermeidbar.
- Dateinamen: sprechend und konsistent zu den Verzeichnisnamen.
- Loesungen immer mit Suffix `_LOESUNG.md`.

## Durchgefuehrte sanfte Migration (April 2026)

1. `materials/tests/sichere_dv` -> `materials/tests/sichere_datenverarbeitung`
2. `materials/tests/sichere_db` -> `materials/tests/sichere_db_anbindung`
3. `Kurztest_Sichere_DV_1.md` -> `Kurztest_Sichere_Datenverarbeitung_1.md`
4. `Kurztest_Sichere_DV_1_LOESUNG.md` -> `Kurztest_Sichere_Datenverarbeitung_1_LOESUNG.md`
5. `Kurztest_Sichere_DB_1.md` -> `Kurztest_Sichere_DB_Anbindung_1.md`
6. `Kurztest_Sichere_DB_1_LOESUNG.md` -> `Kurztest_Sichere_DB_Anbindung_1_LOESUNG.md`

## Nächste sinnvolle Bereinigung

- Tippfehler im Themenordner korrigieren:
  - `komposittion_interface` -> `komposition_interface`

Diese Umbenennung sollte in einem separaten kleinen Schritt erfolgen, damit alle Verweise sauber mitgezogen werden koennen.

## Aktuelle Testserien (Stand April 2026)

- `sichere_datenverarbeitung`: Kurztest 1 bis 3, jeweils mit `_LOESUNG`
- `sichere_db_anbindung`: Kurztest 1 bis 3, jeweils mit `_LOESUNG`

Zusaetzliche Syntaxhilfen:

- `sichere_datenverarbeitung/Syntaxhilfe_Sichere_Datenverarbeitung.md`
- `sichere_db_anbindung/Syntaxhilfe_Sichere_DB_Anbindung.md`
