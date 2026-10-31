> **Language / Idioma:** [English](README.md) | **Português** | [Español](README_es.md) | [Français](README_fr.md) | [Русский](README_ru.md) | [Български](README_bg.md) | [Українська](README_uk.md) | [Deutsch](README_de.md) | [Italiano](README_it.md)

---

# dfieldX — Análise Numérica e Qualitativa de EDOs 1D

O **dfieldX** é um aplicativo desktop moderno e interativo construído com Java 21 e JavaFX para simulação numérica, visualização de campos de direções e análise qualitativa de Equações Diferenciais Ordinárias (EDOs) escalares de primeira ordem:

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspirado no clássico software educacional `dfield` originalmente desenvolvido por **John C. Polking** (Rice University) para MATLAB, o **dfieldX** foi reconstruído do zero para oferecer alto desempenho computacional, uma interface moderna acelerada por GPU e portabilidade universal entre plataformas (Linux, Windows e macOS).

---

## 📥 Instalação e Início Rápido

O **dfieldX** é distribuído como um JAR executável autossuficiente e requer o **Java 21 (LTS) ou mais recente**.

Se você não tem o Java instalado ou precisa de um guia passo a passo para o seu sistema operacional (Windows, macOS ou Linux), consulte nossos guias de instalação:

- 📖 **[Guia de Instalação (Português)](INSTALL_pt_BR.md)**

### Executando o Aplicativo

Uma vez que o Java 21+ esteja instalado, execute a partir do seu terminal:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Principais Recursos

- **Campos de Direções em Alta Definição:** Renderização vetorial suave de segmentos de inclinação com opções para normalização de comprimento e coloração por intensidade de inclinação.
- **Integração Numérica Adaptativa de Alta Precisão:**
  * Solucionadores de passo padrão e adaptativo (Dormand-Prince / Runge-Kutta de 4ª e 5ª ordem, RK4 padrão e Euler).
  * Avaliação nativa no domínio real da função de raiz cúbica real ($\text{cbrt}$) em todo $\mathbb{R}$, eliminando artefatos de fase por quebra de ramo (branch-cut).
- **Análise Qualitativa Automatizada:**
  * Resolução numérica de pontos de equilíbrio ($f(t, x^*) = 0$).
  * Classificação de estabilidade linear (atratores, repulsores e pontos semi-estáveis/sela).
  * Traçado automático de isóclinas através do algoritmo de contorno *Marching Squares*.
  * Diagrama auxiliar de linha de fase ($x' \text{ vs } x$) para equações autônomas.
- **Galeria Integrada de Modelos Selecionados:** Uma coleção de 22 modelos pré-configurados abrangendo:
  * Biomatemática e Dinâmica Populacional (Crescimento logístico, efeito Allee, Gompertz, colheita com esforço constante e cota fixa).
  * Mecânica Clássica e Física (Queda livre com arrasto linear e quadrático, circuitos RC, lei do resfriamento de Newton).
  * Cinética química e EDOs fundamentais de referência.
- **Controles Deslizantes de Parâmetros em Tempo Real:** Controles interativos para ajuste dinâmico de parâmetros e exploração contínua de bifurcações.
- **Exportação Vetorial e Raster de Alta Resolução:**
  * Gráficos vetoriais escaláveis puros (**SVG**) para figuras com qualidade de publicação e integração com LaTeX.
  * Exportação raster de alto DPI (**PNG**) com suporte a fundo transparente (canal alfa RGBA).
- **Fat JAR Universal Multiplataforma:** Executável autossuficiente empacotando binários gráficos nativos da plataforma para **Linux (x86_64)**, **Windows (x86_64)** e **macOS (Apple Silicon M-Series e Intel x86_64)**.

---

## 📋 Pré-requisitos

Para executar o binário pré-compilado, apenas um **Java Runtime Environment (JRE) ou JDK versão 21 ou superior** é necessário.

Verifique sua instalação local do Java executando:
```bash
java -version
```

## Créditos e Licença

Este projeto é uma expansão educacional baseada nos softwares originais dfield e pplane (copyright John C. Polking, Rice University) e suas respectivas versões em Java (Joel Castellanos). Distribuído para uso em instituições educacionais, sem fins lucrativos, respeitando o trabalho dos autores originais.
