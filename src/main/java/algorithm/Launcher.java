package algorithm;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Launcher {
	public static Result launch(String polynomialStr, String resultEpsilonStr, String scopeEpsilonStr, String
			lowerBound, String upperBound, String iterations, String precisionStr) {

		final Integer precision = Integer.valueOf(precisionStr);
		final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_REGEX);
		final List<Element> elements = numbersPattern.splitAsStream(polynomialStr)
				.peek(System.out::println)
				.map(t -> new Element(t.split(Constants.TEST_REGEX), precision))
				.collect(Collectors.toList());

		final BigDecimal resultEpsilon = new BigDecimal(resultEpsilonStr);
		final BigDecimal scopeEpsilon = new BigDecimal(scopeEpsilonStr);
		final Polynomial polynomial = new Polynomial(elements);
		final Integer allowedIterations = Integer.valueOf(iterations);
		System.out.println(polynomial);
		final Bisection bisection = new Bisection(polynomial, scopeEpsilon, resultEpsilon, precision);
		final Result zero = bisection.findZero(new Interval(lowerBound, upperBound, precisionStr), allowedIterations);
		System.out.println(zero);
		return zero;
	}
}
