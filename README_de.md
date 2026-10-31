> **Language / Sprache:** [English](README.md) | [Português](README_pt_BR.md) | [Español](README_es.md) | [Français](README_fr.md) | [Русский](README_ru.md) | [Български](README_bg.md) | [Українська](README_uk.md) | **Deutsch** | [Italiano](README_it.md)

---

# dfieldX — Numerische und qualitative Analyse von 1D-ODEs

**dfieldX** ist eine moderne, interaktive Desktop-Anwendung, die mit Java 21 und JavaFX für die numerische Simulation, die Visualisierung von Richtungsfeldern und die qualitative Analyse von skalaren gewöhnlichen Differentialgleichungen (ODEs) erster Ordnung entwickelt wurde:

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspiriert von der klassischen Lernsoftware `dfield`, die ursprünglich von **John C. Polking** (Rice University) für MATLAB entwickelt wurde, wurde **dfieldX** von Grund auf neu geschrieben, um eine hohe Rechenleistung, eine moderne GPU-beschleunigte Benutzeroberfläche und eine universelle plattformübergreifende Portabilität (Linux, Windows und macOS) zu bieten.

---

## 📥 Installation & Schnellstart

**dfieldX** wird als eigenständige ausführbare JAR-Datei verteilt und erfordert **Java 21 (LTS) oder neuer**.

Wenn Sie Java nicht installiert haben oder eine Schritt-für-Schritt-Anleitung für Ihr Betriebssystem (Windows, macOS oder Linux) benötigen, lesen Sie unsere Installationsanleitungen:

- 📖 **[Installationsanleitung (Deutsch)](INSTALL_de.md)**

### Ausführen der Anwendung

Sobald Java 21+ installiert ist, führen Sie in Ihrem Terminal Folgendes aus:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Hauptmerkmale

- **Hochauflösende Richtungsfelder:** Reibungsloses Vektor-Rendering von Steigungssegmenten mit Optionen zur Längennormalisierung und Farbgebung nach Steigungsintensität.
- **Hochpräzise adaptive numerische Integration:**
  * Standard- und adaptive Schrittweiten-Löser (Dormand-Prince / Runge-Kutta 4. und 5. Ordnung, Standard-RK4 und Euler).
  * Native Auswertung der reellen Kubikwurzelfunktion ($\text{cbrt}$) im reellen Definitionsbereich über ganz $\mathbb{R}$, wodurch Phasenartefakte durch Zweigschnitte eliminiert werden.
- **Automatisierte qualitative Analyse:**
  * Numerische Berechnung von Gleichgewichtspunkten ($f(t, x^*) = 0$).
  * Klassifizierung der linearen Stabilität (Attraktoren, Repeller und halbstabile/Sattelpunkte).
  * Automatische Isoklinen-Verfolgung über den *Marching Squares*-Konturalgorithmus.
  * Zusätzliches Phasenliniendiagramm ($x' \text{ vs } x$) für autonome Gleichungen.
- **Integrierte Galerie ausgewählter Modelle:** Eine Sammlung von 22 vorkonfigurierten Modellen aus den Bereichen:
  * Biomathematik und Populationsdynamik (Logistisches Wachstum, Allee-Effekt, Gompertz, Ernte mit konstantem Aufwand und fester Quote).
  * Klassische Mechanik und Physik (Freier Fall mit linearer und quadratischer Reibung, RC-Schaltkreise, Newtonsches Abkühlungsgesetz).
  * Chemische Kinetik und grundlegende ODE-Benchmarks.
- **Echtzeit-Parameter-Schieberegler:** Interaktive Schieberegler zur dynamischen Parameteranpassung und kontinuierlichen Erkundung von Bifurkationen.
- **Hochauflösender Vektor- und Raster-Export:**
  * Reine skalierbare Vektorgrafiken (**SVG**) für publikationsreife Abbildungen und LaTeX-Integration.
  * Hochauflösender Raster-Export (**PNG**) mit Unterstützung für transparente Hintergründe (RGBA-Alphakanal).
- **Universelle plattformübergreifende Fat-JAR:** Eigenständige ausführbare Datei, die native Grafik-Binärdateien für **Linux (x86_64)**, **Windows (x86_64)** und **macOS (Apple Silicon M-Series und Intel x86_64)** bündelt.

---

## 📋 Voraussetzungen

Um die vorkompilierte Binärdatei auszuführen, ist nur ein **Java Runtime Environment (JRE) oder JDK Version 21 oder höher** erforderlich.

Überprüfen Sie Ihre lokale Java-Installation, indem Sie Folgendes ausführen:
```bash
java -version
```

## Credits und Lizenz

Dieses Projekt ist eine didaktische Erweiterung basierend auf der Originalsoftware dfield und pplane (Copyright John C. Polking, Rice University) und deren jeweiligen Java-Versionen (Joel Castellanos). Verteilt zur Nutzung in Bildungseinrichtungen, ohne Gewinnabsicht, unter Respektierung der Arbeit der ursprünglichen Autoren.
