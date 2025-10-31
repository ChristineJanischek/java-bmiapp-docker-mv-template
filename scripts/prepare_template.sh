#!/usr/bin/env bash
# Script zur Vorbereitung des Repositories als GitHub Template
# Dieses Script stellt sicher, dass alle relevanten Dateien committet sind
# und dokumentiert die Template-Nutzung.

set -euo pipefail

# Farben für Ausgabe
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║  GitHub Template Repository Vorbereitung                  ║${NC}"
echo -e "${BLUE}║  java-bmiapp-docker-mv-template                            ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
echo

# Repository-Informationen
REPO_ROOT=$(git rev-parse --show-toplevel 2>/dev/null || pwd)
cd "$REPO_ROOT"

echo -e "${GREEN}✓${NC} Repository-Root: $REPO_ROOT"
echo

# 1. Status prüfen
echo -e "${YELLOW}[1/5]${NC} Git-Status prüfen..."
if [[ -n $(git status --porcelain) ]]; then
    echo -e "${YELLOW}⚠${NC}  Uncommitted changes gefunden:"
    git status --short
    echo
    read -p "Möchtest du diese Änderungen committen? (y/n): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        read -p "Commit Message: " commit_msg
        git add -A
        git commit -m "$commit_msg"
        echo -e "${GREEN}✓${NC} Changes committed"
    fi
else
    echo -e "${GREEN}✓${NC} Working directory clean"
fi
echo

# 2. Wichtige Template-Dateien überprüfen
echo -e "${YELLOW}[2/5]${NC} Template-Struktur validieren..."
REQUIRED_FILES=(
    "pom.xml"
    "README.md"
    "Dockerfile"
    "docker-compose.yml"
    ".git/hooks/post-commit"
    ".git/hooks/pre-push"
    "src/start/Bmirechner.java"
    "src/test/java/start/BmirechnerTest.java"
)

MISSING_FILES=()
for file in "${REQUIRED_FILES[@]}"; do
    if [[ -f "$file" ]]; then
        echo -e "  ${GREEN}✓${NC} $file"
    else
        echo -e "  ${YELLOW}⚠${NC}  $file (fehlt)"
        MISSING_FILES+=("$file")
    fi
done

if [[ ${#MISSING_FILES[@]} -gt 0 ]]; then
    echo -e "${YELLOW}⚠${NC}  Einige optionale Dateien fehlen, Template kann trotzdem verwendet werden."
fi
echo

# 3. Branches auflisten
echo -e "${YELLOW}[3/5]${NC} Verfügbare Branches (für Template-Nutzer)..."
git branch -a | grep -v HEAD | sed 's/^../  /' | head -n 20
echo

# 4. Tags prüfen
echo -e "${YELLOW}[4/5]${NC} Tags überprüfen..."
TAG_COUNT=$(git tag | wc -l)
if [[ $TAG_COUNT -gt 0 ]]; then
    echo -e "${GREEN}✓${NC} $TAG_COUNT Tag(s) vorhanden:"
    git tag | sed 's/^/  /'
else
    echo -e "${BLUE}ℹ${NC}  Keine Tags vorhanden (optional)"
fi
echo

# 5. GitHub Status prüfen
echo -e "${YELLOW}[5/5]${NC} Remote-Repository Status..."
REMOTE_URL=$(git remote get-url origin 2>/dev/null || echo "Kein Remote konfiguriert")
echo -e "  Remote URL: $REMOTE_URL"

if git ls-remote --exit-code origin &>/dev/null; then
    # Ahead/Behind für main
    AHEAD=$(git rev-list --count origin/main..main 2>/dev/null || echo "0")
    BEHIND=$(git rev-list --count main..origin/main 2>/dev/null || echo "0")
    
    if [[ $AHEAD -eq 0 && $BEHIND -eq 0 ]]; then
        echo -e "  ${GREEN}✓${NC} main branch ist synchronisiert (ahead: 0, behind: 0)"
    else
        echo -e "  ${YELLOW}⚠${NC}  main branch: ahead $AHEAD, behind $BEHIND"
        if [[ $AHEAD -gt 0 ]]; then
            echo -e "  ${YELLOW}→${NC} Führe 'git push' aus, um lokale Commits zu pushen"
        fi
        if [[ $BEHIND -gt 0 ]]; then
            echo -e "  ${YELLOW}→${NC} Führe 'git pull' aus, um Remote-Änderungen zu holen"
        fi
    fi
else
    echo -e "  ${YELLOW}⚠${NC}  Kann Remote nicht erreichen"
fi
echo

# Zusammenfassung und nächste Schritte
echo
echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║  Nächste Schritte zur Template-Aktivierung                ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
echo
echo -e "${GREEN}1.${NC} Gehe zu GitHub Repository Settings:"
echo -e "   ${BLUE}https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template/settings${NC}"
echo
echo -e "${GREEN}2.${NC} Scrolle nach oben zu 'General' → 'Template repository'"
echo -e "   ☐ Aktiviere die Checkbox '${YELLOW}Template repository${NC}'"
echo
echo -e "${GREEN}3.${NC} Speichern"
echo
echo -e "${GREEN}4.${NC} Neues Repository aus Template erstellen:"
echo -e "   • Gehe zu: ${BLUE}https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template${NC}"
echo -e "   • Klicke auf '${GREEN}Use this template${NC}' → '${GREEN}Create a new repository${NC}'"
echo -e "   • Name: ${YELLOW}mindlink${NC} (oder beliebiger Name)"
echo -e "   • Repository wird mit allen Branches, Commits und Dateien erstellt"
echo
echo -e "${GREEN}5.${NC} Updates vom Template ins neue Repository holen:"
echo -e "   ${BLUE}cd /pfad/zu/mindlink${NC}"
echo -e "   ${BLUE}git remote add template https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git${NC}"
echo -e "   ${BLUE}git fetch template${NC}"
echo -e "   ${BLUE}git merge template/main --allow-unrelated-histories${NC}"
echo
echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║  Template ist bereit zur Nutzung! 🎉                       ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
echo
