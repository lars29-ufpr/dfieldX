package com.dfield.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ExampleLibrary {

    private static final List<EquationExample> CUSTOM_EXAMPLES = new ArrayList<>();

    private ExampleLibrary() {}

    public static List<EquationExample> getPredefinedExamples() {
        List<EquationExample> list = new ArrayList<>();

                // --- 0. Sistemas Não Lineares & Bifurcações 1D ---
        list.add(new EquationExample(
                "Bifurcação Sela-Nó (Saddle-Node / Fold)", "Sistemas Não Lineares & Bifurcações",
                "Protótipo canônico de aniquilação e criação de equilíbrios. Para r > 0 existem dois equilíbrios (um estável e um instável); em r = 0 colidem; para r < 0 não há equilíbrios.",
                "t", "x", "r - x^2", "r=1.0",
                0.0, 6.0, -3.0, 3.0
        ));
        list.add(new EquationExample(
                "Bifurcação Transcrítica", "Sistemas Não Lineares & Bifurcações",
                "Troca mútua de estabilidade entre dois pontos fixos ao se cruzarem. Em r = 0, a origem e o ponto x* = r colidem e invertem seus papéis de atrator e repulsor.",
                "t", "x", "r * x - x^2", "r=1.0",
                0.0, 6.0, -2.0, 3.0
        ));
        list.add(new EquationExample(
                "Bifurcação Forquilha Supercrítica (Pitchfork)", "Sistemas Não Lineares & Bifurcações",
                "Quebra espontânea de simetria. Para r <= 0 a origem é o único atrator; para r > 0 a origem torna-se repulsora e bifurca em dois atratores simétricos x* = ±sqrt(r).",
                "t", "x", "r * x - x^3", "r=1.0",
                0.0, 6.0, -2.5, 2.5
        ));
        list.add(new EquationExample(
                "Interruptor Genético / Biestabilidade", "Sistemas Não Lineares & Bifurcações",
                "Modelo clássico de ativação biológica com comutação e histerese. Exibe simultaneamente dois estados estáveis separados por uma bacia de atração instável.",
                "t", "x", "s - x + (x^2) / (1 + x^2)", "s=-0.1",
                0.0, 10.0, -1.0, 3.0
        ));

        // --- 1. Dinâmica Populacional e Biomatemática ---
        list.add(new EquationExample(
                "Crescimento Logístico Clássico", "Dinâmica Populacional",
                "Modelo padrão com saturação e capacidade de suporte.",
                "t", "x", "r * x * (1 - x / K)", "r=0.8, K=5.0",
                0.0, 10.0, 0.0, 7.0
        ));
        list.add(new EquationExample(
                "Efeito Allee (Limiar de Extinção)", "Dinâmica Populacional",
                "Bistabilidade crítica com nível mínimo viável populacional (a).",
                "t", "x", "r * x * (x / a - 1) * (1 - x / K)", "r=0.5, a=1.5, K=6.0",
                0.0, 15.0, 0.0, 8.0
        ));
        list.add(new EquationExample(
                "Colheita com Esforço Constante (Linear)", "Dinâmica Populacional",
                "Extração linear proporcional à densidade populacional.",
                "t", "x", "r * x * (1 - x / K) - E * x", "r=1.0, K=10.0, E=0.3",
                0.0, 15.0, 0.0, 12.0
        ));
        list.add(new EquationExample(
                "Colheita com Quota Constante", "Dinâmica Populacional",
                "Retirada de taxa fixa com bifurcação sela-nó.",
                "t", "x", "r * x * (1 - x / K) - Q", "r=1.0, K=8.0, Q=1.5",
                0.0, 12.0, 0.0, 10.0
        ));
        list.add(new EquationExample(
                "Colheita com Saturação (Holling Tipo II)", "Dinâmica Populacional",
                "Esforço de extração racional com tempo de manuseio.",
                "t", "x", "r * x * (1 - x / K) - (E * x) / (c + x)", "r=1.0, K=10.0, E=2.0, c=1.5",
                0.0, 20.0, 0.0, 12.0
        ));
        list.add(new EquationExample(
                "Spruce Budworm (Holling Tipo III)", "Dinâmica Populacional",
                "Eclosão súbita de pragas com predação sigmoidal e histerese.",
                "t", "x", "r * x * (1 - x / K) - (B * x^2) / (A^2 + x^2)", "r=0.48, K=12.0, B=1.0, A=1.0",
                0.0, 40.0, 0.0, 14.0
        ));
        list.add(new EquationExample(
                "Inibição por Densidade (Holling Tipo IV)", "Dinâmica Populacional",
                "Defesa coletiva da presa / cinética de Andrews-Haldane.",
                "t", "x", "r * x * (1 - x / K) - (c * x) / (a + b * x + x^2)", "r=1.0, K=10.0, c=2.5, a=1.0, b=0.2",
                0.0, 25.0, 0.0, 11.0
        ));
        list.add(new EquationExample(
                "Gompertz (Cinética Tumoral)", "Dinâmica Populacional",
                "Crescimento assimétrico com decaimento logarítmico da taxa.",
                "t", "x", "-r * x * log(x / K)", "r=0.5, K=8.0",
                0.0, 12.0, 0.0, 10.0
        ));
        list.add(new EquationExample(
                "Crescimento de Smith (Efeito de Aglomeração)", "Dinâmica Populacional",
                "Ajuste da logística para saturação de biomassa em culturas.",
                "t", "x", "r * x * (K - x) / (K + c * x)", "r=0.8, K=6.0, c=1.2",
                0.0, 15.0, 0.0, 8.0
        ));
        list.add(new EquationExample(
                "Crescimento Corpóreo de von Bertalanffy", "Dinâmica Populacional",
                "Balanço anabólico/catabólico individual com comprimento assintótico.",
                "t", "L", "k * (Linf - L)", "k=0.3, Linf=50.0",
                0.0, 15.0, 0.0, 60.0
        ));

        // --- 2. Física, Mecânica e Circuitos ---
        list.add(new EquationExample(
                "Lei de Resfriamento de Newton", "Física e Circuitos",
                "Transferência de calor linear para meio ambiente.",
                "t", "T", "-k * (T - Tamb)", "k=0.4, Tamb=22.0",
                0.0, 12.0, 0.0, 100.0
        ));
        list.add(new EquationExample(
                "Queda com Resistência do Ar Quadrática", "Física e Circuitos",
                "Velocidade terminal em regime turbulento.",
                "t", "v", "g - k * v^2", "g=9.8, k=0.05",
                0.0, 8.0, 0.0, 20.0
        ));
        list.add(new EquationExample(
                "Circuito RC com Fonte Alternada", "Física e Circuitos",
                "Resposta forçada senoidal em regime transitório.",
                "t", "V", "-a * V + b * sin(w * t)", "a=1.0, b=5.0, w=2.0",
                0.0, 15.0, -6.0, 6.0
        ));

        // --- 3. Economia e Finanças ---
        list.add(new EquationExample(
                "Amortização e Acumulação Financeira", "Finanças e Economia",
                "Juros contínuos e taxa constante de aporte ou saque.",
                "t", "M", "r * M - S", "r=0.08, S=12.0",
                0.0, 20.0, 0.0, 250.0
        ));
        list.add(new EquationExample(
                "Ajuste de Preços de Walras/Marshall", "Finanças e Economia",
                "Preço de equilíbrio impulsionado pelo excesso de demanda.",
                "t", "P", "alpha * (a - b * P)", "alpha=0.6, a=100.0, b=2.5",
                0.0, 10.0, 0.0, 60.0
        ));

        // --- 4. Modelos Climáticos e Geociências ---
        list.add(new EquationExample(
                "Balanço Radiativo de Budyko-Sellers (0D)", "Clima e Geociências",
                "Bistabilidade climática planetária (Terra estufa vs. Snowball Earth).",
                "t", "T", "S0 * (1 - (a0 - a1 * (T - 273.15) / 30.0)) - (A + B * T)",
                "S0=340.0, a0=0.5, a1=0.2, A=200.0, B=2.0",
                0.0, 50.0, 220.0, 320.0
        ));
        list.add(new EquationExample(
                "Ciclo de Carbono Atmosférico (Balanço 1D)", "Clima e Geociências",
                "Acúmulo de CO2 com emissão constante e absorção oceânica.",
                "t", "C", "E - k * (C - C0)", "E=4.0, k=0.05, C0=280.0",
                0.0, 50.0, 250.0, 450.0
        ));

        // --- 5. EDOs Clássicas e Fenômenos Matemáticos ---
        list.add(new EquationExample(
                "Equação de Riccati Clássica", "Fenômenos Matemáticos",
                "EDO de Riccati com separatriz não linear (padrão histórico dfield).",
                "t", "x", "x^2 - t", "",
                -2.0, 4.0, -2.0, 4.0
        ));
        list.add(new EquationExample(
                "Explosão em Tempo Finito (Blow-up)", "Fenômenos Matemáticos",
                "Soluções tangenciais que escapam para infinito em t finito.",
                "t", "x", "1 + x^2", "",
                -2.0, 2.0, -8.0, 8.0
        ));
        list.add(new EquationExample(
                "Falha de Lipschitz (Não Unicidade)", "Fenômenos Matemáticos",
                "Múltiplas curvas de solução emanando da mesma condição inicial.",
                "t", "x", "3 * x^(2/3)", "",
                0.0, 4.0, 0.0, 8.0
        ));

        return list;
    }

    public static synchronized List<EquationExample> getAllExamples() {
        List<EquationExample> all = new ArrayList<>(getPredefinedExamples());
        all.addAll(CUSTOM_EXAMPLES);
        return Collections.unmodifiableList(all);
    }

    public static synchronized List<EquationExample> getByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("Todas") || category.equalsIgnoreCase("Todos")) {
            return getAllExamples();
        }
        if (category.equalsIgnoreCase("Personalizados") || category.equalsIgnoreCase("Custom")) {
            return getCustomExamples();
        }
        return getAllExamples().stream()
                .filter(e -> e.category().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static synchronized void addCustomExample(EquationExample example) {
        CUSTOM_EXAMPLES.add(example);
    }

    public static synchronized List<EquationExample> getCustomExamples() {
        return Collections.unmodifiableList(new ArrayList<>(CUSTOM_EXAMPLES));
    }
}
