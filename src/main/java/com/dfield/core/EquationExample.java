package com.dfield.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record EquationExample(
        String name,
        String category,
        String description,
        String independentVar,
        String dependentVar,
        String expression,
        String defaultParameters,
        double tMin,
        double tMax,
        double xMin,
        double xMax,
        double defaultT0,
        double defaultX0,
        boolean isCustom
) {
    // Construtor didático (11 parâmetros)
    public EquationExample(String name, String category, String description,
                           String independentVar, String dependentVar, String expression,
                           String defaultParameters, double tMin, double tMax,
                           double xMin, double xMax) {
        this(name, category, description, independentVar, dependentVar, expression,
             defaultParameters, tMin, tMax, xMin, xMax, tMin, (xMin + xMax) / 2.0, false);
    }

    // Construtor para modelos customizados gerados pelo diálogo (13 parâmetros)
    public EquationExample(String name, String category, String description,
                           String independentVar, String dependentVar, String expression,
                           String defaultParameters, double tMin, double tMax,
                           double xMin, double xMax, double defaultT0, double defaultX0) {
        this(name, category, description, independentVar, dependentVar, expression,
             defaultParameters, tMin, tMax, xMin, xMax, defaultT0, defaultX0, true);
    }

    // Aliases para máxima compatibilidade
    public String indepVar() { return independentVar; }
    public String depVar() { return dependentVar; }
    public String equation() { return expression; }
    public String parameters() { return defaultParameters; }

    // Converte a string "a=1.0, b=2.0" para Map caso o diálogo ou visualizador necessite
    public Map<String, Double> parseParameters() {
        if (defaultParameters == null || defaultParameters.isBlank()) {
            return Collections.emptyMap();
        }
        Map<String, Double> map = new LinkedHashMap<>();
        String[] pairs = defaultParameters.split(",");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                try {
                    map.put(kv[0].trim(), Double.parseDouble(kv[1].trim()));
                } catch (NumberFormatException ignored) {}
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return name;
    }
}
