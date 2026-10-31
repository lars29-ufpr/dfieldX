> **Language / Idioma:** **English** | [Português](INSTALL_pt_BR.md) | [Español](INSTALL_es.md) | [Français](INSTALL_fr.md) | [Русский](INSTALL_ru.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | [Italiano](INSTALL_it.md)

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

    java -jar dfieldX.jar

or

    java -jar pplaneX.jar

---

# 3. macOS

## Install Java
1. Visit https://adoptium.net/
2. Download the latest Java LTS (21 or newer) for macOS.  
   * *Tip:* Choose the `.dmg` installer (e.g., `jdk-21_macos-x64_bin.dmg` for Intel Macs, or the `aarch64` equivalent for Apple Silicon M1/M2/M3/M4).
3. Run the installer and follow the on-screen instructions.

## Organize the Application
For better organization, we recommend moving the downloaded `dfieldX.jar` and `pplaneX.jar` files to your **Applications** folder.

## First-Time Execution (Bypassing Gatekeeper)
macOS includes a security feature that blocks applications from unidentified developers. If you simply double-click the `.jar` file for the first time, macOS will display a warning and prevent it from opening.

1. **Do not double-click** the file the first time.
2. Instead, **Right-click** (or Control-click) the `.jar` file and select **Open** from the context menu.
3. A dialogue box will appear asking to confirm. Click **Open** again.
4. **Normal Use:** You only need to do this once. For all subsequent uses, you can simply double-click the `.jar` file to launch the application normally.

## Run via Terminal (Alternative)
If you prefer the command line, open Terminal and run:
    java -jar dfieldX.jar
or
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Install Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verify:

    java -version

Run:

    java -jar dfieldX.jar

or

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Install Java:

    sudo dnf install java-21-openjdk

Verify:

    java -version

Run:

    java -jar dfieldX.jar

or

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Install Java:

    sudo pacman -S jre21-openjdk

Verify:

    java -version

Run:

    java -jar dfieldX.jar

or

    java -jar pplaneX.jar

---

# Troubleshooting

## Nothing Happens When Double-Clicking the JAR File

Run the application from a terminal:

    java -jar dfieldX.jar

or

    java -jar pplaneX.jar

## Unsupported Class Version Error

Install Java 21 or newer.

## Application Does Not Start

Check:

    java -version

and report any error messages to the developer.
