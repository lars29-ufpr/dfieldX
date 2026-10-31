> **Language / Langue:** [English](INSTALL.md) | [Português](INSTALL_pt_BR.md) | [Español](INSTALL_es.md) | **Français** | [Русский](INSTALL_ru.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | [Italiano](INSTALL_it.md)

# Guide d'installation

# dfieldX et pplaneX

Ce guide explique comment installer et exécuter dfieldX et pplaneX sur Windows, macOS et Linux.

Ces applications nécessitent Java 21 ou une version plus récente.

Aucune installation préalable de Java n'est requise. Ce guide suppose que Java n'est pas encore installé sur votre ordinateur.

---

# 1. Télécharger l'Application

Téléchargez la dernière version sur GitHub :

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Enregistrez le fichier JAR téléchargé dans un emplacement facile à trouver ultérieurement, comme votre Bureau ou votre dossier Téléchargements.

---

# 2. Windows

## Installer Java

1. Ouvrez votre navigateur web.
2. Visitez : https://adoptium.net/
3. Téléchargez Eclipse Temurin Java 21 LTS pour Windows x64.
4. Exécutez le programme d'installation téléchargé.
5. Dans "Custom Setup" (Installation personnalisée), assurez-vous que les options "Add to PATH" et "Associate .jar files" sont activées.
6. Terminez l'installation.

## Vérifier l'installation

Ouvrez l'Invite de commandes et exécutez :

    java -version

Toute version 21 ou plus récente est prise en charge.

## Exécuter l'application

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

---

# 3. macOS

## Installer Java
1. Visitez https://adoptium.net/
2. Téléchargez le dernier Java LTS (21 ou plus récent) pour macOS.  
   * *Astuce :* Choisissez le programme d'installation `.dmg` (par ex. `jdk-21_macos-x64_bin.dmg` pour les Mac Intel, ou l'équivalent `aarch64` pour Apple Silicon M1/M2/M3/M4).
3. Exécutez le programme d'installation et suivez les instructions à l'écran.

## Organiser l'application
Pour une meilleure organisation, nous recommandons de déplacer les fichiers téléchargés `dfieldX.jar` et `pplaneX.jar` dans votre dossier **Applications**.

## Première exécution (Contourner Gatekeeper)
macOS intègre une fonction de sécurité qui bloque les applications de développeurs non identifiés. Si vous double-cliquez simplement sur le fichier `.jar` pour la première fois, macOS affichera un avertissement et empêchera son ouverture.

1. **Ne double-cliquez pas** sur le fichier la première fois.
2. Au lieu de cela, faites un **clic droit** (ou Contrôle-clic) sur le fichier `.jar` et sélectionnez **Ouvrir** (Open) dans le menu contextuel.
3. Une boîte de dialogue apparaîtra vous demandant de confirmer. Cliquez à nouveau sur **Ouvrir**.
4. **Utilisation normale :** Vous n'aurez à faire cela qu'une seule fois. Pour toutes les utilisations ultérieures, vous pourrez simplement double-cliquer sur le fichier `.jar` pour lancer l'application normalement.

## Exécuter via le Terminal (Alternative)
Si vous préférez la ligne de commande, ouvrez le Terminal et exécutez :
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

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

## Rien ne se passe lors du double-clic sur le fichier JAR

Exécutez l'application depuis un terminal :

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

## Erreur "Unsupported Class Version"

Installez Java 21 ou plus récent.

## L'application ne démarre pas

Vérifiez :

    java -version

et signalez tout message d'erreur au développeur.
