[Version Française](INSTALL_fr.md) | [English](INSTALL.md) | [Português](INSTALL_pt.md) | [Español](INSTALL_es.md)

# Guide d'Installation
# dfieldX et pplaneX

Ce guide explique comment installer et exécuter dfieldX et pplaneX sur Windows, macOS et Linux.
Ces applications nécessitent Java 21 ou une version ultérieure.
Aucune installation préalable de Java n'est requise. Ce guide suppose que Java n'est pas encore installé sur votre ordinateur.

---

# 1. Télécharger l'Application
Téléchargez la dernière version depuis GitHub :
- dfieldX : https://tinyurl.com/difieldx-ufpr
- pplaneX : https://tinyurl.com/pplanex-ufpr

Enregistrez le fichier JAR téléchargé dans un emplacement facile à retrouver.

---

# 2. Windows
## Installer Java
1. Ouvrez votre navigateur web.
2. Visitez : https://adoptium.net/
3. Téléchargez Eclipse Temurin Java 21 LTS pour Windows x64.
4. Exécutez le programme d'installation téléchargé.
5. Dans "Custom Setup" (Configuration personnalisée), assurez-vous que "Add to PATH" et "Associate .jar files" sont activés.
6. Terminez l'installation.

## Vérifier l'Installation
Ouvrez l'Invite de Commandes et tapez :
    java -version

## Exécuter l'Application
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

---

# 3. macOS
## Déterminer le Type de votre Mac
Ouvrez 'À propos de ce Mac'. Choisissez les versions Apple Silicon pour les Mac M1/M2/M3/M4 et les versions Intel pour les Mac Intel.

## Installer Java
1. Visitez https://adoptium.net/
2. Téléchargez Java 21 LTS pour macOS.
3. Exécutez le programme d'installation et suivez les instructions.

## Vérifier l'Installation
Ouvrez le Terminal et tapez :
    java -version

## Exécuter l'Application
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

## Si macOS Bloque l'Application
Ouvrez : Réglages Système → Confidentialité et sécurité → Ouvrir quand même

---

# 4. Linux (Ubuntu, Debian, Linux Mint)
Installer Java :
    sudo apt update
    sudo apt install openjdk-21-jre

Vérifier :
    java -version

Exécuter :
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)
Installer Java :
    sudo dnf install java-21-openjdk

Vérifier :
    java -version

Exécuter :
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)
Installer Java :
    sudo pacman -S jre21-openjdk

Vérifier :
    java -version

Exécuter :
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

---

# Dépannage
## Il ne se passe rien lors du double-clic sur le fichier JAR
Exécutez l'application depuis un terminal :
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

## Erreur de Version de Classe Non Prise en Charge
Installez Java 21 ou une version ultérieure.

## L'Application ne Démarre Pas
Vérifiez avec :
    java -version
et signalez tout message d'erreur au développeur.

---

## 🍎 Avertissement Gatekeeper macOS
Si l'application est bloquée :
### Méthode 1 : Réglages Système (Interface Graphique)
1. Cliquez sur **Terminé** sur le message d'alerte.
2. Ouvrez **Réglages Système** → **Confidentialité et sécurité**.
3. Dans la section **Sécurité**, cliquez sur **Ouvrir quand même**.
4. Confirmez et cliquez sur **Ouvrir**.

### Méthode 2 : Terminal
Supprimez manuellement l'attribut de quarantaine :
    xattr -d com.apple.quarantine *.jar
Ou exécutez directement :
    java -jar *.jar
