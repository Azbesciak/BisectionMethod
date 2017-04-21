package algorithm.floating;

import algorithm.abstracts.NumberWrapper;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;

class FloatWrapper extends NumberWrapper<BigFloat> {

    private BinaryMathContext context;

    FloatWrapper(String value, BinaryMathContext context) {
        super(new BigFloat(value, context));
        this.context = context;
    }

    private FloatWrapper(BigFloat value, BinaryMathContext context) {
        super(value);
        this.context = context;
    }

    @Override
    public NumberWrapper<BigFloat> addCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.add(wrapper.getValue(), getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> addFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.add(wrapper.getValue(), getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> minusFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> minusCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> minus(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), context), context);
    }

    @Override
    public NumberWrapper<BigFloat> divideCeiling(String value) {
        return divideCeiling(new FloatWrapper(value, context));
    }

    @Override
    public NumberWrapper<BigFloat> divideCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.divide(wrapper.getValue(), getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> divideFloor(String value) {
        return divideFloor(new FloatWrapper(value, context));
    }

    @Override
    public NumberWrapper<BigFloat> divideFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.divide(wrapper.getValue(), getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> multiplyFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.multiply(wrapper.getValue(), getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> multiplyCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.multiply(wrapper.getValue(), getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> abs() {
        return new FloatWrapper(value.abs(), context);
    }

    @Override
    public NumberWrapper<BigFloat> ceil() {
        return new FloatWrapper(value.round(getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> floor() {
        return new FloatWrapper(value.round(getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> powCeiling(String exponent) {
        return powCeiling(new BigFloat(exponent, context));
    }

    public NumberWrapper<BigFloat> powCeiling(BigFloat exponent) {
        return new FloatWrapper(value.pow(exponent, getCeilingContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> powFloor(String exponent) {
        return powFloor(new BigFloat(exponent, context));
    }

    public NumberWrapper<BigFloat> powFloor(BigFloat exponent) {
        return new FloatWrapper(value.pow(exponent, getFloorContext()), context);
    }

    @Override
    public NumberWrapper<BigFloat> max(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(BigFloat.max(value, wrapper.getValue(), context), context);
    }

    @Override
    public NumberWrapper<BigFloat> min(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(BigFloat.min(value, wrapper.getValue(), context), context);
    }

    @Override
    public NumberWrapper<BigFloat> negate() {
        return new FloatWrapper(value.negate(), context);
    }

    @Override
    public boolean lessThan(NumberWrapper<BigFloat> wrapper) {
        return lessThan(wrapper.getValue());
    }

    @Override
    public boolean lessThan(BigFloat value) {
        return this.value.lessThan(value);
    }

    @Override
    public boolean lessThanOrEqualTo(NumberWrapper<BigFloat> wrapper) {
        return lessThanOrEqualTo(wrapper.getValue());
    }

    @Override
    public boolean lessThanOrEqualTo(BigFloat value) {
        return this.value.lessThanOrEqualTo(value);
    }

    @Override
    public boolean equalTo(NumberWrapper<BigFloat> wrapper) {
        return equalTo(wrapper.getValue());
    }

    @Override
    public boolean equalTo(BigFloat value) {
        return this.value.equalTo(value);
    }

    @Override
    public boolean greaterThan(NumberWrapper<BigFloat> wrapper) {
        return this.greaterThan(wrapper.getValue());
    }

    @Override
    public boolean greaterThan(BigFloat value) {
        return this.value.greaterThan(value);
    }

    @Override
    public int compareTo(NumberWrapper<BigFloat> o) {
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

    private BinaryMathContext getCeilingContext() {
        return context.withRoundingMode(RoundingMode.CEILING);
    }

    private BinaryMathContext getFloorContext() {
        return context.withRoundingMode(RoundingMode.FLOOR);
    }
}
