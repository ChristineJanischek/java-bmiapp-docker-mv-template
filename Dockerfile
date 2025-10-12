# Basis-Image mit Java 21
FROM eclipse-temurin:21-jdk-alpine

# Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere den gesamten src-Ordner in das Arbeitsverzeichnis
COPY ./src ./src

# Kopiere den lib-Ordner (JDBC-Treiber) ins Image
COPY ./lib ./lib

# Kompiliere alle Java-Dateien ins build-Verzeichnis
RUN mkdir -p build && javac -d build src/start/*.java

# Starte das Programm mit JDBC-Treiber im Klassenpfad
CMD ["java", "-cp", "build:lib/mysql-connector-j-8.4.0.jar", "start.Main"]
