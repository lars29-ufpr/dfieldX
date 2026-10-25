> **Langue / Idioma:** **Français** | [English](README.md) | [Português](README.pt-BR.md) | [Español](README_es.md)

---

# dfieldX — Analyse Numérique et Qualitative des EDO 1D

**dfieldX** est une application de bureau moderne et interactive développée avec Java 21 et JavaFX pour la simulation numérique, la visualisation des champs de directions et l'analyse qualitative des Équations Différentielles Ordinaires (EDO) scalaires du premier ordre.

Inspiré par le logiciel éducatif classique `dfield` développé à l'origine par **John C. Polking** (Université Rice) pour MATLAB, **dfieldX** a été entièrement recréé pour offrir des performances de calcul élevées, une interface moderne accélérée par GPU et une portabilité universelle (Linux, Windows et macOS).

---

## 📥 Installation & Démarrage Rapide

**dfieldX** est distribué sous forme d'exécutable JAR autonome et nécessite **Java 21 (LTS) ou une version ultérieure**.

- 📖 **[Guide d'Installation (Français)](INSTALL_fr.md)**
- 📖 [Installation Guide (English)](INSTALL.md)
- 📖 [Guia de Instalação (Português)](INSTALL_pt.md)
- 📖 [Guía de Instalación (Español)](INSTALL_es.md)

### Exécuter l'Application
Une fois Java 21+ installé, exécutez depuis votre terminal :
    java -jar dfieldX.jar

---

## 🚀 Fonctionnalités Principales

- **Champs de Directions Haute Définition :** Rendu vectoriel fluide avec options de normalisation et coloration.
- **Intégration Numérique Adaptative :** Solveurs à pas standard et adaptatif (Dormand-Prince / RK4 / Euler).
- **Analyse Qualitative Automatisée :** Résolution des équilibres, classification de la stabilité, traçage des isoclines et diagramme de phase.
- **Galerie de Modèles Intégrée :** 22 modèles préconfigurés en Biomathématiques, Mécanique Classique et Cinétique chimique.
- **Curseurs de Paramètres :** Contrôles interactifs en temps réel.
- **Exportation :** Vectorielle (SVG) et Raster (PNG) avec fond transparent.
- **Fat JAR Universel Multiplateforme :** Exécutable autonome pour Linux, Windows et macOS.

---

## 📋 Prérequis

Seul un **Environnement d'Exécution Java (JRE) ou JDK version 21 ou supérieure** est requis.
Vérifiez votre installation Java locale :
    java -version

## 🆕 Nouveautés de la Version 1.1.0
- **Internationalisation (i18n) :** Prise en charge dynamique complète ajoutée pour l'espagnol (es) et le français (fr).
- **Interface Utilisateur :** Suppression des titres académiques pour un design universel et plus épuré.
- **Moteur JavaFX :** Ajustements du redimensionnement des fenêtres et chargement dynamique du guide des modèles.
