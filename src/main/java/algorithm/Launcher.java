package algorithm;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Launcher {
	public static Result launch(String polynomialStr, String resultEpsilonStr, String scopeEpsilonStr, String
			lowerBound, String upperBound, String iterations, String precisionStr) {

		final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_REGEX);
		final Pattern signsPattern = Pattern.compile(Constants.SIGNS_STREAM_REGEX);
		final List<Element> elements = numbersPattern.splitAsStream(polynomialStr)
//				.peek(System.out::println)
				.map(t -> new Element(t.split(Constants.NUMBER_VARIABLE_SPLIT_REGEX)))
				.collect(Collectors.toList());
		final List<String> signs = signsPattern.splitAsStream(polynomialStr)
				.collect(Collectors.toList());
		System.out.println(signs);
		System.out.println(elements);
		int elementIndex = signs.size() < elements.size() ? 1 : 0;
		final Iterator<String> signsIterator = signs.iterator();
		while (elementIndex < elements.size() && signsIterator.hasNext()) {
			elements.get(elementIndex).multiplyBySign(signsIterator.next());
			elementIndex++;
		}

		final BigDecimal resultEpsilon = new BigDecimal(resultEpsilonStr);
		final BigDecimal scopeEpsilon = new BigDecimal(scopeEpsilonStr);
		final Polynomial polynomial = new Polynomial(elements);
		final Integer precision = Integer.valueOf(precisionStr);
		final Integer allowedIterations = Integer.valueOf(iterations);

		final Bisection bisection = new Bisection(polynomial, scopeEpsilon, resultEpsilon, precision);
		final Result zero = bisection.findZero(new Interval(lowerBound, upperBound, precisionStr), allowedIterations);
		System.out.println(zero);
		return zero;
	}
}
