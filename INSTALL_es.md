# Guía de Instalación y Ejecución

## Requisitos del Sistema
- Entorno de Ejecución Java (JRE) versión 17 o superior (Java 21 recomendado).
- Sistemas Operativos Soportados:
  - Linux (Ubuntu, Fedora, Arch Linux, Mint, etc.)
  - Windows 10 / 11
  - macOS (Intel & Apple Silicon)

## Cómo Ejecutar la Aplicación Empaquetada (.jar)
Para ejecutar la aplicación directamente sin compilar desde el código fuente, asegúrese de que Java esté instalado y ejecute en su terminal o consola:

java -jar target/dfieldX.jar

### Notas Específicas por Sistema Operativo:
- Linux: Asegúrese de que su entorno de escritorio soporte el renderizado gráfico de JavaFX (bibliotecas GTK3 instaladas). Abra una terminal en el directorio de la aplicación y ejecute el comando.
- Windows: Abra el Símbolo del sistema (cmd) o PowerShell en la carpeta de la aplicación y ejecute el comando. Asegúrese de que Java esté configurado en el PATH.
- macOS: Abra Terminal, navegue hasta la carpeta de la aplicación usando el comando cd y ejecute el comando. Verifique que su entorno Java esté configurado correctamente.

## Forzando un Idioma Específico mediante Línea de Comandos
Puede sobrescribir el idioma predeterminado del sistema operativo utilizando propiedades del sistema:

- Español:
  java -Duser.language=es -Duser.country=ES -jar target/dfieldX.jar
- Inglés:
  java -Duser.language=en -Duser.country=US -jar target/dfieldX.jar
- Portugués:
  java -Duser.language=pt -Duser.country=BR -jar target/dfieldX.jar
- Francés:
  java -Duser.language=fr -Duser.country=FR -jar target/dfieldX.jar
- Ruso:
  java -Duser.language=ru -Duser.country=RU -jar target/dfieldX.jar

## Desarrollo y Compilación desde el Código Fuente
Si desea compilar y empaquetar la aplicación desde el código fuente utilizando Maven:
mvn clean package
