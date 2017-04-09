package algorithm.decimal;

import algorithm.Bisection;
import algorithm.Params;
import algorithm.Result;
import algorithm.abstracts.interfaces.Factory;

import java.math.BigDecimal;

public class DecimalFactory implements Factory<BigDecimal, Integer>{
    public DecimalFactory(){}

    @Override
    public Result<BigDecimal, Integer> prepareResult(Params params) {
        final Integer precision = Integer.valueOf(params.getPrecision());
        final BigDecimal resultEpsilon = new BigDecimal(params.getResultEpsilon());
        final BigDecimal scopeEpsilon = new BigDecimal(params.getScopeEpsilon());
        final DecimalPolynomial polynomial = new DecimalPolynomial(params.getPolynomial(), precision);
        final Bisection<BigDecimal, Integer> bisection = new Bisection<>(polynomial, scopeEpsilon, resultEpsilon);
        final String lowerBound = params.getLowerBound();
        final String upperBound = params.getUpperBound();
        final String iterations = params.getIterations();
        return bisection.findZero(new DecimalInterval(lowerBound, upperBound, precision), Integer.valueOf(iterations));
    }
}
