package com.dfield.numerical;

/**
 * Motivos de interrupção da integração numérica.
 */
public enum TerminationReason {
    /** A integração atingiu o tempo limite t_min ou t_max especificado normalmente. */
    NORMAL,
    /** A trajetória cruzou os limites visíveis da caixa de plotagem. */
    BOUNDS,
    /** O tempo real de cálculo (wall-clock time) excedeu o teto configurado. */
    TIMEOUT,
    /** O número máximo de iterações/passos foi atingido. */
    MAX_STEPS,
    /** A solução divergiu numericamente (NaN ou Infinito/Blow-up). */
    BLOW_UP
}