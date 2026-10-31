> **Language / Idioma:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | **Español** | [Français](INSTALL_fr.md) | [Русский](INSTALL_ru.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | [Italiano](INSTALL_it.md)

# Guía de Instalación

# dfieldX y pplaneX

Esta guía explica cómo instalar y ejecutar dfieldX y pplaneX en Windows, macOS y Linux.

Estas aplicaciones requieren Java 21 o una versión más reciente.

No se requiere una instalación previa de Java. Esta guía asume que Java aún no está instalado en su computadora.

---

# 1. Descargar la Aplicación

Descargue la última versión desde GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Guarde el archivo JAR descargado en una ubicación donde pueda encontrarlo fácilmente más tarde, como su Escritorio (Desktop) o la carpeta Descargas (Downloads).

---

# 2. Windows

## Instalar Java

1. Abra su navegador web.
2. Visite: https://adoptium.net/
3. Descargue Eclipse Temurin Java 21 LTS para Windows x64.
4. Ejecute el instalador descargado.
5. En "Custom Setup" (Instalación Personalizada), asegúrese de que "Add to PATH" y "Associate .jar files" estén habilitadas.
6. Finalice la instalación.

## Verificar la Instalación

Abra el Símbolo del sistema (Command Prompt) y ejecute:

    java -version

Cualquier versión 21 o más reciente es compatible.

## Ejecutar la Aplicación

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

---

# 3. macOS

## Instalar Java
1. Visite https://adoptium.net/
2. Descargue el Java LTS más reciente (21 o superior) para macOS.  
   * *Consejo:* Elija el instalador `.dmg` (ej. `jdk-21_macos-x64_bin.dmg` para Macs con Intel, o el equivalente `aarch64` para Apple Silicon M1/M2/M3/M4).
3. Ejecute el instalador y siga las instrucciones en la pantalla.

## Organizar la Aplicación
Para una mejor organización, recomendamos mover los archivos descargados `dfieldX.jar` y `pplaneX.jar` a su carpeta de **Aplicaciones** (Applications).

## Primera Ejecución (Evitando Gatekeeper)
macOS incluye una función de seguridad que bloquea aplicaciones de desarrolladores no identificados. Si simplemente hace doble clic en el archivo `.jar` por primera vez, macOS mostrará una advertencia y evitará que se abra.

1. **No haga doble clic** en el archivo la primera vez.
2. En su lugar, haga **clic derecho** (o Control-clic) en el archivo `.jar` y seleccione **Abrir** (Open) en el menú contextual.
3. Aparecerá un cuadro de diálogo pidiendo confirmación. Haga clic en **Abrir** nuevamente.
4. **Uso Normal:** Solo necesita hacer esto una vez. Para todos los usos posteriores, simplemente haga doble clic en el archivo `.jar` para iniciar la aplicación normalmente.

## Ejecutar a través del Terminal (Alternativa)
Si prefiere la línea de comandos, abra el Terminal y ejecute:
    java -jar dfieldX.jar
o
    java -jar pplaneX.jar

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

## No sucede nada al hacer doble clic en el archivo JAR

Ejecute la aplicación desde un terminal:

    java -jar dfieldX.jar

o

    java -jar pplaneX.jar

## Error "Unsupported Class Version"

Instale Java 21 o una versión más reciente.

## La Aplicación No Inicia

Verifique:

    java -version

y reporte cualquier mensaje de error al desarrollador.
