# Parallele Entwicklung und Branch Protection

## Ziel
Diese Konfiguration stellt sicher, dass:

- die Systementwicklung parallel zu Arbeiten in `materials/tests` erfolgen kann,
- Änderungen in `main`, `feat/testsdev` und `chore/todos-next-block` nur per Pull Request laufen,
- jede Änderung vor dem Merge durch Repository-Owner-Freigabe abgesichert wird,
- das spätere Zusammenführen der Zweige sauber und nachvollziehbar möglich bleibt.

## Vorgesehene Branches

- `main`: Stabiler Integrationszweig.
- `feat/testsdev`: Weiterentwicklung der Test-Inhalte (insbesondere unter `materials/tests`).
- `chore/todos-next-block`: Vorbereitende Systemarbeiten, Refactorings und To-do-Block.

## Workflow fuer parallele Entwicklung

1. Niemals direkt auf geschuetzte Branches committen/pushen.
2. Fuer jede Aufgabe einen kurzen Arbeitsbranch von einem geschuetzten Zielbranch erstellen:
   - Beispiel: `work/testsdev-<ticket>` von `feat/testsdev`
   - Beispiel: `work/system-<ticket>` von `chore/todos-next-block`
3. Arbeitsbranch pushen und Pull Request in den jeweiligen Zielbranch eroefnen.
4. Owner-Review abwarten und erst danach mergen.
5. Regelmaessig vom Zielbranch rebasen oder mergen, um kleine Konflikte frueh zu loesen.

## Merge-Strategie fuer spaeteres Zusammenfuehren

1. `feat/testsdev` und `chore/todos-next-block` regelmaessig mit `main` synchron halten.
2. Abschlussintegration ueber Pull Requests in dieser Reihenfolge:
   1. `feat/testsdev` -> `main`
   2. `chore/todos-next-block` -> `main`
3. Vor jedem Integrations-PR:
   - Build und relevante Tests lokal laufen lassen.
   - Konflikte lokal aufloesen und erneut testen.
4. Merge als `Squash and merge` oder `Rebase and merge` verwenden, damit die Historie linear und pruefbar bleibt.

## Verbindliche Schutzregeln (Best Practice)

Diese Regeln gelten fuer alle drei Branches: `main`, `feat/testsdev`, `chore/todos-next-block`.

- Require a pull request before merging: `true`
- Required approving reviews: `1`
- Require code owner reviews: `true`
- Dismiss stale approvals: `true`
- Require approval of most recent push: `true`
- Require conversation resolution: `true`
- Require linear history: `true`
- Allow force pushes: `false`
- Allow deletions: `false`
- Enforce for administrators: `true`

## Technische Voraussetzung im Repository

Die Datei `.github/CODEOWNERS` ist hinterlegt, damit Owner-Reviews erzwungen werden koennen, wenn Branch Protection aktiviert ist.

Aktueller Eintrag:

```text
* @ChristineJanischek
```

## Automatisiertes Setup der Branch Protection

Es gibt ein idempotentes Skript:

- `scripts/setup_branch_protection.sh`

Ausfuehrung:

```bash
export GITHUB_TOKEN=<token-mit-repo-admin-rechten>
./scripts/setup_branch_protection.sh ChristineJanischek java-bmiapp-docker-mv-template
```

Hinweis: Ein Token ohne Admin-Berechtigung auf Repository-Ebene kann Branch Protection nicht setzen (GitHub API liefert dann HTTP 403).

## Pruefliste nach Einrichtung

1. Direkter Push auf `main` wird abgelehnt.
2. Direkter Push auf `feat/testsdev` wird abgelehnt.
3. Direkter Push auf `chore/todos-next-block` wird abgelehnt.
4. PR ohne Owner-Approval kann nicht gemergt werden.
5. Nach neuem Commit auf einen PR wird erneute Freigabe verlangt.

## Dokumentations- und Audit-Hinweise

- Jede funktionale Aenderung erfolgt ueber PR mit klarer Beschreibung.
- Jeder PR enthaelt: Ziel, Risiko, Testnachweis, Rueckfallstrategie.
- Review-Kommentare bleiben im PR als nachvollziehbarer Entscheidungsverlauf erhalten.
