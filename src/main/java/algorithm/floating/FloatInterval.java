package algorithm.floating;

import algorithm.abstracts.Interval;
import algorithm.abstracts.interfaces.Compartmental;
import algorithm.abstracts.interfaces.Computable;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import static algorithm.floating.FloatConstant.ROUND_CEIL_64X;
import static algorithm.floating.FloatConstant.ROUND_FLOOR_64X;
import static algorithm.floating.FloatConstant.TWO_64X;

class FloatInterval extends Interval<BigFloat> {

    FloatInterval(String point) {
        this(new BigFloat(point, ROUND_FLOOR_64X), new BigFloat(point, ROUND_CEIL_64X));
    }

    FloatInterval(BigFloat point) {
        this(point, point);
    }

    FloatInterval(String lower, String upper) {
        this(new BigFloat(lower, ROUND_FLOOR_64X), new BigFloat(upper, ROUND_CEIL_64X));
    }

    FloatInterval(BigFloat lower, BigFloat upper) {
        if (lower.greaterThan(upper))
            throw new RuntimeException("Lower bound cannot be upper than upper");
        this.delta = upper.subtract(lower, BinaryMathContext.BINARY64);
    }

    public BigFloat getUpper() {
        return upper;
    }

    public BigFloat getLower() {
        return lower;
    }

    public FloatInterval getCenterPoint() {
        final BigFloat sum = lower.add(upper, BinaryMathContext.BINARY64);
        final BigFloat lowerBound = sum.divide(TWO_64X, ROUND_FLOOR_64X);
        final BigFloat upperBound = sum.divide(TWO_64X, ROUND_CEIL_64X);

        return new FloatInterval(lowerBound, upperBound);
    }

    @Override
    protected void setDelta(BigFloat lower, BigFloat upper) {
        this.delta = upper.subtract(lower, BinaryMathContext.BINARY64);
    }

    private boolean isPoint() {
        return lower.equals(upper);
    }

    public Compartmental<BigFloat> findSubInterval(Computable<BigFloat> polynomial) {
        final FloatInterval centerPoint = getCenterPoint();
        final FloatInterval lowerInterval = new FloatInterval(lower, centerPoint.getUpper());
        if (polynomial.canBeComputedWith(lowerInterval)) {
            return lowerInterval;
        } else {
            return new FloatInterval(centerPoint.getLower(), upper);
        }
    }

    public boolean isLowerOrEqualValueWithAbs(BigFloat value) {
        final BigFloat lowerAbs = lower.abs();
        final BigFloat higherAbs = upper.abs();
        final BigFloat furtherFromZero = BigFloat.max(lowerAbs, higherAbs, BinaryMathContext.BINARY64);
        return furtherFromZero.lessThanOrEqualTo(value);
    }

    public boolean isNarrowerThan(BigFloat width) {
        return upper.subtract(lower, BinaryMathContext.BINARY64).lessThanOrEqualTo(width);
    }

    public Compartmental<BigFloat> add(final Compartmental<BigFloat> other) {
        final BigFloat lower = this.lower.add(other.getLower(), ROUND_FLOOR_64X);
        final BigFloat higher = this.upper.add(other.getUpper(), ROUND_CEIL_64X);
        return new FloatInterval(lower, higher);
    }

    public Compartmental<BigFloat> sum(Compartmental<BigFloat> other) {
        final BigFloat lower = this.lower.lessThan(other.getLower()) ? this.lower : other.getLower();
        final BigFloat higher = this.upper.greaterThan(other.getUpper()) ? this.upper : other.getUpper();
        return new FloatInterval(lower, higher);
    }

    public Compartmental<BigFloat> multiply(Compartmental<BigFloat> other) {
        Compartmental<BigFloat> lowerInterval = other.multiply(lower);
        Compartmental<BigFloat> higherInterval = other.multiply(upper);
        return lowerInterval.sum(higherInterval);
    }

    public Compartmental<BigFloat> multiply(BigFloat value) {
        final BigFloat lowerValue = lower.multiply(value, ROUND_FLOOR_64X);
        final BigFloat upperValue = upper.multiply(value, ROUND_CEIL_64X);

        return new FloatInterval(lowerValue, upperValue);
    }

    public static Compartmental<BigFloat> multiply(BigFloat multiplier, BigFloat multiplicand) {
        final BigFloat resultLower = multiplier.multiply(multiplicand,ROUND_FLOOR_64X);
        final BigFloat resultUpper = multiplier.multiply(multiplicand,ROUND_CEIL_64X);

        return new FloatInterval(resultLower, resultUpper);
    }

    public Compartmental<BigFloat> negate() {
        return new FloatInterval(upper.negate(), lower.negate());
    }

    public String getIntervalWithDelta() {
        return toSimpleString() + " \u0394 " + delta;
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    private String toSimpleString() {
        if (isPoint()) {
            return lower.toString();
        }
        return "[" + lower +", " + upper+ "]";
    }
}
