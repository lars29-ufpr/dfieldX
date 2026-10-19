package com.dfield.core;

public record EquationExample(
        String name,
        String category,
        String description,
        String indepVar,
        String depVar,
        String equation,
        String parameters,
        double tMin,
        double tMax,
        double xMin,
        double xMax,
        boolean isCustom
) {
    public EquationExample(String name, String category, String description,
                           String indepVar, String depVar, String equation,
                           String parameters, double tMin, double tMax,
                           double xMin, double xMax) {
        this(name, category, description, indepVar, depVar, equation, parameters, tMin, tMax, xMin, xMax, false);
    }

    @Override
    public String toString() {
        return name;
    }
}
