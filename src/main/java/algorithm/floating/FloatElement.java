package algorithm.floating;

import algorithm.abstracts.Element;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import static algorithm.floating.FloatConstant.*;

class FloatElement extends Element<BigFloat, BigFloat> {
    FloatElement(String[] array) {
        switch (array.length) {

            case VALUE_WITH_POWER: {
                exponent = new BigFloat(array[1], BinaryMathContext.BINARY64);
            }
            case JUST_VALUE: {
                prepareSingleValueElement(array[0]);
                break;
            }
            case INTERVAL_WITH_EXPONENT: {
                exponent = new BigFloat(array[3], BinaryMathContext.BINARY64);
            }
            case INTERVAL_AS_VALUE: {
                prepareIntervalElement(array);
            }
            default:
                break;
        }
        if (factor == null) {
            factor = new FloatInterval(ONE_64X);
        }
        if (exponent == null) {
            exponent = ZERO_64X;
        }
    }

    protected void prepareSingleValueElement(String value) {
        String factor = value;
        if ("+".equals(factor) || "".equals(factor)) {
            factor = "1";
        } else if ("-".equals(factor)) {
            factor = "-1";
        }
        final BigFloat lower = new BigFloat(factor, ROUND_FLOOR_64X);
        final BigFloat upper = new BigFloat(factor, ROUND_CEIL_64X);

        this.factor = new FloatInterval(lower, upper);
    }

    protected void prepareIntervalElement(String[] array) {
        final FloatInterval interval = new FloatInterval(array[1], array[2]);
        factor = "-".equals(array[0]) ? interval.negate() : interval;
    }

    public void multiplyBySign(String sign) {
        if ("-".equals(sign)) {
            factor = factor.negate();
        }
    }

    @Override
    public String toString() {
        String result = factor.toString();
        if (exponent.greaterThan(ZERO_64X)) {
            if ("1".equals(result)) {
                result = "";
            } else if ("-1".equals(result)) {
                result = "-";
            }
            result += "x";
            if (exponent.greaterThan(ONE_64X)) {
                result += "^" + exponent;
            }
        }
        return result;
    }

}