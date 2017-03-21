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

	public Interval countForValue(BigDecimal value, int precision) {
		Interval result = new Interval(BigDecimal.ZERO, precision);
		for (Element element : elements) {
			final Interval factor = element.getFactor();
			final BigDecimal pow = value.pow(element.getExponent());
			result = result.add(factor.multiplyByValue(pow)); //sum += aixi^i
		}
		return result;
	}

	public boolean canBeComputedWith(Interval interval, int precision) {
		final Interval lowerValue = countForValue(interval.getLower(), precision);
		final Interval higherValue = countForValue(interval.getUpper(), precision);
		return lowerValue
				.getLower()
				.multiply(higherValue.getUpper())
				.compareTo(BigDecimal.ZERO) < 0;
	}

	public Interval countForInterval(Interval interval, int precision) {
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
		String result = "";
		for (Element element : elements) {
			result += element + " ";
		}
		return result;
	}
}
