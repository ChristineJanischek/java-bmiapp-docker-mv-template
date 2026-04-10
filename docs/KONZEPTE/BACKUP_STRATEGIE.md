# Backup-Strategie und Meilensteine

## Ziel

Diese Strategie definiert, wie Aenderungen im Repository sicher und nachvollziehbar gesichert werden.

## Grundprinzip

- Branch ist Arbeitsstand.
- Push auf `origin` ist Mindest-Sicherung.
- Annotierte Tags markieren Meilensteine.
- Optionales Release-Archiv dient als externe Wiederherstellungsstufe.

## Backup-Ebenen

### Ebene 1: Laufende Sicherung (Pflicht)

Nach jedem abgeschlossenen Arbeitspaket:

1. lokale Aenderungen committen
2. auf den Remote-Branch pushen

Damit existiert stets mindestens eine aktuelle Sicherung auf GitHub.

### Ebene 2: Meilenstein-Sicherung (empfohlen)

Bei inhaltlichen Meilensteinen (z. B. neue Modulversion, neue Bewertungsrubrik, stabile Batch-Pipeline):

1. annotierten Tag erstellen
2. Tag auf `origin` pushen

Beispiel:

```bash
git tag -a v6.0-docs-baseline -m "Milestone v6.0: Bewertungsmodul-Doku eingefuehrt"
git push origin v6.0-docs-baseline
```

Vorteil: Der Stand ist eindeutig referenzierbar und leicht wiederherstellbar.

### Ebene 3: Release-Sicherung (optional)

Bei Abgabe- oder Freigabestaenden:

1. GitHub Release aus Tag erstellen
2. optionales Archiv der zentralen Artefakte ablegen (z. B. Berichte, Profile, Anleitung)

## Wann einen Meilenstein-Tag setzen?

Ein Tag ist sinnvoll, wenn mindestens einer der Punkte zutrifft:

- didaktisch relevanter Stand erreicht
- neues Modul oder neue Architektur eingefuehrt
- Bewertungsprofil fachlich kalibriert
- dokumentierter Stand wird in Unterricht oder Bewertung eingesetzt
- vor groesserem Umbau als Ruecksprungpunkt

## Namenskonvention fuer Tags

Empfehlung:

- `v<major>.<minor>` fuer stabile fachliche Stufen
- optionaler Suffix fuer Kontext, z. B. `v6.0-docs-baseline`

Konventionen sollten im Team konsistent verwendet werden.

## Wiederherstellung

Stand aus einem Tag lokal auschecken:

```bash
git fetch --tags
git checkout v6.0-docs-baseline
```

Neuen Branch aus Tag erzeugen:

```bash
git checkout -b hotfix/v6.0-docs-baseline v6.0-docs-baseline
```

## Kurz-Checkliste

- [ ] Arbeitspaket abgeschlossen
- [ ] Commit erstellt
- [ ] Branch gepusht
- [ ] Meilenstein-Tag gesetzt (wenn relevant)
- [ ] Tag gepusht
