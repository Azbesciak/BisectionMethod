package algorithm.floating;

import algorithm.Bisection;
import algorithm.Params;
import algorithm.Result;
import algorithm.abstracts.interfaces.Factory;
import org.kframework.mpfr.BigFloat;

import static org.kframework.mpfr.BinaryMathContext.BINARY64;


public class FloatFactory implements Factory<BigFloat,BigFloat> {
    @Override
    public Result<BigFloat, BigFloat> prepareResult(Params params) {
        final BigFloat resultEpsilon = new BigFloat(params.getResultEpsilon(), BINARY64);
        final BigFloat scopeEpsilon = new BigFloat(params.getScopeEpsilon(), BINARY64);
        final FloatPolynomial polynomial = new FloatPolynomial(params.getPolynomial());
        final Bisection<BigFloat, BigFloat> bisection = new Bisection<>(polynomial, scopeEpsilon, resultEpsilon);
        final String lowerBound = params.getLowerBound();
        final String upperBound = params.getUpperBound();
        final String iterations = params.getIterations();
        return bisection.findZero(new FloatInterval(lowerBound, upperBound), Integer.valueOf(iterations));
    }
}
