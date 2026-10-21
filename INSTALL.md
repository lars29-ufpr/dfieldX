[Versão em Português](INSTALL_pt.md)

# Installation Guide

# dfieldX and pplaneX

This guide explains how to install and run dfieldX and pplaneX on Windows, macOS, and Linux.

These applications require Java 21 or newer.

No previous Java installation is required. This guide assumes Java is not yet installed on your computer.

---

# 1. Download the Application

Download the latest release from GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr
- pplaneX: https://tinyurl.com/pplanex-ufpr

Save the downloaded JAR file in a location you can easily find later, such as your Desktop or Downloads folder.

---

# 2. Windows

## Install Java

1. Open your web browser.
2. Visit: https://adoptium.net/
3. Download Eclipse Temurin Java 21 LTS for Windows x64.
4. Run the downloaded installer.
5. In "Custom Setup", ensure "Add to PATH" and "Associate .jar files" are enabled.
6. Finish the installation.

## Verify the Installation

Open Command Prompt and run:

    java -version

Any version 21 or newer is supported.

## Run the Application

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

---

# 3. macOS

## Determine Your Mac Type

Open 'About This Mac'.

Choose Apple Silicon builds for M1/M2/M3/M4 Macs and Intel builds for Intel Macs.

## Install Java

1. Visit https://adoptium.net/
2. Download Java 21 LTS for macOS.
3. Run the installer.
4. Follow the installation instructions.

## Verify the Installation

Open Terminal and run:

    java -version

## Run the Application

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

## If macOS Blocks the Application

Open:

System Settings → Privacy & Security → Open Anyway

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Install Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verify:

    java -version

Run:

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Install Java:

    sudo dnf install java-21-openjdk

Verify:

    java -version

Run:

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

---

# 6. Linux (Arch Linux, Manjaro)

Install Java:

    sudo pacman -S jre21-openjdk

Verify:

    java -version

Run:

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

---

# Troubleshooting

## Nothing Happens When Double-Clicking the JAR File

Run the application from a terminal:

    java -jar dfieldX-1.0.0.jar

or

    java -jar phase-plane-analyzer-1.0.0.jar

## Unsupported Class Version Error

Install Java 21 or newer.

## Application Does Not Start

Check:

    java -version

and report any error messages to the developer.

---

## 🍎 macOS Gatekeeper Warning

If you see a prompt stating that the application **"cannot be opened because Apple cannot verify it is free of malware"**, this occurs because the open-source `.jar` file is not signed with an Apple Developer certificate.

### Method 1: System Settings (GUI)
1. Click **Done** on the alert prompt.
2. Open **System Settings** → **Privacy & Security**.
3. Scroll down to the **Security** section.
4. Locate the message indicating the `.jar` was blocked and click **Open Anyway**.
5. Confirm with your password/Touch ID and click **Open**.

### Method 2: Terminal
Remove the quarantine flag manually:
```bash
xattr -d com.apple.quarantine *.jar
```
Or run directly from the command line:
```bash
java -jar *.jar
```
