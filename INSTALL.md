# Installation & Execution Guide

## System Requirements
- Java Runtime Environment (JRE) version 17 or higher (Java 21 recommended).
- Supported Operating Systems:
  - Linux (Ubuntu, Fedora, Arch Linux, Mint, etc.)
  - Windows 10 / 11
  - macOS (Intel & Apple Silicon)

## Running the Pre-packaged Application (.jar)
To execute the application directly without building from source, ensure Java is installed and run in your terminal or command prompt:

java -jar target/dfieldX.jar

### Platform-Specific Execution Notes:
- Linux: Ensure your desktop environment supports JavaFX graphical rendering (GTK3 libraries installed). Open a terminal in the application directory and run the command.
- Windows: Open Command Prompt or PowerShell in the application folder and execute the command. If Java is in your system PATH, it will launch seamlessly.
- macOS: Open Terminal, navigate to the application folder using the cd command, and run the execution command. Ensure your JAVA_HOME and PATH are properly configured.

## Forcing a Specific Language via Command Line
You can override the operating system default locale by passing system properties during startup:

- English:
  java -Duser.language=en -Duser.country=US -jar target/dfieldX.jar
- Portuguese (Brazil):
  java -Duser.language=pt -Duser.country=BR -jar target/dfieldX.jar
- Spanish:
  java -Duser.language=es -Duser.country=ES -jar target/dfieldX.jar
- French:
  java -Duser.language=fr -Duser.country=FR -jar target/dfieldX.jar
- Russian:
  java -Duser.language=ru -Duser.country=RU -jar target/dfieldX.jar

## Development & Compilation from Source
If you are modifying the source code and need to recompile and package the application using Maven:
mvn clean package
