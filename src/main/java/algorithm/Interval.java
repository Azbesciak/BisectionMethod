package algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class Interval {
	private final BigDecimal lower;
	private final BigDecimal upper;
	private final BigDecimal delta;
	private final int precision;
	private static NumberFormat formatter;
	private final static  BigDecimal TWO = new BigDecimal("2");
	static {
		formatter = new DecimalFormat("0.#E0");
//		formatter.setMinimumFractionDigits(Constants.BASE_PRECISION);
//		formatter.setRoundingMode(RoundingMode.UNNECESSARY);
//		formatter.setRoundingMode(RoundingMode.HALF_UP);
		formatter.setMaximumFractionDigits(Constants.BASE_PRECISION);
	}

	Interval(String point, int precision) {
		this(new BigDecimal(point), precision);
	}

	Interval(BigDecimal point, int precision) {
		this(point, point, precision);
	}

	Interval(String lower, String upper, String precision) {
		this(new BigDecimal(lower), new BigDecimal(upper), Integer.valueOf(precision));
	}

	Interval(String lower, String upper, int precision) {
		this(new BigDecimal(lower), new BigDecimal(upper), precision);
	}

	Interval(BigDecimal lower, BigDecimal upper, int precision) {
		if (lower.compareTo(upper) > 0)
			throw new RuntimeException("Lower bound cannot be upper than upper");
		this.lower = lower;
		this.upper = upper;
		this.precision = precision;
		this.delta = upper.subtract(lower);
	}

	BigDecimal getUpper() {
		return upper;
	}

	BigDecimal getLower() {
		return lower;
	}

	Interval getCenterPoint() {

		final BigDecimal sum = lower.add(upper);
		final BigDecimal lowerBound = sum.divide(TWO, precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal upperBound = sum.divide(TWO, precision, BigDecimal.ROUND_CEILING);

		return new Interval(lowerBound, upperBound, precision);
	}

	private boolean isPoint() {
		return lower.equals(upper);
	}

	Interval findSubInterval(Polynomial polynomial) {
		final Interval centerPoint = getCenterPoint();
		final Interval lowerInterval = new Interval(lower, centerPoint.getUpper(), precision);
		if (polynomial.canBeComputedWith(lowerInterval, precision)) {
			return lowerInterval;
		} else {
			return new Interval(centerPoint.getLower(), upper, precision);
		}
	}

	boolean isLowerOrEqualValueWithAbs(BigDecimal value) {
		final BigDecimal lowerAbs = lower.abs();
		final BigDecimal higherAbs = upper.abs();
		final BigDecimal furtherFromZero = lowerAbs.max(higherAbs);
		return furtherFromZero.compareTo(value) <= 0;
	}

	boolean isNarrowerThan(BigDecimal width) {
		return upper.subtract(lower).compareTo(width) <= 0;
	}

	Interval add(Interval other) {
		final BigDecimal lower = this.lower.add(other.lower).setScale(precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal higher = this.upper.add(other.upper).setScale(precision, BigDecimal.ROUND_CEILING);
		return new Interval(lower, higher, precision);
	}

	Interval sum(Interval other) {
		final BigDecimal lower = this.lower.compareTo(other.lower) < 0 ? this.lower : other.lower;
		final BigDecimal higher = this.upper.compareTo(other.upper) > 0 ? this.upper : other.upper;
		return new Interval(lower, higher, precision);
	}

	public Interval multiplyByInterval(Interval other) {
		Interval lowerInterval = other.multiplyByValue(lower);
		Interval higherInterval = other.multiplyByValue(upper);
		return lowerInterval.sum(higherInterval);
	}

	Interval multiplyByValue(BigDecimal value) {
		final BigDecimal lowerValue = value
				.setScale(precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal higherValue = value
				.setScale(precision, BigDecimal.ROUND_CEILING);

		final BigDecimal lowerLower = lower.multiply(lowerValue);
		final BigDecimal lowerHigher = lower.multiply(higherValue);
		final BigDecimal higherLower = upper.multiply(lowerValue);
		final BigDecimal higherHigher = upper.multiply(higherValue);

		final List<BigDecimal> values = Arrays.asList(lowerHigher, lowerLower, higherHigher, higherLower);
		final BigDecimal lowerResult = values.stream().min(BigDecimal::compareTo).orElse(lowerLower);
		final BigDecimal higherResult = values.stream().max(BigDecimal::compareTo).orElse(higherHigher);
		return new Interval(lowerResult, higherResult, precision);
	}

	public static Interval multiplyValues(BigDecimal multiplier, BigDecimal multiplicand, int precision) {
		final BigDecimal result = multiplier.multiply(multiplicand);

		final BigDecimal roundedDown = result
				.setScale(precision, BigDecimal.ROUND_DOWN);
		final BigDecimal roundedUp = result
				.setScale(precision, BigDecimal.ROUND_UP);

		if (roundedDown.compareTo(roundedUp) <= 0) {
			return new Interval(roundedDown, roundedUp, precision);
		} else {
			return new Interval(roundedUp, roundedDown, precision);
		}
	}

	Interval negate() {
		return new Interval(upper.negate(), lower.negate(), precision);
	}

	private String formatValue(BigDecimal value) {
		return formatter.format(value);
	}

	public String getIntervalWithDelta() {
		return toScientificString() + " \u0394 " + formatValue(delta);
	}

	@Override
	public String toString() {
		return toSimpleString();
	}

	private String toSimpleString() {
		if (isPoint()) {
			return lower.stripTrailingZeros().toEngineeringString();
		}
		return "[" + lower.stripTrailingZeros().toEngineeringString() +", " + upper.stripTrailingZeros().toEngineeringString() + "]";
	}

	private String toScientificString() {
		if (isPoint()) {
			return formatValue(lower);
		}
		return "[" + formatValue(lower) +", " + formatValue(upper) + "]";
	}
}
