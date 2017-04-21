package algorithm.logic;


import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;

class Element<V extends Number & Comparable<V>> {
    private final static int SINGLE_VARIABLE = 0;
    private  final static int JUST_VALUE = 1;
    private final static int VALUE_WITH_POWER = 2;
    private final static int INTERVAL_AS_VALUE = 3;
    private final static int INTERVAL_WITH_EXPONENT = 4;

    private Interval<V> factor;
    private String exponent;
    private int expVal;

    Element(String[] array, WrapperBuilder builder) {
        switch (array.length) {
            case VALUE_WITH_POWER: {
                setExponent(array[1]);
            }
            case JUST_VALUE: {
                prepareSingleValueElement(array[0], builder);
                break;
            }
            case INTERVAL_WITH_EXPONENT: {
                setExponent(array[3]);
            }
            case INTERVAL_AS_VALUE: {
                prepareIntervalElement(array, builder);
            }
            default:
                break;
        }
        if (factor == null) {
            factor = new Interval<>(builder.getWrapper("1"));
        }
        if (exponent == null) {
            setExponent("0");
        }
    }

    private void prepareSingleValueElement(String value, WrapperBuilder builder) {
        String factor = value;
        if ("+".equals(factor) || "".equals(factor)) {
            factor = "1";
        } else if ("-".equals(factor)) {
            factor = "-1";
        }
        final NumberWrapper<V> temp = builder.getWrapper(factor);
        final NumberWrapper<V> lower = temp.floor();
        final NumberWrapper<V> upper = temp.ceil();
        this.factor = new Interval<>(lower, upper);
    }

    private void prepareIntervalElement(String[] array, WrapperBuilder builder) {
        final NumberWrapper<V> lower = (NumberWrapper<V>) builder.getWrapper(array[1]).floor();
        final NumberWrapper<V> upper = (NumberWrapper<V>) builder.getWrapper(array[2]).ceil();
        final Interval<V> interval = new Interval<>(lower, upper);
        factor = "-".equals(array[0]) ? interval.negate() : interval;
    }

    public void multiplyBySign(String sign) {
        if ("-".equals(sign)) {
            factor = factor.negate();
        }
    }

    private String makeString(int rootPosition) {
        String result = "";
        if (rootPosition >= 0) {
            result += "\u221A";
        }
        result += factor;
        if (expVal >= 0) {
            if ("1".equals(result)) {
                result = "";
            } else if ("-1".equals(result)) {
                result = "-";
            }
            result += "x";

            if (expVal > 1) {
                result += "^" + exponent;
            }
        }
        return result;
    }

    public Interval<V> getFactor() {
        return factor;
    }

    public String getExponent() {
        return exponent;
    }

    private void setExponent(String exponent) {
        this.exponent = exponent;
        expVal = Integer.valueOf(exponent);
    }

    @Override
    public String toString() {
        String result = factor.toString();
        if (expVal > 0) {
            if ("1".equals(result)) {
                result = "";
            } else if ("-1".equals(result)) {
                result = "-";
            }
            result += "x";
            if (expVal > 1) {
                result += "^" + exponent;
            }
        }
        return result;

    }
}
