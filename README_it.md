> **Language / Lingua:** [English](README.md) | [Português](README_pt_BR.md) | [Español](README_es.md) | [Français](README_fr.md) | [Русский](README_ru.md) | [Български](README_bg.md) | [Українська](README_uk.md) | [Deutsch](README_de.md) | **Italiano**

---

# dfieldX — Analisi Numerica e Qualitativa di EDO 1D

**dfieldX** è un'applicazione desktop moderna e interattiva sviluppata con Java 21 e JavaFX per la simulazione numerica, la visualizzazione di campi di direzioni e l'analisi qualitativa di Equazioni Differenziali Ordinarie (EDO) scalari del primo ordine:

$$x' = \frac{dx}{dt} = f(t, x)$$

Ispirato al classico software didattico `dfield` originariamente sviluppato da **John C. Polking** (Rice University) per MATLAB, **dfieldX** è stato ricostruito da zero per offrire elevate prestazioni computazionali, una moderna interfaccia accelerata via GPU e una portabilità universale multipiattaforma (Linux, Windows e macOS).

---

## 📥 Installazione e Avvio Rapido

**dfieldX** è distribuito come file JAR eseguibile autonomo e richiede **Java 21 (LTS) o versioni successive**.

Se non hai Java installato o hai bisogno di una guida passo passo per il tuo sistema operativo (Windows, macOS o Linux), consulta le nostre guide all'installazione:

- 📖 **[Guida all'installazione (Italiano)](INSTALL_it.md)**

### Esecuzione dell'Applicazione

Una volta installato Java 21+, esegui dal tuo terminale:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Caratteristiche Principali

- **Campi di Direzioni ad Alta Definizione:** Rendering vettoriale fluido dei segmenti di pendenza con opzioni per la normalizzazione della lunghezza e la colorazione in base all'intensità della pendenza.
- **Integrazione Numerica Adattiva ad Alta Precisione:**
  * Risolutori a passo standard e adattivo (Dormand-Prince / Runge-Kutta di 4° e 5° ordine, RK4 standard ed Eulero).
  * Valutazione nativa nel dominio reale della funzione radice cubica reale ($\text{cbrt}$) su tutto $\mathbb{R}$, eliminando gli artefatti di fase dovuti ai tagli di ramo (branch-cut).
- **Analisi Qualitativa Automatizzata:**
  * Risoluzione numerica dei punti di equilibrio ($f(t, x^*) = 0$).
  * Classificazione della stabilità lineare (attrattori, repulsori e punti semi-stabili/sella).
  * Tracciamento automatico delle isocline tramite l'algoritmo di contornatura *Marching Squares*.
  * Diagramma ausiliario della linea di fase ($x' \text{ vs } x$) per equazioni autonome.
- **Galleria Integrata di Modelli Selezionati:** Una collezione di 22 modelli preconfigurati che coprono:
  * Biomatematica e Dinamica delle Popolazioni (Crescita logistica, effetto Allee, Gompertz, prelievo a sforzo costante e a quota fissa).
  * Meccanica Classica e Fisica (Caduta libera con attrito lineare e quadratico, circuiti RC, legge di raffreddamento di Newton).
  * Cinetica chimica e benchmark fondamentali per le EDO.
- **Slider dei Parametri in Tempo Reale:** Controlli interattivi tramite slider per la regolazione dinamica dei parametri e l'esplorazione continua delle biforcazioni.
- **Esportazione Vettoriale e Raster ad Alta Risoluzione:**
  * Grafica vettoriale scalabile pura (**SVG**) per figure di qualità tipografica e integrazione con LaTeX.
  * Esportazione raster ad alti DPI (**PNG**) con supporto per lo sfondo trasparente (canale alfa RGBA).
- **Fat JAR Universale Multipiattaforma:** Eseguibile autonomo che include i binari grafici nativi della piattaforma per **Linux (x86_64)**, **Windows (x86_64)** e **macOS (Apple Silicon serie M e Intel x86_64)**.

---

## 📋 Prerequisiti

Per eseguire il binario precompilato, è richiesto solo un **Java Runtime Environment (JRE) o JDK versione 21 o superiore**.

Verifica la tua installazione locale di Java eseguendo:
```bash
java -version
```

## Crediti e Licenza

Questo progetto è un'espansione didattica basata sul software originale dfield e pplane (copyright John C. Polking, Rice University) e sulle rispettive versioni Java (Joel Castellanos). Distribuito per l'uso nelle istituzioni educative, senza scopo di lucro, nel rispetto del lavoro degli autori originali.
