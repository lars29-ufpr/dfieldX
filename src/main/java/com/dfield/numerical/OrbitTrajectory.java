package com.dfield.numerical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma órbita/solução integral bidirecional a partir de (t0, x0).
 * Contém o ramo progressivo (t >= t0), o ramo regressivo (t <= t0) e a curva concatenada ordenada.
 */
public record OrbitTrajectory(
        List<TrajectoryPoint> forwardBranch,
        List<TrajectoryPoint> backwardBranch,
        TerminationReason forwardReason,
        TerminationReason backwardReason
) {

    public OrbitTrajectory {
        forwardBranch = List.copyOf(Objects.requireNonNull(forwardBranch, "forwardBranch não pode ser nulo"));
        backwardBranch = List.copyOf(Objects.requireNonNull(backwardBranch, "backwardBranch não pode ser nulo"));
        Objects.requireNonNull(forwardReason, "forwardReason não pode ser nulo");
        Objects.requireNonNull(backwardReason, "backwardReason não pode ser nulo");
    }

    /**
     * Retorna a trajetória contínua completa, ordenada cronologicamente do menor t para o maior t.
     * Os pontos regressivos invertidos são encadeados com os progressivos sem duplicar o ponto inicial.
     */
    public List<TrajectoryPoint> getFullOrderedTrajectory() {
        if (forwardBranch.isEmpty() && backwardBranch.isEmpty()) {
            return Collections.emptyList();
        }

        List<TrajectoryPoint> full = new ArrayList<>(backwardBranch.size() + forwardBranch.size());

        // O backwardBranch parte de t0 e caminha para tmin. Revertemos para ficar em ordem crescente de t.
        for (int i = backwardBranch.size() - 1; i >= 0; i--) {
            full.add(backwardBranch.get(i));
        }

        // Se o ponto t0 já estiver no backwardBranch, pula o primeiro de forwardBranch para evitar duplicata
        int startIndex = 0;
        if (!full.isEmpty() && !forwardBranch.isEmpty() && full.getLast().equals(forwardBranch.getFirst())) {
            startIndex = 1;
        }

        for (int i = startIndex; i < forwardBranch.size(); i++) {
            full.add(forwardBranch.get(i));
        }

        return Collections.unmodifiableList(full);
    }
}