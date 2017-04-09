package algorithm;

import controller.MainController;


public class Params {
//    /**
//     * @param polynomial    polynomial string - required to using square brackets for interval and space after single
//     * expression. Also need to explicitly say that power of x is 1 if it is.
//     * {@link controller.MainController}
//     * @param resultEpsilonStr string version of result epsilon
//     * @param scopeEpsilonStr  string version of arguments' scope epsilon
//     * @param lowerBound       lower bound of the given range of arguments
//     * @param upperBound       upper bound of the given range of arguments
//     * @param iterationsStr    allowed iterations
//     * @param precisionStr     decimal precision of computations. Need to be lower than epsilon - else computations won't
//     * be optimal (will use all available iterations)
//     */
    private String polynomial;
    private String lowerBound;
    private String upperBound;
    private String scopeEpsilon;
    private String resultEpsilon;
    private String iterations;
    private String precision;
    private Arithmetic arithmetic;

    public static Params parse(MainController controller) {
        Params params = new Params();
        params.polynomial = preparePolynomialForAlgorithm(controller.getPolynomialInput().getText());
        params.lowerBound = controller.getLowerBoundInput().getText().replace(",", ".");
        params.upperBound = controller.getUpperBoundInput().getText().replace(",", ".");
        params.scopeEpsilon = controller.getScopeEpsilonInput().getText().replace(",", ".");
        params.resultEpsilon = controller.getResultEpsilonInput().getText().replace(",", ".");
        params.iterations = controller.getIterationsInput().getText();
        params.precision = controller.getPrecisionInput().getText();
        params.arithmetic = controller.getArithmeticChoice().getValue();
        return params;
    }

    /**
     * Prepares polynomial to parsable by launcher form
     *
     * @param polynomial given from input polynomial
     * @return computable polynomial
     */
    private static String preparePolynomialForAlgorithm(String polynomial) {
        return polynomial.replace(",", ".").replaceAll("\\s+", "")
                //if variable hasn't exponent, it is added with 0 power.
                .replaceAll("((?![eE])[a-zA-Z])(\\s*([^\\^\\s]|$))", "$1^1$3")
                .replaceAll("([^eE])([+-])", "$1 $2"); //adding spaces before +/- signs except those after exponent
    }

    public String getPolynomial() {
        return polynomial;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public String getScopeEpsilon() {
        return scopeEpsilon;
    }

    public String getResultEpsilon() {
        return resultEpsilon;
    }

    public String getIterations() {
        return iterations;
    }

    public String getPrecision() {
        return precision;
    }

    public Arithmetic getArithmetic() {
        return arithmetic;
    }
}
