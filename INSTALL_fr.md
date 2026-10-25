# Guide d'Installation et d'Exécution

## Prérequis Système
- Environnement d'Exécution Java (JRE) version 17 ou supérieure (Java 21 recommandé).
- Systèmes d'exploitation pris en charge :
  - Linux (Ubuntu, Fedora, Arch Linux, Mint, etc.)
  - Windows 10 / 11
  - macOS (Intel & Apple Silicon)

## Comment Exécuter l'Application Emballée (.jar)
Pour exécuter l'application directement sans compilation à partir du code source, assurez-vous que Java est installé et exécutez dans votre terminal :

java -jar target/dfieldX.jar

### Notes Spécifiques par Système d'Exploitation :
- Linux : Assurez-vous que votre environnement de bureau prend en charge le rendu graphique JavaFX (bibliothèques GTK3 installées). Ouvrez un terminal dans le répertoire de l'application et exécutez la commande.
- Windows : Ouvrez l'Invite de commandes (cmd) ou PowerShell dans le dossier de l'application et exécutez la commande. Assurez-vous que Java est configuré dans le PATH.
- macOS : Ouvrez Terminal, accédez au dossier de l'application à l'aide de la commande cd et exécutez la commande. Vérifiez que votre environnement Java est correctement configuré.

## Forcer une Langue Spécifique via la Ligne de Commande
Vous pouvez remplacer la langue par défaut du système d'exploitation en définissant des propriétés système :

- Français :
  java -Duser.language=fr -Duser.country=FR -jar target/dfieldX.jar
- Anglais :
  java -Duser.language=en -Duser.country=US -jar target/dfieldX.jar
- Portugais :
  java -Duser.language=pt -Duser.country=BR -jar target/dfieldX.jar
- Espagnol :
  java -Duser.language=es -Duser.country=ES -jar target/dfieldX.jar
- Russe :
  java -Duser.language=ru -Duser.country=RU -jar target/dfieldX.jar

## Développement et Compilation à partir du Code Source
Si vous souhaitez compiler et empaqueter l'application à partir du code source à l'aide de Maven :
mvn clean package
