#!/bin/bash

# BMI-Rechner Build Script
# Verwendet Java 21 LTS und Maven

set -e

echo "=== BMI-Rechner Build Script ==="
echo "Java Version:"
java -version
echo ""

echo "Building with Maven..."
mvn clean compile

echo ""
echo "Build erfolgreich abgeschlossen!"
echo "Kompilierte Dateien befinden sich im 'build/' Verzeichnis."
