package algorithm;

import java.math.BigDecimal;

class Interval {
	private final BigDecimal lower;
	private final BigDecimal higher;
	private final BigDecimal delta;
	private final int precision;

	Interval(BigDecimal point, int precision) {
		this(point, point, precision);
	}

	Interval(String lower, String higher, String precision) {
		this(new BigDecimal(lower), new BigDecimal(higher), Integer.valueOf(precision));
	}

	Interval(BigDecimal lower, BigDecimal higher, int precision) {
		if (lower.compareTo(higher) > 0)
			throw new RuntimeException("Lower bound cannot be higher than upper");
		this.lower = lower;
		this.higher = higher;
		this.precision = precision;
		this.delta = higher.subtract(lower);
	}

	public BigDecimal getHigher() {
		return higher;
	}

	public BigDecimal getLower() {
		return lower;
	}

	public BigDecimal getCenterPoint() {

		return lower.add(higher).divide(new BigDecimal(2.0), precision, BigDecimal.ROUND_HALF_UP);
	}

	public boolean isPoint() {
		return lower.equals(higher);
	}

	public Interval findSubInterval(Polynomial polynomial) {
		final BigDecimal centerPoint = getCenterPoint();
		final Interval lowerInterval = new Interval(lower, centerPoint, precision);
		if (polynomial.canBeComputedWith(lowerInterval, precision)) {
			return lowerInterval;
		} else {
			return new Interval(centerPoint, higher, precision);
		}
	}

	public boolean isLowerOrEqualValueWithAbs(BigDecimal value) {
		final BigDecimal lowerAbs = lower.abs();
		final BigDecimal higherAbs = higher.abs();
		final BigDecimal furtherFromZero = lowerAbs.max(higherAbs);
		return furtherFromZero.compareTo(value) <= 0;
	}

	public boolean isNarrowerThan(BigDecimal width) {
		return higher.subtract(lower).compareTo(width) <= 0;
	}

	public Interval add(Interval other) {
		final BigDecimal lower = this.lower.add(other.lower);
		final BigDecimal higher = this.higher.add(other.higher);
		return new Interval(lower, higher, precision);
	}

	public Interval sum(Interval other) {
		final BigDecimal lower = this.lower.compareTo(other.lower) < 0 ? this.lower : other.lower;
		final BigDecimal higher = this.higher.compareTo(other.higher) > 0 ? this.higher : other.higher;
		return new Interval(lower, higher, precision);
	}

	@Override
	public String toString() {
		return "[" + lower.stripTrailingZeros().toEngineeringString() +", " +
				higher.stripTrailingZeros().toEngineeringString() + "] \u0394 " +
				delta.stripTrailingZeros().toEngineeringString();
	}
}
