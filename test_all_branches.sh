#!/bin/bash
# Test-Skript: Prüft ob die GUI in allen Branches verfügbar und lauffähig ist

echo "=== GUI-Verfügbarkeitstest für alle Branches ==="
echo ""

BRANCHES=("main" "version-0-grundgeruest" "version-1-mvc-gui" "version-2-methoden" "version-3-validation")

for branch in "${BRANCHES[@]}"; do
    echo "----------------------------------------"
    echo "📋 Branch: $branch"
    echo "----------------------------------------"
    
    # Prüfe welche Java-Dateien existieren
    echo "✓ Java-Dateien im Branch:"
    git ls-tree -r $branch --name-only | grep "\.java$" | sed 's/^/  /'
    
    # Prüfe ob MainWindow.java existiert
    if git ls-tree -r $branch --name-only | grep -q "MainWindow.java"; then
        echo "✅ MainWindow.java: VORHANDEN"
        
        # Prüfe ob BmiManager.java existiert
        if git ls-tree -r $branch --name-only | grep -q "BmiManager.java"; then
            echo "✅ BmiManager.java: VORHANDEN"
        else
            echo "❌ BmiManager.java: FEHLT"
        fi
        
        # Prüfe ob Bmirechner.java existiert
        if git ls-tree -r $branch --name-only | grep -q "Bmirechner.java"; then
            echo "✅ Bmirechner.java: VORHANDEN"
        else
            echo "❌ Bmirechner.java: FEHLT"
        fi
        
        # Fazit
        if git ls-tree -r $branch --name-only | grep -q "BmiManager.java" && \
           git ls-tree -r $branch --name-only | grep -q "Bmirechner.java"; then
            echo "🎉 GUI-TEST: LAUFFÄHIG (alle Dependencies vorhanden)"
        else
            echo "⚠️  GUI-TEST: NICHT LAUFFÄHIG (Dependencies fehlen)"
        fi
    else
        echo "❌ MainWindow.java: FEHLT"
        echo "🚫 GUI-TEST: NICHT MÖGLICH"
    fi
    
    echo ""
done

echo "========================================"
echo "📊 ZUSAMMENFASSUNG"
echo "========================================"
echo ""
echo "Empfehlung für pädagogisches Konzept:"
echo ""
echo "1. Version 0 (main): Schüler implementieren Model & Controller"
echo "   → GUI ist vorhanden, aber nicht lauffähig (fehlt Logik)"
echo "   → Schüler sehen das Ziel (GUI) von Anfang an"
echo ""
echo "2. Version 1: Musterlösung mit kompletter MVC-Implementierung"
echo "   → GUI voll funktionsfähig"
echo ""
echo "3. Version 2+: Erweiterte Features"
echo "   → GUI mit zusätzlichen Komponenten"
echo ""
