> **Idioma / Language:** **Español** | [Français](README_fr.md) | [English](README.md) | [Português](README.pt-BR.md)

---

# dfieldX — Análisis Numérico y Cualitativo de EDOs 1D

**dfieldX** es una aplicación de escritorio moderna e interactiva construida con Java 21 y JavaFX para la simulación numérica, visualización de campos de direcciones y análisis cualitativo de Ecuaciones Diferenciales Ordinarias (EDOs) escalares de primer orden:

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspirado en el clásico software educativo `dfield` desarrollado originalmente por **John C. Polking** (Rice University) para MATLAB, **dfieldX** fue reconstruido desde cero para ofrecer un alto rendimiento computacional, una interfaz moderna acelerada por GPU y portabilidad universal multiplataforma (Linux, Windows y macOS).

---

## 📥 Instalación e Inicio Rápido

**dfieldX** se distribuye como un ejecutable JAR autónomo y requiere **Java 21 (LTS) o superior**.

Si no tiene Java instalado o necesita una guía paso a paso para su sistema operativo (Windows, macOS o Linux), consulte nuestras guías de instalación:

- 📖 **[Guía de Instalación (Español)](INSTALL_es.md)**

### Ejecutar la Aplicación

Una vez instalado Java 21+, ejecute desde su terminal:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Características Principales

- **Campos de Direcciones de Alta Definición:** Renderizado vectorial suave de los segmentos de pendiente con opciones para la normalización de longitud y coloración por intensidad de pendiente.
- **Integración Numérica Adaptativa de Alta Precisión:**
  * Solucionadores de paso estándar y adaptativo (Dormand-Prince / Runge-Kutta de 4to y 5to orden, RK4 estándar y Euler).
  * Evaluación nativa en el dominio real de la función raíz cúbica real ($\text{cbrt}$) en todo $\mathbb{R}$, eliminando los artefactos de fase por corte de rama.
- **Análisis Cualitativo Automatizado:**
  * Cálculo numérico de puntos de equilibrio ($f(t, x^*) = 0$).
  * Clasificación de estabilidad lineal (atractores, repulsores y puntos semiestables/silla).
  * Trazado automático de isoclinas mediante el algoritmo de contorno *Marching Squares*.
  * Diagrama auxiliar de línea de fase ($x' \text{ vs } x$) para ecuaciones autónomas.
- **Galería de Modelos Integrada:** Una colección de 22 modelos preconfigurados que abarcan:
  * Biomatemáticas y Dinámica de Poblaciones (Crecimiento logístico, efecto Allee, Gompertz, cosecha de esfuerzo constante y cuota fija).
  * Mecánica Clásica y Física (Caída libre con fricción lineal y cuadrática, circuitos RC, ley de enfriamiento de Newton).
  * Cinética química y modelos de referencia en EDOs.
- **Deslizadores de Parámetros en Tiempo Real:** Controles deslizantes interactivos para el ajuste dinámico de parámetros y exploración continua de bifurcaciones.
- **Exportación Vectorial y Raster de Alta Resolución:**
  * Gráficos vectoriales escalables puros (**SVG**) para figuras con calidad de publicación e integración con LaTeX.
  * Exportación raster de altos DPI (**PNG**) con soporte de fondo transparente (canal alfa RGBA).
- **Fat JAR Universal Multiplataforma:** Ejecutable autónomo que empaqueta los binarios gráficos nativos para **Linux (x86_64)**, **Windows (x86_64)** y **macOS (Apple Silicon Serie M e Intel x86_64)**.

---

## 📋 Prerrequisitos

Para ejecutar el binario precompilado, solo se requiere un **Entorno de Ejecución de Java (JRE) o JDK versión 21 o superior**.

Verifique su instalación local de Java ejecutando:
```bash
java -version
```
```

