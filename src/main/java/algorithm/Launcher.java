package algorithm;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Integration layer controller and logic - prepares data to computations
 */
public class Launcher {

	/**
	 * Main method - parses data from view and starts computations. All decimal points need to be as dot ('.').
	 *
	 * @param polynomialStr    polynomial string - required to using square brackets for interval and space after single
	 *                         expression. Also need to explicitly say that power of x is 1 if it is.
	 *                         {@link controller.MainController}
	 * @param resultEpsilonStr string version of result epsilon
	 * @param scopeEpsilonStr  string version of arguments' scope epsilon
	 * @param lowerBound       lower bound of the given range of arguments
	 * @param upperBound       upper bound of the given range of arguments
	 * @param iterationsStr    allowed iterations
	 * @param precisionStr     decimal precision of computations. Need to be lower than epsilon - else computations won't
	 *                         be optimal (will use all available iterations)
	 */
	public static Result launch(String polynomialStr, String resultEpsilonStr, String scopeEpsilonStr,
								String lowerBound, String upperBound, String iterationsStr, String precisionStr) {

		final Integer precision = Integer.valueOf(precisionStr);
		final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_SPLIT_REGEX);
		final List<Element> elements = numbersPattern.splitAsStream(polynomialStr)
				.map(t -> new Element(t.split(Constants.ELEMENTS_SPLIT_REGEX), precision))
				.collect(Collectors.toList());

		final BigDecimal resultEpsilon = new BigDecimal(resultEpsilonStr);
		final BigDecimal scopeEpsilon = new BigDecimal(scopeEpsilonStr);
		final Polynomial polynomial = new Polynomial(elements);
		final Integer allowedIterations = Integer.valueOf(iterationsStr);
		final Bisection bisection = new Bisection(polynomial, scopeEpsilon, resultEpsilon, precision);
		return bisection.findZero(new Interval(lowerBound, upperBound, precisionStr), allowedIterations);
	}
}
