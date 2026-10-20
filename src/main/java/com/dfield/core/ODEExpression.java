package com.dfield.core;

import com.dfield.numerical.FirstOrderODE;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.function.Function;

import java.util.*;

/**
 * Avaliador de expressões analíticas para Equações Diferenciais Ordinárias de 1ª ordem:
 * dx/dt = f(t, x).
 * <p>
 * Implementa {@link FirstOrderODE} utilizando a biblioteca {@code exp4j}.
 * Garante segurança em ambientes concorrentes (Thread-Safe) via {@link ThreadLocal}
 * e resiliência matemática contra singularidades e divisões por zero.
 */
public final class ODEExpression implements FirstOrderODE {

    private final String expressionString;
    private final String independentVar;
    private final String dependentVar;
    private final Map<String, Double> parameters;
    private final Set<String> declaredVariables;

    // Garante que cada thread (ex: renderização concorrente do campo ou integração em background)
    // possua sua própria instância isolada de Expression do exp4j.
    private final ThreadLocal<Expression> threadLocalExpression;

    // --- Funções Matemáticas Personalizadas ---
    private static final Function CBRT_FUNCTION = new Function("cbrt", 1) {
        @Override
        public double apply(double... args) {
            return Math.cbrt(args[0]);
        }
    };

    private static final Function LN_FUNCTION = new Function("ln", 1) {
        @Override
        public double apply(double... args) {
            return Math.log(args[0]);
        }
    };

    private static final Function SGN_FUNCTION = new Function("sgn", 1) {
        @Override
        public double apply(double... args) {
            return Math.signum(args[0]);
        }
    };

    private static final Function HEAVISIDE_FUNCTION = new Function("heaviside", 1) {
        @Override
        public double apply(double... args) {
            return args[0] >= 0.0 ? 1.0 : 0.0;
        }
    };

    private static final Function STEP_FUNCTION = new Function("step", 1) {
        @Override
        public double apply(double... args) {
            return args[0] >= 0.0 ? 1.0 : 0.0;
        }
    };

    private static final Function SEC_FUNCTION = new Function("sec", 1) {
        @Override
        public double apply(double... args) {
            return 1.0 / Math.cos(args[0]);
        }
    };

    private static final Function CSC_FUNCTION = new Function("csc", 1) {
        @Override
        public double apply(double... args) {
            return 1.0 / Math.sin(args[0]);
        }
    };

    private static final Function COT_FUNCTION = new Function("cot", 1) {
        @Override
        public double apply(double... args) {
            return 1.0 / Math.tan(args[0]);
        }
    };

    private static final List<Function> CUSTOM_FUNCTIONS = List.of(
            CBRT_FUNCTION,
            LN_FUNCTION,
            SGN_FUNCTION,
            HEAVISIDE_FUNCTION,
            STEP_FUNCTION,
            SEC_FUNCTION,
            CSC_FUNCTION,
            COT_FUNCTION
    );

    /**
     * Constrói e valida uma expressão de EDO.
     *
     * @param expressionString string com a expressão f(t, x) (ex: "a * x^2 - t")
     * @param independentVar   nome da variável independente (padrão "t")
     * @param dependentVar     nome da variável de estado (ex: "x", "P", "V_c")
     * @param parameters       mapa de parâmetros estáticos (ex: {a=1.0, K=10.0})
     * @throws IllegalArgumentException se a expressão for malformada, contiver variáveis não declaradas
     *                                  ou se houver colisão de nomes.
     */
    public ODEExpression(
            String expressionString,
            String independentVar,
            String dependentVar,
            Map<String, Double> parameters
    ) {
        if (expressionString == null || expressionString.isBlank()) {
            throw new IllegalArgumentException("A expressão da EDO não pode ser nula ou vazia.");
        }
        if (independentVar == null || independentVar.isBlank()) {
            throw new IllegalArgumentException("A variável independente deve ser informada (ex: 't').");
        }
        if (dependentVar == null || dependentVar.isBlank()) {
            throw new IllegalArgumentException("A variável dependente deve ser informada (ex: 'x').");
        }
        if (independentVar.equals(dependentVar)) {
            throw new IllegalArgumentException("As variáveis independente e dependente não podem ter o mesmo nome: '" + independentVar + "'");
        }

        this.expressionString = expressionString.trim();
        this.independentVar = independentVar.trim();
        this.dependentVar = dependentVar.trim();
        this.parameters = parameters != null ? Map.copyOf(parameters) : Collections.emptyMap();

        // Monta o catálogo de identificadores permitidos na fórmula
        this.declaredVariables = new HashSet<>();
        this.declaredVariables.add(this.independentVar);
        this.declaredVariables.add(this.dependentVar);

        for (String paramName : this.parameters.keySet()) {
            String trimmedParam = paramName.trim();
            if (trimmedParam.equals(this.independentVar) || trimmedParam.equals(this.dependentVar)) {
                throw new IllegalArgumentException("O parâmetro '" + trimmedParam + "' coincide com o nome de uma das variáveis do sistema.");
            }
            this.declaredVariables.add(trimmedParam);
        }

        // Valida sintaxe e semântica imediatamente (Fail-Fast)
        validateExpression();

        // Inicializa o fornecedor local de threads para avaliações
        this.threadLocalExpression = ThreadLocal.withInitial(this::buildNewExpressionInstance);
    }

    /**
     * Valida antecipadamente se a expressão é interpretável pelo exp4j,
     * se todas as variáveis estão mapeadas e se não há tokens inválidos.
     */
    private void validateExpression() {
        try {
            Expression testExpr = buildNewExpressionInstance();

            // Aplica valores de teste representativos
            testExpr.setVariable(independentVar, 1.0);
            testExpr.setVariable(dependentVar, 1.0);
            for (Map.Entry<String, Double> entry : parameters.entrySet()) {
                testExpr.setVariable(entry.getKey(), entry.getValue() != null ? entry.getValue() : 0.0);
            }

            ValidationResult validation = testExpr.validate(false);
            if (!validation.isValid()) {
                throw new IllegalArgumentException("Erro de validação na expressão: " + String.join(", ", validation.getErrors()));
            }

            // Executa uma avaliação de teste com ponto seguro
            testExpr.evaluate();

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Sintaxe matemática inválida em '" + expressionString + "': " + e.getMessage(), e);
        }
    }

    /**
     * Constrói e configura uma nova instância limpa de {@link Expression}.
     */
    private Expression buildNewExpressionInstance() {
        ExpressionBuilder builder = new ExpressionBuilder(expressionString)
                .variables(declaredVariables)
                .functions(CUSTOM_FUNCTIONS);

        return builder.build();
    }

    @Override
    public double eval(double t, double x) {
        if (!Double.isFinite(t) || !Double.isFinite(x)) {
            return Double.NaN;
        }

        try {
            Expression expr = threadLocalExpression.get();

            expr.setVariable(independentVar, t);
            expr.setVariable(dependentVar, x);

            for (Map.Entry<String, Double> entry : parameters.entrySet()) {
                Double val = entry.getValue();
                expr.setVariable(entry.getKey(), val != null ? val : 0.0);
            }

            double result = expr.evaluate();

            // Protege contra overflow e indefinições (ex: 1/0 ou log(-1))
            return Double.isFinite(result) ? result : Double.NaN;

        } catch (ArithmeticException | IllegalArgumentException e) {
            // Em caso de descontinuidade matemática (ex: divisão por zero ou log de número negativo)
            return Double.NaN;
        } catch (Exception e) {
            // Qualquer outra falha de runtime é tratada como ponto não computável
            return Double.NaN;
        }
    }

    public String getExpressionString() {
        return expressionString;
    }

    public String getIndependentVar() {
        return independentVar;
    }

    public String getDependentVar() {
        return dependentVar;
    }

    public Map<String, Double> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "d" + dependentVar + "/d" + independentVar + " = " + expressionString;
    }
}