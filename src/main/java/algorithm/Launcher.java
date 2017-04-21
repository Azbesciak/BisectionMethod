package algorithm;

import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;
import algorithm.types.WrapperBuilderFactory;
import algorithm.logic.Bisection;
import algorithm.logic.Interval;
import algorithm.logic.Polynomial;

/**
 * Integration layer controller and logic - prepares data to computations
 */
public class Launcher {

	/**
	 * Main method - parses data from view and starts computations. All decimal points need to be as dot ('.').

	 */
	public static Result launch(Params params) {
        final WrapperBuilder builder = WrapperBuilderFactory.getWrapper(params);
        return prepareResult(params, builder);
	}

	private static <V extends Number & Comparable<V>> Result<V> prepareResult(Params params, WrapperBuilder builder) {
        final NumberWrapper<V> resultEpsilon = builder.getWrapper(params.getResultEpsilon());
        final NumberWrapper<V> scopeEpsilon = builder.getWrapper(params.getScopeEpsilon());
        final Polynomial<V> polynomial = new Polynomial<>(params.getPolynomial(), builder);
        final Bisection<V> bisection = new Bisection<>(polynomial, scopeEpsilon, resultEpsilon);
        final String lowerBound = params.getLowerBound();
        final String upperBound = params.getUpperBound();
        final String iterations = params.getIterations();
        return bisection.findZero(new Interval<>(builder.getWrapper(lowerBound), builder.getWrapper(upperBound)), Integer.valueOf(iterations));
    }
}
