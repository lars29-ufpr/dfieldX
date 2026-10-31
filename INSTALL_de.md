> **Language / Sprache:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | **Deutsch**

# Installationsanleitung

# dfieldX und pplaneX

Diese Anleitung erklärt, wie Sie dfieldX und pplaneX unter Windows, macOS und Linux installieren und ausführen.

Diese Anwendungen erfordern Java 21 oder neuer.

Es ist keine vorherige Java-Installation erforderlich. Diese Anleitung geht davon aus, dass Java noch nicht auf Ihrem Computer installiert ist.

---

# 1. Herunterladen der Anwendung

Laden Sie die neueste Version von GitHub herunter:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Speichern Sie die heruntergeladene JAR-Datei an einem Ort, den Sie später leicht wiederfinden, z. B. auf Ihrem Desktop oder im Ordner "Downloads".

---

# 2. Windows

## Java installieren

1. Öffnen Sie Ihren Webbrowser.
2. Besuchen Sie: https://adoptium.net/
3. Laden Sie Eclipse Temurin Java 21 LTS für Windows x64 herunter.
4. Führen Sie das heruntergeladene Installationsprogramm aus.
5. Stellen Sie unter "Custom Setup" (Benutzerdefinierte Installation) sicher, dass "Add to PATH" und "Associate .jar files" aktiviert sind.
6. Schließen Sie die Installation ab.

## Installation überprüfen

Öffnen Sie die Eingabeaufforderung (Command Prompt) und führen Sie aus:

    java -version

Jede Version 21 oder neuer wird unterstützt.

## Anwendung ausführen

    java -jar dfieldX.jar

oder

    java -jar pplaneX.jar

---

# 3. macOS

## Java installieren
1. Besuchen Sie https://adoptium.net/
2. Laden Sie das neueste Java LTS (21 oder neuer) für macOS herunter.  
   * *Tipp:* Wählen Sie das `.dmg`-Installationsprogramm (z. B. `jdk-21_macos-x64_bin.dmg` für Intel-Macs oder das `aarch64`-Äquivalent für Apple Silicon M1/M2/M3/M4).
3. Führen Sie das Installationsprogramm aus und befolgen Sie die Anweisungen auf dem Bildschirm.

## Anwendung organisieren
Zur besseren Übersicht empfehlen wir, die heruntergeladenen Dateien `dfieldX.jar` und `pplaneX.jar` in Ihren Ordner **Applications** (Programme) zu verschieben.

## Erstmalige Ausführung (Gatekeeper umgehen)
macOS enthält eine Sicherheitsfunktion, die Anwendungen von nicht verifizierten Entwicklern blockiert. Wenn Sie die `.jar`-Datei zum ersten Mal einfach per Doppelklick öffnen, zeigt macOS eine Warnung an und verhindert das Öffnen.

1. **Machen Sie beim ersten Mal keinen Doppelklick** auf die Datei.
2. Machen Sie stattdessen einen **Rechtsklick** (oder Control-Klick) auf die `.jar`-Datei und wählen Sie **Öffnen** (Open) aus dem Kontextmenü.
3. Ein Dialogfeld zur Bestätigung wird angezeigt. Klicken Sie erneut auf **Öffnen**.
4. **Normale Verwendung:** Dies müssen Sie nur einmal tun. Für alle weiteren Verwendungen können Sie die `.jar`-Datei einfach per Doppelklick starten.

## Ausführung über das Terminal (Alternative)
Wenn Sie die Kommandozeile bevorzugen, öffnen Sie das Terminal und führen Sie aus:
    java -jar dfieldX.jar
oder
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Java installieren:

    sudo apt update
    sudo apt install openjdk-21-jre

Überprüfen:

    java -version

Ausführen:

    java -jar dfieldX.jar

oder

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Java installieren:

    sudo dnf install java-21-openjdk

Überprüfen:

    java -version

Ausführen:

    java -jar dfieldX.jar

oder

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Java installieren:

    sudo pacman -S jre21-openjdk

Überprüfen:

    java -version

Ausführen:

    java -jar dfieldX.jar

oder

    java -jar pplaneX.jar

---

# Fehlerbehebung

## Nichts passiert beim Doppelklick auf die JAR-Datei

Führen Sie die Anwendung über ein Terminal aus:

    java -jar dfieldX.jar

oder

    java -jar pplaneX.jar

## Unsupported Class Version Error

Installieren Sie Java 21 oder neuer.

## Anwendung startet nicht

Überprüfen Sie:

    java -version

und melden Sie eventuelle Fehlermeldungen dem Entwickler.
