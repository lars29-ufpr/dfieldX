---
title: 'dfieldX and pplaneX: Modern Open-Source Java Tools for Direction Fields and Phase Plane Analysis'
tags:
  - ordinary differential equations
  - dynamical systems
  - phase portrait
  - direction fields
  - visualization
  - mathematics education
  - java
authors:
  - name: Luiz Antonio Ribeiro de Santana
    affiliation: 1
affiliations:
  - name: Departamento de Matemática, Universidade Federal do Paraná (UFPR), Curitiba, PR, Brazil
    index: 1
date: 20 October 2026
bibliography: paper.bib
---

# Summary

`dfieldX` and `pplaneX` are modern, cross-platform, open-source applications designed for the interactive qualitative and numerical analysis of ordinary differential equations (ODEs) and two-dimensional planar dynamical systems. Developed in Java 21 and JavaFX, they represent an independent, clean-room modernization of the classical educational software packages originally conceived by John Polking [@polking1995ordinary].

`dfieldX` visualizes direction fields and integral curves of first-order scalar ODEs, while `pplaneX` visualizes vector fields, phase portraits, nullclines, equilibrium points, and basins of attraction for planar systems. Both tools operate natively across Linux, Windows, and macOS without requiring proprietary mathematical platforms, providing embedded pedagogical documentation with MathJax rendering, curated canonical model galleries, and publication-grade vector graphic export for instructional and scientific materials.

# Statement of need

Qualitative geometric analysis is indispensable for understanding nonlinear differential equations and dynamical systems, particularly where analytical closed-form solutions do not exist. For decades, undergraduate mathematics, engineering, and biosciences curricula relied extensively on software tools that enabled students to click directly onto a direction field to compute and trace solution trajectories in real time.

However, recent technological transitions have created an acute software deficit in university instruction:
1. Historical Java applets embedded in browsers have been rendered completely obsolete and unexecutable due to modern browser security restrictions and the removal of the Java plugin architecture.
2. Legacy MATLAB-based scripts [@polking1995ordinary] require expensive commercial software licenses and institutional access, creating severe accessibility barriers for public universities, secondary schools, and self-directed learners globally.
3. While state-of-the-art script-based libraries in Python (`SciPy`, `matplotlib`) [@hunter2007matplotlib] and Julia are outstanding for research computing, using them in introductory or service-level ODE classrooms imposes heavy cognitive overhead: students must troubleshoot programming syntax, environment managers, and script errors rather than engaging directly with dynamical concepts.

`dfieldX` and `pplaneX` address this pedagogical gap by providing zero-configuration, standalone executable tools that restore immediate mouse-driven exploration, combining modern runtime stability with rigorous numerical methods and comprehensive domain-specific model catalogs.

# State of the field

Existing computational environments for ODE exploration present substantial trade-offs between immediacy, analytical power, and maintainability:

- **Legacy Tools:** The original `dfield` and `pplane` software packages developed by John Polking [@polking1995ordinary] established the mouse-driven standard for ODE pedagogy. Nevertheless, reliance on proprietary runtimes or deprecated Java Web Start / browser plugins has left them largely inaccessible to modern users.
- **Web-Based Plotters:** Lightweight web visualizers (such as HTML5 canvas widgets or interactive GeoGebra/Desmos worksheets) offer fast browser execution. However, they typically lack advanced numerical schemes (such as adaptive Runge-Kutta methods), automated Jacobian linearization at equilibrium points, saddle separatrix manifold tracing, and publication-grade vector graphics export.
- **Specialized Research Suites:** High-level packages such as `XPPAUT` or `MATCONT` provide comprehensive bifurcation analysis and continuation algorithms, but their steep learning curve and idiosyncratic interfaces render them challenging for introductory differential equations and mathematical biology courses.

`dfieldX` and `pplaneX` occupy a vital middle ground: they deliver the immediate, intuitive accessibility of classical desktop utilities alongside a modern, reliable Java 21 / OpenJFX architecture [@openjfx], native vector export, and bilingual pedagogical guides.

# Software design

Both applications are engineered following clean software architecture principles, ensuring high performance, platform independence, and modular extensibility:

- **Runtime and Portability:** Implemented in Java 21 (LTS) using the OpenJFX 21 platform [@openjfx]. Packaged as self-contained fat JAR files, both tools execute across Linux, Windows, and macOS with zero configuration.
- **Engine Decoupling:** The numerical integration core, expression evaluation engine, and mathematical root-finding modules are strictly decoupled from the UI rendering layer. Computationally intensive orbit integrations execute smoothly without blocking interface responsiveness.
- **Adaptive Numerical Solvers:** Trajectories are calculated using standard numerical integration schemes, including classical Runge-Kutta 4th order (RK4) and adaptive step-size Runge-Kutta-Fehlberg (RKF45), ensuring accurate tracking across stiff and rapid dynamical transitions.
- **Equilibrium Analysis and Manifold Tracing:** `pplaneX` provides numerical equilibrium location via multi-dimensional Newton-Raphson routines, computes the analytical Jacobian matrix, evaluates eigenvalues for spectral classification [@perko2001differential; @strogatz2018nonlinear], and automatically traces stable and unstable invariant manifolds (separatrices) emanating from saddle points to delineate basin boundaries [@guckenheimer1983nonlinear].
- **Publication-Grade Graphics Export:** Canvases can be exported directly to high-resolution raster formats (PNG) and vector graphics (Scalable Vector Graphics, SVG).

- **Internationalization (i18n):** Full UI and documentation support for both English and Brazilian Portuguese via Java `ResourceBundle`.

![Interactive direction field, numerical orbits, isoclines, and embedded pedagogical tutorial guide for the constant harvesting model in `dfieldX`.\label{fig:dfieldx}](figure1.png)

### Built-in Model Catalogs and Pedagogical Reference Guides

A defining pedagogical strength of both tools is the inclusion of embedded, curated model libraries accompanied by theoretical background, suggested numerical exploration parameters, and primary literature citations:

- **First-Order Scalar Catalog (`dfieldX`):** Features 22 models across ecology, epidemiology, physics, chemistry, and calculus. Systems include Malthusian and Verhulst logistic growth [@verhulst1838notice], bioeconomic harvesting models [@clark1990mathematical], seasonal harvesting [@kot2001elements], critical Allee effects [@allee1938social], Von Bertalanffy allometric growth [@vonbertalanffy1957quantitative], Newton cooling and Torricelli drainage [@boyce2021elementary], RL/RC electrical circuits [@nilsson2014electric], Bateman radioactive decay cascades [@bateman1910solution], autocatalytic and reversible chemical reactions [@atkins2014physical; @epstein1998introduction], and saddle-node normal forms and finite-time blow-up dynamics [@simmons2016differential; @hirsch2013differential; @polyanin2003handbook].
- **Planar Systems Catalog (`pplaneX`):** Features 11 foundational systems including Lotka-Volterra predator-prey [@lotka1925elements; @volterra1926variazioni; @murray2002mathematical], interspecific competition [@begon2006ecology], FitzHugh-Nagumo and neuron action potentials [@fitzhugh1961impulses; @nagumo1962active; @izhikevich2007dynamical], Kermack-McKendrick epidemic dynamics [@kermack1927contribution; @brauer2012mathematical], Van der Pol relaxation oscillators [@vanderpol1926relaxation], non-linear damped pendula [@marion2004classical], autonomous Duffing oscillators [@duffing1918erzwungene], and Strogatz's coupled linear relationship models [@strogatz2018nonlinear].

![Phase portrait, nullclines, limit cycle, interactive parameter tuning, and Jacobian critical point spectral analysis for the FitzHugh-Nagumo model in `pplaneX`.\label{fig:pplanex}](figure2.png)


# Research impact statement

`dfieldX` and `pplaneX` are actively utilized in undergraduate mathematics and mathematical biology coursework at the Universidade Federal do Paraná (UFPR), serving as foundational visualization engines for introductory ODE courses and mathematical modeling seminars. By eliminating proprietary software dependencies and providing standalone, cross-platform binaries, they restore equitable access for students and instructors across diverse institutional settings. Furthermore, their vector export capabilities enable researchers and educators to generate publication-quality figures directly for articles, monographs, and instructional textbooks.

# AI usage disclosure

An AI assistant was used to assist in drafting and refining sections of this manuscript and documentation scaffolding. All conceptual formulation, code verification, mathematical equations, and scientific materials were supervised and validated by the author.

# Acknowledgements

This paper is dedicated to the memory of Professor John C. Polking (1932–2026), Emeritus Professor of Mathematics at Rice University, whose visionary design of `dfield` and `pplane` transformed ordinary differential equations pedagogy worldwide.

The author also honors the enduring pedagogical legacy of Professor Ary Orozimbo Chiacchio, retired professor at the Instituto de Matemática, Estatística e Computação Científica of the Universidade Estadual de Campinas (IMECC/Unicamp), whose mentorship and dedication to mathematical education profoundly shaped the author's academic path. Finally, the author acknowledges the Department of Mathematics at the Universidade Federal do Paraná (DMAT/UFPR) for continuous institutional support.

# References
