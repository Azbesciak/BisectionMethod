package algorithm.types.floating;

import algorithm.abstracts.NumberWrapper;
import org.kframework.mpfr.BigFloat;

class FloatWrapper extends NumberWrapper<BigFloat> {

    private FloatParam param;

    FloatWrapper(String value, FloatParam param) {
        super(new BigFloat(value, param.context));
        this.param = param;
    }

    private FloatWrapper(BigFloat value, FloatParam param) {
        super(value);
        this.param = param;
    }

    @Override
    public NumberWrapper<BigFloat> addCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.add(wrapper.getValue(), param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> addFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.add(wrapper.getValue(), param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> minusFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> minusCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> minus(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.subtract(wrapper.getValue(), param.direct()), param);
    }

    @Override
    public NumberWrapper<BigFloat> divideCeiling(String value) {
        return divideCeiling(new FloatWrapper(value, param));
    }

    @Override
    public NumberWrapper<BigFloat> divideCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.divide(wrapper.getValue(), param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> divideFloor(String value) {
        return divideFloor(new FloatWrapper(value, param));
    }

    @Override
    public NumberWrapper<BigFloat> divideFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.divide(wrapper.getValue(), param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> multiplyFloor(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.multiply(wrapper.getValue(), param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> multiplyCeiling(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(value.multiply(wrapper.getValue(), param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> abs() {
        return new FloatWrapper(value.abs(), param);
    }

    @Override
    public NumberWrapper<BigFloat> ceil() {
        return new FloatWrapper(value.round(param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> floor() {
        return new FloatWrapper(value.round(param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> powCeiling(String exponent) {
        return powCeiling(new BigFloat(exponent, param.ceiling()));
    }

    public NumberWrapper<BigFloat> powCeiling(BigFloat exponent) {
        return new FloatWrapper(value.pow(exponent, param.ceiling()), param);
    }

    @Override
    public NumberWrapper<BigFloat> powFloor(String exponent) {
        return powFloor(new BigFloat(exponent, param.floor()));
    }

    public NumberWrapper<BigFloat> powFloor(BigFloat exponent) {
        return new FloatWrapper(value.pow(exponent, param.floor()), param);
    }

    @Override
    public NumberWrapper<BigFloat> max(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(BigFloat.max(value, wrapper.getValue(), param.direct()), param);
    }

    @Override
    public NumberWrapper<BigFloat> min(NumberWrapper<BigFloat> wrapper) {
        return new FloatWrapper(BigFloat.min(value, wrapper.getValue(), param.direct()), param);
    }

    @Override
    public NumberWrapper<BigFloat> negate() {
        return new FloatWrapper(value.negate(), param);
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
    public String getFloorStringValue() {
        return param.getFloorString(value);
    }

    @Override
    public NumberWrapper<BigFloat> getFloorPrintableValue() {
        return new FloatWrapper(param.getFloorPrintable(value), param);
    }

    @Override
    public String getCeilingStringValue() {
        return param.getCeilString(value);
    }

    @Override
    public NumberWrapper<BigFloat> getCeilingPrintableValue() {
        return new FloatWrapper(param.getCeilPrintable(value), param);
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

    @Override
    public String toString() {
        return param.getDirectString(value);
    }
}
