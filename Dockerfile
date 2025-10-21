# Basis-Image mit Java 21 (Alpine)
FROM eclipse-temurin:21-jdk-alpine

# Arbeitsverzeichnis im Container
WORKDIR /app

# Benötigte X11 Bibliotheken für Swing/AWT GUI installieren
# (libXtst, libXext, libXrender, libXi, etc.) und Fonts
RUN apk add --no-cache \
	libx11 \
	libxext \
	libxrender \
	libxtst \
	libxi \
	libxfixes \
	libxrandr \
	libxinerama \
	ttf-dejavu \
	fontconfig

# Kopiere den gesamten src-Ordner in das Arbeitsverzeichnis
COPY ./src ./src

# Kopiere den lib-Ordner (JDBC-Treiber) ins Image
COPY ./lib ./lib

# Kompiliere alle Java-Dateien ins build-Verzeichnis
RUN mkdir -p build && javac -d build src/start/*.java \
 && mkdir -p build/start/images \
 && cp -r src/start/images/* build/start/images/

# Starte das GUI-Programm (MainWindow). CLASSPATH enthält alle JARs in lib.
# Hinweis: Für die Anzeige muss der Container Zugriff auf den X11-Socket haben (siehe docker-compose.yml)
CMD ["java", "-cp", "build:lib/*", "start.MainWindow"]
