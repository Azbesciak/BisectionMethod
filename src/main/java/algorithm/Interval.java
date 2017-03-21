package algorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class Interval {
	private final BigDecimal lower;
	private final BigDecimal upper;
	private final BigDecimal delta;
	private final int precision;

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

	public BigDecimal getUpper() {
		return upper;
	}

	public BigDecimal getLower() {
		return lower;
	}

	public BigDecimal getCenterPoint() {

		return lower.add(upper).divide(new BigDecimal(2.0), precision, BigDecimal.ROUND_HALF_UP);
	}

	public boolean isPoint() {
		return lower.equals(upper);
	}

	public Interval findSubInterval(Polynomial polynomial) {
		final BigDecimal centerPoint = getCenterPoint();
		final Interval lowerInterval = new Interval(lower, centerPoint, precision);
		if (polynomial.canBeComputedWith(lowerInterval, precision)) {
			return lowerInterval;
		} else {
			return new Interval(centerPoint, upper, precision);
		}
	}

	public boolean isLowerOrEqualValueWithAbs(BigDecimal value) {
		final BigDecimal lowerAbs = lower.abs();
		final BigDecimal higherAbs = upper.abs();
		final BigDecimal furtherFromZero = lowerAbs.max(higherAbs);
		return furtherFromZero.compareTo(value) <= 0;
	}

	public boolean isNarrowerThan(BigDecimal width) {
		return upper.subtract(lower).compareTo(width) <= 0;
	}

	public Interval add(Interval other) {
		final BigDecimal lower = this.lower.add(other.lower);
		final BigDecimal higher = this.upper.add(other.upper);
		return new Interval(lower, higher, precision);
	}

	public Interval sum(Interval other) {
		final BigDecimal lower = this.lower.compareTo(other.lower) < 0 ? this.lower : other.lower;
		final BigDecimal higher = this.upper.compareTo(other.upper) > 0 ? this.upper : other.upper;
		return new Interval(lower, higher, precision);
	}

	public Interval multiplyByInterval(Interval other) {
		Interval lowerInterval = other.multiplyByValue(lower);
		Interval higherInterval = other.multiplyByValue(upper);
		return lowerInterval.sum(higherInterval);
	}

	public Interval multiplyByValue(BigDecimal value) {
		final BigDecimal lowerValue = value
				.setScale(precision, BigDecimal.ROUND_DOWN);
		final BigDecimal higherValue = value
				.setScale(precision, BigDecimal.ROUND_UP);

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

	public String getIntervalWithDelta() {
		return this + " \u0394 " + delta.stripTrailingZeros().toEngineeringString();
	}

	public Interval negate() {
		return new Interval(upper.negate(), lower.negate(), precision);
	}
	@Override
	public String toString() {
		if (isPoint()) {
			return lower.stripTrailingZeros().toEngineeringString();
		}
		return "[" + lower.stripTrailingZeros().toEngineeringString() +", " +
				upper.stripTrailingZeros().toEngineeringString() + "]";
	}
}
