[English](INSTALL.md) | [Versão em Português](INSTALL_pt_BR.md)

# Guía de Instalación

# dfieldX y pplaneX

Esta guía explica cómo instalar y ejecutar dfieldX y pplaneX en Windows, macOS y Linux.

Estas aplicaciones requieren Java 21 o superior.

No se requiere una instalación previa de Java. Esta guía asume que Java aún no está instalado en su computadora.

---

# 1. Descargar la Aplicación

Descargue la última versión desde GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Guarde el archivo JAR descargado en una ubicación que pueda encontrar fácilmente más tarde, como su Escritorio o la carpeta de Descargas.

---

# 2. Windows

## Instalar Java

1. Abra su navegador web.
2. Visite: https://adoptium.net/
3. Descargue Eclipse Temurin Java 21 LTS para Windows x64.
4. Ejecute el instalador descargado.
5. En "Custom Setup" (Instalación Personalizada), asegúrese de que "Add to PATH" y "Associate .jar files" estén habilitados.
6. Finalice la instalación.

## Verificar la Instalación

Abra el Símbolo del sistema (Command Prompt) y ejecute:

    java -version

Cualquier versión 21 o superior es compatible.

## Ejecutar la Aplicación

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

---

# 3. macOS

## Determine el Tipo de su Mac

Abra 'Acerca de esta Mac'.

Elija las versiones de Apple Silicon para Macs con M1/M2/M3/M4 y las versiones de Intel para Macs con procesador Intel.

## Instalar Java

1. Visite https://adoptium.net/
2. Descargue Java 21 LTS para macOS.
3. Ejecute el instalador.
4. Siga las instrucciones de instalación.

## Verificar la Instalación

Abra la Terminal y ejecute:

    java -version

## Ejecutar la Aplicación

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

## Si macOS Bloquea la Aplicación

Abra:

Configuración del Sistema → Privacidad y Seguridad → Abrir de todos modos

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Instalar Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verificar:

    java -version

Ejecutar:

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Instalar Java:

    sudo dnf install java-21-openjdk

Verificar:

    java -version

Ejecutar:

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Instalar Java:

    sudo pacman -S jre21-openjdk

Verificar:

    java -version

Ejecutar:

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

---

# Solución de Problemas

## No Ocurre Nada al Hacer Doble Clic en el Archivo JAR

Ejecute la aplicación desde una terminal:

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

## Error de Versión de Clase no Soportada (Unsupported Class Version Error)

Instale Java 21 o superior.

## La Aplicación No Inicia

Compruebe:

    java -version

y reporte cualquier mensaje de error al desarrollador.

---

## 🍎 Advertencia de Gatekeeper en macOS

Si ve un mensaje indicando que la aplicación **"no se puede abrir porque Apple no puede verificar que esté libre de malware"**, esto ocurre porque el archivo `.jar` de código abierto no está firmado con un certificado de desarrollador de Apple.

### Método 1: Configuración del Sistema (GUI)
1. Haga clic en **Aceptar** (Done) en la ventana de alerta.
2. Abra **Configuración del Sistema** → **Privacidad y Seguridad**.
3. Desplácese hacia abajo hasta la sección de **Seguridad**.
4. Localice el mensaje indicando que el archivo `.jar` fue bloqueado y haga clic en **Abrir de todos modos** (Open Anyway).
5. Confirme con su contraseña/Touch ID y haga clic en **Abrir** (Open).

### Método 2: Terminal
Elimine la bandera de cuarentena manualmente:
```bash
xattr -d com.apple.quarantine *.jar
```
O ejecute directamente desde la línea de comandos:
```bash
java -jar *.jar
```
