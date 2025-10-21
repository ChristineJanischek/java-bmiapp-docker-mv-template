#!/bin/bash

# BMI-Rechner Run Script
# Startet die GUI-Anwendung mit Java 21

set -e

echo "=== BMI-Rechner Starter ==="
echo "Java Version:"
java -version
echo ""

# Stelle sicher, dass das Projekt kompiliert ist
if [ ! -d "build/start" ]; then
    echo "Projekt wird zunächst kompiliert..."
    ./build.sh
    echo ""
fi

echo "Starte MainWindow (GUI)..."
java -cp build:lib/* start.MainWindow
