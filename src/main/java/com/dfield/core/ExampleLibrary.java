package com.dfield.core;

import com.dfield.util.I18n;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ExampleLibrary {

    private static final List<EquationExample> CUSTOM_EXAMPLES = new ArrayList<>();

    private ExampleLibrary() {}

    public static List<EquationExample> getPredefinedExamples() {
        List<EquationExample> list = new ArrayList<>();

        String catBio = I18n.get("gallery.cat.biomath");
        String catPhys = I18n.get("gallery.cat.physics");
        String catChem = I18n.get("gallery.cat.chemistry");
        String catMath = I18n.get("gallery.cat.math");

        // --- I. Biomatemática & Dinâmica Populacional ---
        // 1. Malthus
        list.add(new EquationExample(
                I18n.get("model.1.name"), catBio, I18n.get("model.1.desc"),
                "t", "x", "r * x", "r=0.5",
                0.0, 10.0, 0.0, 10.0
        ));
        // 2. Verhulst
        list.add(new EquationExample(
                I18n.get("model.2.name"), catBio, I18n.get("model.2.desc"),
                "t", "x", "r * x * (1 - x / K)", "r=0.8, K=5.0",
                0.0, 10.0, 0.0, 7.0
        ));
        // 3. Colheita Constante
        list.add(new EquationExample(
                I18n.get("model.3.name"), catBio, I18n.get("model.3.desc"),
                "t", "x", "r * x * (1 - x / K) - h", "r=1.0, K=8.0, h=1.5",
                0.0, 12.0, 0.0, 10.0
        ));
        // 4. Colheita por Esforço
        list.add(new EquationExample(
                I18n.get("model.4.name"), catBio, I18n.get("model.4.desc"),
                "t", "x", "r * x * (1 - x / K) - E * x", "r=1.0, K=10.0, E=0.3",
                0.0, 15.0, 0.0, 12.0
        ));
        // 5. Colheita Sazonal
        list.add(new EquationExample(
                I18n.get("model.5.name"), catBio, I18n.get("model.5.desc"),
                "t", "x", "r * x * (1 - x / K) - h0 * (1 + A * sin(w * t))", "r=0.8, K=6.0, h0=0.8, A=0.5, w=1.0",
                0.0, 20.0, 0.0, 8.0
        ));
        // 6. Efeito Allee
        list.add(new EquationExample(
                I18n.get("model.6.name"), catBio, I18n.get("model.6.desc"),
                "t", "x", "r * x * (1 - x / K) * (x / a - 1)", "r=0.5, a=1.5, K=6.0",
                0.0, 15.0, 0.0, 8.0
        ));
        // 7. Gompertz
        list.add(new EquationExample(
                I18n.get("model.7.name"), catBio, I18n.get("model.7.desc"),
                "t", "x", "-a * x * log(b * x)", "a=0.5, b=0.15",
                0.0, 12.0, 0.0, 10.0
        ));
        // 8. Von Bertalanffy
        list.add(new EquationExample(
                I18n.get("model.8.name"), catBio, I18n.get("model.8.desc"),
                "t", "x", "a * cbrt(x)^2 - b * x", "a=1.5, b=0.5",
                0.0, 15.0, 0.0, 35.0
        ));

        // --- II. Física, Termodinâmica & Mecânica ---
        // 9. Newton Resfriamento
        list.add(new EquationExample(
                I18n.get("model.9.name"), catPhys, I18n.get("model.9.desc"),
                "t", "T", "-k * (T - Tamb)", "k=0.4, Tamb=22.0",
                0.0, 12.0, 0.0, 100.0
        ));
        // 10. Newton Resfriamento Oscilante
        list.add(new EquationExample(
                I18n.get("model.10.name"), catPhys, I18n.get("model.10.desc"),
                "t", "T", "-k * (T - (T0 + A * sin(w * t)))", "k=0.5, T0=20.0, A=10.0, w=1.0",
                0.0, 15.0, 0.0, 45.0
        ));
        // 11. Arrasto Stokes
        list.add(new EquationExample(
                I18n.get("model.11.name"), catPhys, I18n.get("model.11.desc"),
                "t", "v", "g - (k / m) * v", "g=9.8, k=2.0, m=1.0",
                0.0, 6.0, 0.0, 10.0
        ));
        // 12. Arrasto Quadrático
        list.add(new EquationExample(
                I18n.get("model.12.name"), catPhys, I18n.get("model.12.desc"),
                "t", "v", "g - gamma * v^2", "g=9.8, gamma=0.05",
                0.0, 8.0, 0.0, 20.0
        ));
        // 13. Torricelli
        list.add(new EquationExample(
                I18n.get("model.13.name"), catPhys, I18n.get("model.13.desc"),
                "t", "h", "-c * sqrt(h)", "c=0.8",
                0.0, 8.0, 0.0, 5.0
        ));
        // 14. Circuito RL/RC
        list.add(new EquationExample(
                I18n.get("model.14.name"), catPhys, I18n.get("model.14.desc"),
                "t", "I", "-a * I + b * cos(w * t)", "a=1.0, b=5.0, w=2.0",
                0.0, 15.0, -6.0, 6.0
        ));
        // 15. Cadeia de Bateman
        list.add(new EquationExample(
                I18n.get("model.15.name"), catPhys, I18n.get("model.15.desc"),
                "t", "x", "l1 * N0 * exp(-l1 * t) - l2 * x", "l1=0.8, l2=0.2, N0=10.0",
                0.0, 10.0, 0.0, 8.0
        ));

        // --- III. Química & Cinética de Reações ---
        // 16. Reação Reversível
        list.add(new EquationExample(
                I18n.get("model.16.name"), catChem, I18n.get("model.16.desc"),
                "t", "x", "kf * (A0 - x) - kr * x", "kf=0.6, kr=0.2, A0=5.0",
                0.0, 8.0, 0.0, 6.0
        ));
        // 17. Reação Autocatalítica
        list.add(new EquationExample(
                I18n.get("model.17.name"), catChem, I18n.get("model.17.desc"),
                "t", "x", "k * x * (C0 - x)", "k=0.5, C0=6.0",
                0.0, 6.0, 0.0, 8.0
        ));
        // 18. Tanque CSTR
        list.add(new EquationExample(
                I18n.get("model.18.name"), catChem, I18n.get("model.18.desc"),
                "t", "C", "(q / V) * (Cin - C)", "q=2.0, V=10.0, Cin=8.0",
                0.0, 20.0, 0.0, 10.0
        ));

        // --- IV. Geometria, Singularidades & Bifurcações ---
        // 19. Linear t - x
        list.add(new EquationExample(
                I18n.get("model.19.name"), catMath, I18n.get("model.19.desc"),
                "t", "x", "t - x", "",
                -4.0, 4.0, -4.0, 4.0
        ));
        // 20. Riccati
        list.add(new EquationExample(
                I18n.get("model.20.name"), catMath, I18n.get("model.20.desc"),
                "t", "x", "x^2 - t", "",
                -3.0, 4.0, -3.0, 4.0
        ));
        // 21. Sela-Nó
        list.add(new EquationExample(
                I18n.get("model.21.name"), catMath, I18n.get("model.21.desc"),
                "t", "x", "r + x^2", "r=-1.0",
                -4.0, 4.0, -3.0, 3.0
        ));
        // 22. Blow-up
        list.add(new EquationExample(
                I18n.get("model.22.name"), catMath, I18n.get("model.22.desc"),
                "t", "x", "1 + x^2", "",
                -2.0, 2.0, -6.0, 6.0
        ));

        return list;
    }

    public static synchronized List<EquationExample> getAllExamples() {
        List<EquationExample> all = new ArrayList<>(getPredefinedExamples());
        all.addAll(CUSTOM_EXAMPLES);
        return Collections.unmodifiableList(all);
    }

    public static synchronized List<EquationExample> getByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("Todas") || category.equalsIgnoreCase("All")) {
            return getAllExamples();
        }
        String customCat = I18n.get("gallery.cat.custom");
        if (category.equalsIgnoreCase(customCat) || category.equalsIgnoreCase("Personalizados") || category.equalsIgnoreCase("Custom Models")) {
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
