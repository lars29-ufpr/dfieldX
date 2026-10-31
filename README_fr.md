> **Language / Langue:** [English](README.md) | [Português](README_pt_BR.md) | [Español](README_es.md) | **Français** | [Русский](README_ru.md) | [Български](README_bg.md) | [Українська](README_uk.md) | [Deutsch](README_de.md) | [Italiano](README_it.md)

---

# dfieldX — Analyse Numérique et Qualitative des EDO 1D

**dfieldX** est une application de bureau moderne et interactive développée avec Java 21 et JavaFX pour la simulation numérique, la visualisation de champs de directions et l'analyse qualitative d'Équations Différentielles Ordinaires (EDO) scalaires du premier ordre :

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspiré du logiciel éducatif classique `dfield` développé à l'origine par **John C. Polking** (Université Rice) pour MATLAB, **dfieldX** a été reconstruit à partir de zéro pour offrir de hautes performances de calcul, une interface moderne accélérée par GPU et une portabilité universelle multiplateforme (Linux, Windows et macOS).

---

## 📥 Installation et Démarrage Rapide

**dfieldX** est distribué sous la forme d'un fichier JAR exécutable autonome et nécessite **Java 21 (LTS) ou plus récent**.

Si vous n'avez pas installé Java ou si vous avez besoin d'un guide étape par étape pour votre système d'exploitation (Windows, macOS ou Linux), consultez nos guides d'installation :

- 📖 **[Guide d'installation (Français)](INSTALL_fr.md)**

### Exécution de l'application

Une fois Java 21+ installé, exécutez depuis votre terminal :

```bash
java -jar dfieldX.jar
```

---

## 🚀 Caractéristiques Principales

- **Champs de directions haute définition :** Rendu vectoriel fluide des segments de pente avec options de normalisation de longueur et coloration par intensité de pente.
- **Intégration numérique adaptative de haute précision :**
  * Solveurs à pas standard et adaptatif (Dormand-Prince / Runge-Kutta 4ème et 5ème ordre, RK4 standard et Euler).
  * Évaluation native dans le domaine réel de la fonction racine cubique réelle ($\text{cbrt}$) sur tout $\mathbb{R}$, éliminant les artefacts de phase liés aux coupures de branche.
- **Analyse qualitative automatisée :**
  * Résolution numérique des points d'équilibre ($f(t, x^*) = 0$).
  * Classification de la stabilité linéaire (attracteurs, répulseurs et points semi-stables/selles).
  * Tracé automatique des isoclines via l'algorithme de contour *Marching Squares*.
  * Diagramme auxiliaire de ligne de phase ($x' \text{ vs } x$) pour les équations autonomes.
- **Galerie intégrée de modèles sélectionnés :** Une collection de 22 modèles préconfigurés couvrant :
  * Biomathématiques et Dynamique des Populations (Croissance logistique, effet Allee, Gompertz, récolte à effort constant et quota fixe).
  * Mécanique Classique et Physique (Chute libre avec frottement linéaire et quadratique, circuits RC, loi de refroidissement de Newton).
  * Cinétique chimique et EDO de référence fondamentales.
- **Curseurs de paramètres en temps réel :** Contrôles interactifs pour l'ajustement dynamique des paramètres et l'exploration continue des bifurcations.
- **Exportation vectorielle et raster haute résolution :**
  * Graphiques vectoriels évolutifs purs (**SVG**) pour des figures de qualité publication et une intégration LaTeX.
  * Exportation raster à DPI élevé (**PNG**) avec prise en charge du fond transparent (canal alpha RGBA).
- **Fat JAR Universel Multiplateforme :** Exécutable autonome regroupant les binaires graphiques natifs de la plateforme pour **Linux (x86_64)**, **Windows (x86_64)** et **macOS (Apple Silicon M-Series et Intel x86_64)**.

---

## 📋 Prérequis

Pour exécuter le binaire précompilé, seul un **Environnement d'Exécution Java (JRE) ou JDK version 21 ou supérieure** est requis.

Vérifiez votre installation locale de Java en exécutant :
```bash
java -version
```

## Crédits et Licence

Ce projet est une extension éducative basée sur les logiciels originaux dfield et pplane (copyright John C. Polking, Université Rice) et leurs versions Java respectives (Joel Castellanos). Distribué pour une utilisation dans les établissements d'enseignement, sans but lucratif, en respectant le travail des auteurs originaux.
