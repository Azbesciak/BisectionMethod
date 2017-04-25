package algorithm.logic;

import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.Computable;

import java.util.Arrays;
import java.util.List;

public class Interval<V extends Number & Comparable<V>> {
    private NumberWrapper<V> lower;
    private NumberWrapper<V> upper;
    private NumberWrapper<V> delta;

    Interval(NumberWrapper<V> point) {
        this(point.floor(), point.ceil());
    }

    public Interval(NumberWrapper<V> lower, NumberWrapper<V> upper) {
        this.lower = lower;
        this.upper = upper;
        setDelta(lower, upper);
    }

    private void setDelta(NumberWrapper<V> lower, NumberWrapper<V> upper) {
        delta = upper.getCeilingPrintableValue().minus(lower.getFloorPrintableValue());
    }

    private boolean isPoint() {
        return lower.equals(upper);
    }

    private String toSimpleString() {
        if (isPoint()) {
            return lower.toString();
        }
        return "[" + lower.getFloorPrintableValue() +", " + upper.getCeilingStringValue()+ "]";
    }

    Interval<V> getCenterPoint() {
        final NumberWrapper<V> sumCeiling = lower.addCeiling(upper);
        final NumberWrapper<V> sumFloor = lower.addFloor(upper);
        final NumberWrapper<V> lowerBound = sumCeiling.divideCeiling("2");
        final NumberWrapper<V> upperBound = sumFloor.divideFloor("2");

        return new Interval<>(lowerBound, upperBound);
    }

    Interval<V>findSubInterval(Computable<V> polynomial) {
        final Interval<V> centerPoint = getCenterPoint();
        final Interval<V> lowerInterval = new Interval<>(lower, centerPoint.getUpper());
        if (polynomial.canBeComputedWith(lowerInterval)) {
            return lowerInterval;
        } else {
            return new Interval<>(centerPoint.getLower(), upper);
        }
    }

    boolean isLowerOrEqualValueWithAbs(NumberWrapper<V> value) {
        return isLowerOrEqualValueWithAbs(value.getValue());
    }

    private boolean isLowerOrEqualValueWithAbs(V value) {
        final NumberWrapper<V> lowerAbs = lower.abs();
        final NumberWrapper<V> higherAbs = upper.abs();
        final NumberWrapper<V> furtherFromZero = lowerAbs.max(higherAbs);
        return furtherFromZero.lessThanOrEqualTo(value);
    }
    boolean isNarrowerThan(NumberWrapper<V> width) {
        return upper.minusCeiling(lower).lessThanOrEqualTo(width);
    }

    boolean isNarrowerThan(V width) {
        return upper.minusCeiling(lower).lessThanOrEqualTo(width);
    }

    Interval<V> add(Interval<V> other) {
        final NumberWrapper<V> lower = this.lower.addFloor(other.lower);
        final NumberWrapper<V> upper = this.upper.addCeiling(other.upper);
        return new Interval<>(lower, upper);
    }

    Interval<V> sum(Interval<V> other) {
        final NumberWrapper<V> lower = this.lower.min(other.lower);
        final NumberWrapper<V> higher = this.upper.max(other.upper);
        return new Interval<>(lower, higher);
    }

    Interval<V> multiply(Interval<V> other) {
        Interval<V> lowerDecimalInterval = other.multiply(lower);
        Interval<V> higherDecimalInterval = other.multiply(upper);
        return lowerDecimalInterval.sum(higherDecimalInterval);    }


    Interval<V>multiply(NumberWrapper<V> other) {

        final NumberWrapper<V> lowerLower = lower.multiplyFloor(other);
        final NumberWrapper<V> lowerHigher = lower.multiplyCeiling(other);
        final NumberWrapper<V> higherLower = upper.multiplyFloor(other);
        final NumberWrapper<V> higherHigher = upper.multiplyCeiling(other);

        final List<NumberWrapper<V>> values = Arrays.asList(lowerHigher, lowerLower, higherHigher, higherLower);
        final NumberWrapper<V> lowerResult = values.stream().min(NumberWrapper::compareTo).orElse(lowerLower);
        final NumberWrapper<V> higherResult = values.stream().max(NumberWrapper::compareTo).orElse(higherHigher);
        return new Interval<>(lowerResult, higherResult);
    }

    Interval<V> negate() {
        return new Interval<>(upper.negate(), lower.negate());
    }

    V getLowerValue() {
        return lower.getValue();
    }

    NumberWrapper<V> getLower() {
        return lower;
    }

    V getUpperValue() {
        return upper.getValue();
    }

    NumberWrapper<V> getUpper() {
        return upper;
    }

    public String getIntervalWithDelta() {
        return toSimpleString() + " \u0394 " + delta.getFloorStringValue();
    }

    @Override
    public String toString() {
        return toSimpleString();
    }
}
