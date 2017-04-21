package algorithm.decimal;

import algorithm.abstracts.NumberWrapper;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_FLOOR;
import static java.math.RoundingMode.*;

class DecimalWrapper extends NumberWrapper<BigDecimal> {

    private int precision;

    DecimalWrapper(String value, int precision) {
        super(new BigDecimal(value));
        this.precision = precision;
    }

    private DecimalWrapper(BigDecimal value, int precision) {
        super(value);
        this.precision = precision;
    }

    @Override
    public int compareTo(NumberWrapper<BigDecimal> o) {
        return value.compareTo(o.getValue());
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public NumberWrapper<BigDecimal> addCeiling(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                add(wrapper).setScale(precision, CEILING), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> addFloor(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                add(wrapper).setScale(precision, ROUND_FLOOR),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> minusFloor(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(value.subtract(wrapper.getValue()).setScale(precision, FLOOR), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> minusCeiling(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(value.subtract(wrapper.getValue()).setScale(precision, CEILING), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> minus(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(value.subtract(wrapper.getValue()).setScale(precision, HALF_UP), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> divideCeiling(String value) {
        return divideCeiling(new DecimalWrapper(value, precision));
    }

    private BigDecimal add(NumberWrapper<BigDecimal> wrapper) {
        return value.add(wrapper.getValue());
    }

    @Override
    public NumberWrapper<BigDecimal> divideCeiling(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                value.divide(wrapper.getValue(), precision, CEILING),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> divideFloor(String value) {
        return divideFloor(new DecimalWrapper(value, precision));
    }

    @Override
    public NumberWrapper<BigDecimal> divideFloor(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                value.divide(wrapper.getValue(), precision, FLOOR),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> multiplyFloor(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                value.multiply(wrapper.getValue()).setScale(precision, FLOOR),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> multiplyCeiling(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(
                value.multiply(wrapper.getValue()).setScale(precision, CEILING),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> abs() {
        return new DecimalWrapper(value.abs(), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> ceil() {
        return new DecimalWrapper(value.setScale(precision, CEILING), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> floor() {
        return new DecimalWrapper(value.setScale(precision, FLOOR), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> powCeiling(String exponent) {
        return powCeiling(Integer.valueOf(exponent));
    }

    public NumberWrapper<BigDecimal> powCeiling(Integer exponent) {
        return new DecimalWrapper(value.pow(exponent)
                .setScale(precision, BigDecimal.ROUND_CEILING),
                precision);
    }

    @Override
    public NumberWrapper<BigDecimal> powFloor(String exponent) {
        return powFloor(Integer.valueOf(exponent));
    }

    public NumberWrapper<BigDecimal> powFloor(Integer exponent) {
        return new DecimalWrapper(value.pow(exponent)
                .setScale(precision, ROUND_FLOOR),
                precision);
    }


    @Override
    public NumberWrapper<BigDecimal> max(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(value.max(wrapper.getValue()), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> min(NumberWrapper<BigDecimal> wrapper) {
        return new DecimalWrapper(value.min(wrapper.getValue()), precision);
    }

    @Override
    public NumberWrapper<BigDecimal> negate() {
        return new DecimalWrapper(value.negate(), precision);
    }

    @Override
    public boolean lessThan(NumberWrapper<BigDecimal> wrapper) {
        return this.lessThan(wrapper.getValue());
    }

    @Override
    public boolean lessThan(BigDecimal value) {
        return this.value.compareTo(value) < 0;
    }

    @Override
    public boolean lessThanOrEqualTo(NumberWrapper<BigDecimal> wrapper) {
        return lessThanOrEqualTo(wrapper.getValue());
    }

    @Override
    public boolean lessThanOrEqualTo(BigDecimal value) {
        return this.value.compareTo(value) <= 0;
    }

    @Override
    public boolean equalTo(NumberWrapper<BigDecimal> value) {
        return equalTo(value.getValue());
    }

    @Override
    public boolean equalTo(BigDecimal value) {
        return this.value.compareTo(value) == 0;
    }

    @Override
    public boolean greaterThan(NumberWrapper<BigDecimal> wrapper) {
        return greaterThan(wrapper.getValue());
    }

    @Override
    public boolean greaterThan(BigDecimal value) {
        return this.value.compareTo(value) > 0;
    }
}
