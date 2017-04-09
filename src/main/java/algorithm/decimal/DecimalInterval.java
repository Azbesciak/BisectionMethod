package algorithm.decimal;

import algorithm.Constants;
import algorithm.abstracts.Interval;
import algorithm.abstracts.interfaces.Compartmental;
import algorithm.abstracts.interfaces.Computable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

class DecimalInterval extends Interval<BigDecimal> {
	private final int precision;
	private static NumberFormat formatter;
	private final static  BigDecimal TWO = new BigDecimal("2");
	static {
		formatter = new DecimalFormat("0.#E0");
		formatter.setMaximumFractionDigits(Constants.BASE_PRECISION);
	}


	DecimalInterval(BigDecimal point, int precision) {
		this(point, point, precision);
	}

	DecimalInterval(String lower, String upper, int precision) {
		this(new BigDecimal(lower), new BigDecimal(upper), precision);
	}

	DecimalInterval(BigDecimal lower, BigDecimal upper, int precision) {
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

	public DecimalInterval getCenterPoint() {

		final BigDecimal sum = lower.add(upper);
		final BigDecimal lowerBound = sum.divide(TWO, precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal upperBound = sum.divide(TWO, precision, BigDecimal.ROUND_CEILING);

		return new DecimalInterval(lowerBound, upperBound, precision);
	}

	@Override
	protected void setDelta(BigDecimal lower, BigDecimal upper) {
		this.delta = upper.subtract(lower).setScale(precision, BigDecimal.ROUND_CEILING);
	}

	private boolean isPoint() {
		return lower.equals(upper);
	}

	public Compartmental<BigDecimal> findSubInterval(Computable<BigDecimal> decimalPolynomial) {
		final DecimalInterval centerPoint = getCenterPoint();
		final DecimalInterval lowerDecimalInterval = new DecimalInterval(lower, centerPoint.getUpper(), precision);
		if (decimalPolynomial.canBeComputedWith(lowerDecimalInterval)) {
			return lowerDecimalInterval;
		} else {
			return new DecimalInterval(centerPoint.getLower(), upper, precision);
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

	public Compartmental<BigDecimal> add(Compartmental<BigDecimal> other) {
		DecimalInterval otherDecimal = (DecimalInterval) other;
		final BigDecimal lower = this.lower.add(otherDecimal.lower).setScale(precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal higher = this.upper.add(otherDecimal.upper).setScale(precision, BigDecimal.ROUND_CEILING);
		return new DecimalInterval(lower, higher, precision);
	}

	public Compartmental<BigDecimal> sum(Compartmental<BigDecimal> other) {
		DecimalInterval otherDecimal = (DecimalInterval) other;
		final BigDecimal lower = this.lower.compareTo(otherDecimal.lower) < 0 ? this.lower : otherDecimal.lower;
		final BigDecimal higher = this.upper.compareTo(otherDecimal.upper) > 0 ? this.upper : otherDecimal.upper;
		return new DecimalInterval(lower, higher, precision);
	}

	public Compartmental<BigDecimal> multiply(Compartmental<BigDecimal> other) {
		Compartmental<BigDecimal> lowerDecimalInterval = other.multiply(lower);
		Compartmental<BigDecimal> higherDecimalInterval = other.multiply(upper);
		return lowerDecimalInterval.sum(higherDecimalInterval);
	}

	public Compartmental<BigDecimal> multiply(BigDecimal value) {
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
		return new DecimalInterval(lowerResult, higherResult, precision);
	}

	public static Compartmental<BigDecimal> multiply(BigDecimal multiplier, BigDecimal multiplicand, int precision) {
		final BigDecimal result = multiplier.multiply(multiplicand);

		final BigDecimal roundedDown = result
				.setScale(precision, BigDecimal.ROUND_DOWN);
		final BigDecimal roundedUp = result
				.setScale(precision, BigDecimal.ROUND_UP);

		if (roundedDown.compareTo(roundedUp) <= 0) {
			return new DecimalInterval(roundedDown, roundedUp, precision);
		} else {
			return new DecimalInterval(roundedUp, roundedDown, precision);
		}
	}

	public Compartmental<BigDecimal> negate() {
		return new DecimalInterval(upper.negate(), lower.negate(), precision);
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
