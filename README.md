> **Language / Idioma:** **English** | [Português](README.pt-BR.md)

---

# dfieldX — Numerical and Qualitative Analysis of 1D ODEs

**dfieldX** is a modern, interactive desktop application built with Java 21 and JavaFX for numerical simulation, direction field visualization, and qualitative analysis of first-order scalar Ordinary Differential Equations (ODEs):

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspired by the classic educational software `dfield` originally developed by **John C. Polking** (Rice University) for MATLAB, **dfieldX** was rebuilt from scratch to deliver high computational performance, a modern GPU-accelerated interface, and universal cross-platform portability (Linux, Windows, and macOS).

---

## 🚀 Key Features

* **High-Definition Direction Fields:** Smooth vector rendering of slope segments with options for length normalization and slope-intensity coloring.
* **High-Precision Adaptive Numerical Integration:**
  * Standard and adaptive step-size solvers (Dormand-Prince / Runge-Kutta 4th and 5th order, standard RK4, and Euler).
  * Native real-domain evaluation of the real cube root function ($\text{cbrt}$) across all $\mathbb{R}$, eliminating branch-cut phase artifacts.
* **Automated Qualitative Analysis:**
  * Numerical equilibrium solving ($f(t, x^*) = 0$).
  * Linear stability classification (attractors, repellers, and semi-stable/saddle points).
  * Automatic isocline tracing via the *Marching Squares* contouring algorithm.
  * Auxiliary phase-line diagram ($x' \text{ vs } x$) for autonomous equations.
* **Curated Built-in Model Gallery:** A collection of 22 preconfigured models spanning:
  * Biomathematics and Population Dynamics (Logistic growth, Allee effect, Gompertz, constant-effort and fixed-quota harvesting).
  * Classical Mechanics and Physics (Free fall with linear and quadratic drag, RC circuits, Newton's law of cooling).
  * Chemical kinetics and foundational ODE benchmarks.
* **Real-Time Parameter Sliders:** Interactive slider controls for dynamic parameter tuning and continuous bifurcation exploration.
* **Vector and High-Resolution Raster Export:**
  * Pure scalable vector graphics (**SVG**) for publication-grade figures and LaTeX integration.
  * High-DPI raster export (**PNG**) with transparent background (RGBA alpha channel) support.
* **Universal Cross-Platform Fat JAR:** Self-contained executable bundling native platform graphics binaries for **Linux (x86_64)**, **Windows (x86_64)**, and **macOS (Apple Silicon M-Series and Intel x86_64)**.

---

## 📋 Prerequisites

To run the pre-built binary, only a **Java Runtime Environment (JRE) or JDK version 21 or higher** is required.

Verify your local Java installation by running:
```bash
java -version
