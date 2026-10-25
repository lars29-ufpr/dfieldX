> **Langue / Idioma:** **Français** | [English](README.md) | [Português](README_pt_BR.md) | [Español](README_es.md)

---

# dfieldX — Analyse Numérique et Qualitative des EDO 1D

**dfieldX** est une application de bureau moderne et interactive développée avec Java 21 et JavaFX pour la simulation numérique, la visualisation des champs de directions et l'analyse qualitative des Équations Différentielles Ordinaires (EDO) scalaires du premier ordre :

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspiré par le logiciel éducatif classique `dfield` développé à l'origine par **John C. Polking** (Université Rice) pour MATLAB, **dfieldX** a été entièrement recréé pour offrir des performances de calcul élevées, une interface moderne accélérée par GPU et une portabilité universelle (Linux, Windows et macOS).

---

## 📥 Installation & Démarrage Rapide

**dfieldX** est distribué sous forme d'exécutable JAR autonome et nécessite **Java 21 (LTS) ou une version ultérieure**.

Si Java n'est pas installé ou si vous avez besoin d'instructions étape par étape pour votre système d'exploitation (Windows, macOS ou Linux), consultez notre guide d'installation :

- 📖 **[Guide d'Installation (Français)](INSTALL_fr.md)**

### Exécuter l'Application

Une fois Java 21+ installé, exécutez depuis votre terminal :

```bash
java -jar dfieldX.jar
```

---

## 🚀 Fonctionnalités Principales

- **Champs de Directions Haute Définition :** Rendu vectoriel fluide des segments de pente avec options de normalisation de la longueur et coloration selon l'intensité de la pente.
- **Intégration Numérique Adaptative de Haute Précision :**
  * Solveurs à pas standard et adaptatif (Dormand-Prince / Runge-Kutta 4ème et 5ème ordre, RK4 standard et Euler).
  * Évaluation native dans le domaine réel de la fonction racine cubique réelle ($\text{cbrt}$) sur tout $\mathbb{R}$, éliminant les artefacts de phase liés aux coupures de branche.
- **Analyse Qualitative Automatisée :**
  * Résolution numérique des équilibres ($f(t, x^*) = 0$).
  * Classification de la stabilité linéaire (attracteurs, répulseurs et points semi-stables/cols).
  * Traçage automatique des isoclines via l'algorithme des contours *Marching Squares*.
  * Diagramme de phase auxiliaire ($x' \text{ vs } x$) pour les équations autonomes.
- **Galerie de Modèles Intégrée :** Une collection de 22 modèles préconfigurés couvrant :
  * Biomathématiques et Dynamique des Populations (Croissance logistique, Effet Allee, Gompertz, prélèvement à effort constant et quota fixe).
  * Mécanique Classique et Physique (Chute libre avec frottement linéaire et quadratique, circuits RC, loi de refroidissement de Newton).
  * Cinétique chimique et équations de référence.
- **Curseurs de Paramètres en Temps Réel :** Contrôles interactifs pour l'ajustement dynamique des paramètres et l'exploration continue des bifurcations.
- **Exportation Vectorielle et Raster Haute Résolution :**
  * Graphiques vectoriels évolutifs purs (**SVG**) pour des figures de qualité publication et l'intégration LaTeX.
  * Exportation raster haute résolution (**PNG**) avec prise en charge du fond transparent (canal alpha RGBA).
- **Fat JAR Universel Multiplateforme :** Exécutable autonome intégrant les binaires graphiques natifs pour **Linux (x86_64)**, **Windows (x86_64)** et **macOS (Apple Silicon M-Series et Intel x86_64)**.

---

## 📋 Prérequis

Pour exécuter le binaire précompilé, seul un **Environnement d'Exécution Java (JRE) ou JDK version 21 ou supérieure** est requis.

Vérifiez votre installation Java locale en exécutant :

```bash
java -version
```
