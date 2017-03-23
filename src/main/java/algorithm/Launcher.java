package algorithm;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Launcher {
	public static Result launch(String polynomialStr, String resultEpsilonStr, String scopeEpsilonStr, String
			lowerBound, String upperBound, String iterations, String precisionStr) {

		final Integer precision = Integer.valueOf(precisionStr);
		final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_SPLIT_REGEX);
		final List<Element> elements = numbersPattern.splitAsStream(polynomialStr)
				.map(t -> new Element(t.split(Constants.ELEMENTS_SPLIT_REGEX), precision))
				.collect(Collectors.toList());

		final BigDecimal resultEpsilon = new BigDecimal(resultEpsilonStr);
		final BigDecimal scopeEpsilon = new BigDecimal(scopeEpsilonStr);
		final Polynomial polynomial = new Polynomial(elements);
		final Integer allowedIterations = Integer.valueOf(iterations);
		final Bisection bisection = new Bisection(polynomial, scopeEpsilon, resultEpsilon, precision);
		return bisection.findZero(new Interval(lowerBound, upperBound, precisionStr), allowedIterations);
	}
}
