> **Language / Idioma:** [English](README.md) | **Português** | [Español](README_es.md)

---
# dfieldX — Análise Numérica e Qualitativa de EDOs 1D

**dfieldX** é uma aplicação gráfica interativa moderna desenvolvida em Java 21 e JavaFX para a simulação numérica, traçado de campos de direções e análise qualitativa de Equações Diferenciais Ordinárias (EDOs) escalares de primeira ordem:

$$x' = \frac{dx}{dt} = f(t, x)$$

Inspirado na clássica ferramenta pedagógica `dfield` desenvolvida originalmente por **John C. Polking** (Rice University) para o ambiente MATLAB, o **dfieldX** foi reconstruído do zero para oferecer alto desempenho computacional, interface moderna com aceleração gráfica nativa e portabilidade universal multiplataforma (Linux, Windows e macOS).

---

## 📥 Instalação e Execução

O **dfieldX** é distribuído como um arquivo JAR executável e requer **Java 21 (LTS) ou superior**.

Caso você não tenha o Java instalado ou deseje instruções detalhadas para o seu sistema operacional (Windows, macOS ou Linux), consulte os guias de instalação:

- 📖 **[Guia de Instalação (Português)](INSTALL_pt.md)**

### Executando o Programa

Com o Java 21+ instalado, execute pelo terminal:

```bash
java -jar dfieldX.jar
```

---

## 🚀 Destaques e Funcionalidades

* **Campos de Direções em Alta Definição:** Renderização vetorial fluida de vetores tangentes com opções de normalização de comprimento e gradiente de intensidade de inclinação.
* **Integração Numérica Adaptativa de Alta Precisão:**
  * Métodos clássicos e de passo adaptativo (Dormand-Prince / Runge-Kutta 4ª e 5ª ordem, Euler e RK4 padrão).
  * Suporte computacional nativo à função raiz cúbica real ($\text{cbrt}$) em todo o domínio real $\mathbb{R}$, evitando descontinuidades de fase.
* **Análise Qualitativa Automatizada:**
  * Localização numérica de equilíbrios locais ($f(t, x^*) = 0$).
  * Classificação de estabilidade linear (atratores, repulsores e pontos de sela/semi-estáveis).
  * Traçado automático de isóclinas via algoritmo *Marching Squares*.
  * Diagrama de linha de fase auxiliar ($x' \text{ vs } x$) para equações autônomas.
* **Galeria Didática Integrada:** Coleção de 22 modelos pré-configurados abrangendo:
  * Biomatemática e Dinâmica Populacional (Logístico, Allee, Gompertz, Colheita com esforço constante e quota fixa).
  * Física e Mecânica Clássica (Queda livre com resistência linear e quadrática, Circuito RC, Resfriamento de Newton).
  * Cinética Química e Modelagem Geral.
* **Exploração Paramétrica Contínua:** Janela dedicada de controles deslizantes (*sliders*) para ajuste em tempo real de coeficientes e parâmetros livres da equação.
* **Exportação Vetorial e Raster de Alta Resolução:**
  * Exportação gráfica para formatos **SVG** (vetorial puro escalável para artigos e LaTeX).
  * Exportação raster em **PNG** em alta definição com suporte a fundo transparente (canal alfa RGBA).
* **Binário Universal Cross-Platform:** Empacotamento em um único executável *fat JAR* contendo bibliotecas nativas de aceleração gráfica para **Linux (x86_64)**, **Windows (x86_64)** e **macOS (Apple Silicon M-Series e Intel x86_64)**.

---

## 📋 Pré-requisitos

Para executar o binário pré-compilado, é necessário apenas ter o **Java Runtime Environment (JRE) ou JDK versão 21 ou superior** instalado no sistema operacional.

Para verificar a instalação do Java, execute no terminal:
```bash
java -version
```

## 🆕 Novidades da Versão 1.1.0
- **Internacionalização (i18n):** Suporte completo e dinâmico adicionado para Espanhol (es).
- **Interface:** Remoção de títulos acadêmicos para um design mais universal.
- **Motor JavaFX:** Ajuste no redimensionamento de janelas e carregamento dinâmico do guia de modelos.
