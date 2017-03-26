package algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Polynomial {
	private final List<Element> elements;

	Polynomial() {
		elements = new ArrayList<>();
	}

	Polynomial(Element... elements) {
		this(Arrays.asList(elements));
	}

	Polynomial(List<Element> elements) {
		this.elements = elements;
	}

	/**
	 * Counts value of the polynomial for given argument
	 *
	 * @param value     given argument
	 * @param precision precision of computations
	 * @return scope of values for given argument
	 */
	Interval countForValue(BigDecimal value, int precision) {
		Interval result = new Interval(BigDecimal.ZERO, precision);
		for (Element element : elements) {
			final Interval factor = element.getFactor();
			final BigDecimal pow = value.pow(element.getExponent());
			result = result.add(factor.multiplyByValue(pow)); //sum += aixi^i
		}
		return result;
	}

	/**
	 * Checks, if a product of computations for given scope's borders is negative - that means there is zero point
	 * between them.
	 *
	 * @param interval  Given to computations interval - scope
	 * @param precision decimal precision of computation
	 * @return if computations can be made for given interval
	 */
	boolean canBeComputedWith(Interval interval, int precision) {
		final Interval lowerValue = countForValue(interval.getLower(), precision);
		final Interval higherValue = countForValue(interval.getUpper(), precision);
		return lowerValue
				.getLower()
				.multiply(higherValue.getUpper())
				.compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * Counts result of polynomial for given as interval argument
	 *
	 * @param interval  given scope
	 * @param precision decimal precision of computations
	 * @return calculation results for values at the edges of compartment
	 */
	Interval countForInterval(Interval interval, int precision) {
		final Interval lowerResult = countForValue(interval.getLower(), precision);
		final Interval higherResult = countForValue(interval.getUpper(), precision);
		return lowerResult.sum(higherResult);
	}

	public boolean isAcceptableScope(Interval interval, BigDecimal epsilon, int precision) {

		final Interval lowerResult = countForValue(interval.getLower(), precision);
		final Interval higherResult = countForValue(interval.getUpper(), precision);

		return lowerResult.isLowerOrEqualValueWithAbs(epsilon) ||
				higherResult.isLowerOrEqualValueWithAbs(epsilon);
	}

	public boolean isAcceptableResult(Interval result, BigDecimal epsilon) {
		return result.isLowerOrEqualValueWithAbs(epsilon);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Element element : elements) {
			result.append(element).append(" ");
		}
		return result.toString();
	}
}
