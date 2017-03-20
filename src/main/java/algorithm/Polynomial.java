package algorithm;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		BigDecimal resultLower = BigDecimal.ZERO;
		BigDecimal resultHigher = BigDecimal.ZERO;
		for (Element element : elements) {
			final BigDecimal factor = element.getFactor();
			final BigDecimal fragment = factor
					.multiply(value.pow(element.getExponent()));
			final BigDecimal lowerValueFragment = fragment
					.setScale(precision, BigDecimal.ROUND_DOWN);
			final BigDecimal higherValueFragment = fragment
					.setScale(precision, BigDecimal.ROUND_UP);

			final BigDecimal lowerLower = resultLower.add(lowerValueFragment);
			final BigDecimal lowerHigher = resultLower.add(higherValueFragment);
			final BigDecimal higherLower = resultHigher.add(lowerValueFragment);
			final BigDecimal higherHigher = resultHigher.add(higherValueFragment);

			final List<BigDecimal> values = Arrays.asList(lowerHigher, lowerLower, higherHigher, higherLower);
			resultLower = values.stream().min(BigDecimal::compareTo).orElse(lowerLower);
			resultHigher = values.stream().max(BigDecimal::compareTo).orElse(higherHigher);
		}
		if (resultLower.compareTo(resultHigher) <= 0) {
			return new Interval(resultLower, resultHigher, precision);
		} else {
			return new Interval(resultHigher, resultLower, precision);
		}
	}

//	public Interval countForInterval(Interval interval, int precision) {
//		BigDecimal resultLower = BigDecimal.ZERO;
//		BigDecimal resultHigher = BigDecimal.ZERO;
//		for (Element element : elements) {
//			final BigDecimal multiply = element.getFactor()
//					.multiply(value.pow(element.getExponent()));
//			resultLower = resultLower.add(multiply
//												  .setScale(precision, BigDecimal.ROUND_DOWN));
//			resultHigher = resultHigher.add(multiply
//													.setScale(precision, BigDecimal.ROUND_UP));
//		}
//		if (resultLower.compareTo(resultHigher) <= 0) {
//			return new Interval(resultLower, resultHigher, precision);
//		} else {
//			return new Interval(resultHigher, resultLower, precision);
//		}
//	}

	public boolean canBeComputedWith(Interval interval, int precision) {
		final Interval lowerValue = countForValue(interval.getLower(), precision);
		final Interval higherValue = countForValue(interval.getHigher(), precision);
		return lowerValue
				.getLower()
				.multiply(higherValue.getHigher())
				.compareTo(BigDecimal.ZERO) < 0;
	}

	public Interval countForInterval(Interval interval, int precision) {
		final Interval lowerResult = countForValue(interval.getLower(), precision);
		final Interval higherResult = countForValue(interval.getHigher(), precision);
		return lowerResult.sum(higherResult);
	}

	public boolean isAcceptableScope(Interval interval, BigDecimal epsilon, int precision) {

		final Interval lowerResult = countForValue(interval.getLower(), precision);
		final Interval higherResult = countForValue(interval.getHigher(), precision);

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
