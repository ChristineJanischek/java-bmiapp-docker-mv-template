## Ziel dieser Änderung
<!-- Was wird hier gelöst oder hinzugefügt? -->


## Art der Änderung
- [ ] Neues Feature
- [ ] Bugfix
- [ ] Refactoring / Strukturverbesserung
- [ ] Dokumentation
- [ ] Sonstiges: ___

## Betroffene Bereiche
<!-- Welche Dateien, Klassen, Branches, Verzeichnisse sind relevant? -->


## Risiko
- [ ] Kein Risiko – reines Hinzufügen ohne bestehende Änderungen
- [ ] Geringes Risiko – kleine Anpassungen an vorhandenem Code
- [ ] Mittleres Risiko – bestehende Logik geändert
- [ ] Hohes Risiko – kritische Komponenten betroffen

<!-- Kurze Begründung: -->


## Tests
- [ ] Manuelle Tests durchgeführt
- [ ] Unit Tests ergänzt / angepasst
- [ ] Kein Test notwendig (Begründung: ___)

<!-- Was wurde wie getestet? -->


## Rollback-Strategie
<!-- Was tun, falls nach dem Merge ein Problem auftritt? -->
- [ ] Einfach revertierbar per `git revert`
- [ ] Manueller Rollback notwendig – Beschreibung:


## Checkliste vor Review-Anfrage
- [ ] Branch ist aktuell (rebase/merge mit Zielbranch erfolgt)
- [ ] Kein direkter Push auf geschützten Branch
- [ ] Code kompiliert ohne Fehler (`mvn clean package`)
- [ ] Keine offenen Gesprächsfäden im PR
- [ ] CODEOWNERS-Freigabe durch @ChristineJanischek angefordert
