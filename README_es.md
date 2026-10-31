> **Language / Idioma:** [English](README.md) | [Português](README_pt_BR.md) | **Español** | [Français](README_fr.md) | [Русский](README_ru.md) | [Български](README_bg.md) | [Українська](README_uk.md) | [Deutsch](README_de.md) | [Italiano](README_it.md)

---

# dfieldX — Análisis Numérico y Cualitativo de EDOs 1D

**dfieldX** es una aplicación de escritorio moderna e interactiva construida con Java 21 y JavaFX para simulación numérica, visualización de campos de direcciones y análisis cualitativo de Ecuaciones Diferenciales Ordinarias (EDOs) escalares de primer orden:

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspirado en el clásico software educativo `dfield` desarrollado originalmente por **John C. Polking** (Rice University) para MATLAB, **dfieldX** fue reconstruido desde cero para ofrecer un alto rendimiento computacional, una interfaz moderna acelerada por GPU y portabilidad universal multiplataforma (Linux, Windows y macOS).

---

## 📥 Instalación e Inicio Rápido

**dfieldX** se distribuye como un JAR ejecutable independiente y requiere **Java 21 (LTS) o más reciente**.

Si no tiene Java instalado o necesita una guía paso a paso para su sistema operativo (Windows, macOS o Linux), consulte nuestras guías de instalación:

- 📖 **[Guía de Instalación (Español)](INSTALL_es.md)**

### Ejecutar la Aplicación

Una vez que Java 21+ esté instalado, ejecute desde su terminal:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Características Principales

- **Campos de Direcciones de Alta Definición:** Renderizado vectorial suave de segmentos de pendiente con opciones para normalización de longitud y coloración por intensidad de pendiente.
- **Integración Numérica Adaptativa de Alta Precisión:**
  * Solucionadores de paso estándar y adaptativo (Dormand-Prince / Runge-Kutta de 4º y 5º orden, RK4 estándar y Euler).
  * Evaluación nativa en el dominio real de la función raíz cúbica real ($\text{cbrt}$) en todo $\mathbb{R}$, eliminando artefactos de fase por cortes de rama (branch-cut).
- **Análisis Cualitativo Automatizado:**
  * Resolución numérica de puntos de equilibrio ($f(t, x^*) = 0$).
  * Clasificación de estabilidad lineal (atractores, repulsores y puntos semi-estables/silla).
  * Trazado automático de isoclinas a través del algoritmo de contorno *Marching Squares*.
  * Diagrama auxiliar de línea de fase ($x' \text{ vs } x$) para ecuaciones autónomas.
- **Galería Integrada de Modelos Seleccionados:** Una colección de 22 modelos preconfigurados que abarcan:
  * Biomatemática y Dinámica Poblacional (Crecimiento logístico, efecto Allee, Gompertz, recolección con esfuerzo constante y cuota fija).
  * Mecánica Clásica y Física (Caída libre con resistencia lineal y cuadrática, circuitos RC, ley de enfriamiento de Newton).
  * Cinética química y EDOs fundamentales de referencia.
- **Controles Deslizantes de Parámetros en Tiempo Real:** Controles interactivos para el ajuste dinámico de parámetros y la exploración continua de bifurcaciones.
- **Exportación Vectorial y Raster de Alta Resolución:**
  * Gráficos vectoriales escalables puros (**SVG**) para figuras con calidad de publicación e integración con LaTeX.
  * Exportación raster de alto DPI (**PNG**) con soporte para fondo transparente (canal alfa RGBA).
- **Fat JAR Universal Multiplataforma:** Ejecutable independiente que empaqueta binarios gráficos nativos de la plataforma para **Linux (x86_64)**, **Windows (x86_64)** y **macOS (Apple Silicon M-Series e Intel x86_64)**.

---

## 📋 Requisitos Previos

Para ejecutar el binario precompilado, solo se requiere un **Java Runtime Environment (JRE) o JDK versión 21 o superior**.

Verifique su instalación local de Java ejecutando:
```bash
java -version
```

## Créditos y Licencia

Este proyecto es una expansión educativa basada en los softwares originales dfield y pplane (copyright John C. Polking, Rice University) y sus respectivas versiones en Java (Joel Castellanos). Distribuido para su uso en instituciones educativas, sin fines de lucro, respetando el trabajo de los autores originales.
