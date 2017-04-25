package algorithm.types.floating;

import algorithm.utils.Params;
import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;

public class FloatBuilder implements WrapperBuilder<BigFloat> {

    private final FloatParam param;

    public FloatBuilder(Params params) {
        BinaryMathContext context = BinaryMathContext.BINARY128;
        switch (params.getArithmetic()) {
            case INTERVAL:
                param = new IntervalFloatParam(context);
                break;
            case SIMPLE:
            default:
                param = new SimpleFloatParam(context);
                break;
        }
    }

    @Override
    public NumberWrapper<BigFloat> getWrapper(String value) {
        return new FloatWrapper(value, param);
    }

    private class IntervalFloatParam extends FloatParam {
        private final BinaryMathContext FLOOR_CONTEXT;
        private final BinaryMathContext CEIL_CONTEXT;

        IntervalFloatParam(BinaryMathContext context) {
            super(context);
            FLOOR_CONTEXT = context.withRoundingMode(RoundingMode.FLOOR);
            CEIL_CONTEXT = context.withRoundingMode(RoundingMode.CEILING);
        }

        @Override
        BinaryMathContext ceiling() {
            return CEIL_CONTEXT;
        }

        @Override
        BinaryMathContext floor() {
            return FLOOR_CONTEXT;
        }

        @Override
        BinaryMathContext direct() {
            return context;
        }

        @Override
        BigFloat getCeilPrintable(BigFloat value) {
            return value
                    .round(BinaryMathContext.BINARY64
                            .withRoundingMode(RoundingMode.CEILING));
        }

        @Override
        String getCeilString(BigFloat value) {
            return getCeilPrintable(value).toString();
        }

        @Override
        BigFloat getFloorPrintable(BigFloat value) {
            return value
                    .round(BinaryMathContext.BINARY64
                            .withRoundingMode(RoundingMode.FLOOR));
        }
        @Override
        String getFloorString(BigFloat value) {
            return getFloorPrintable(value).toString();
        }

        @Override
        String getDirectString(BigFloat value) {
            return value.round(BinaryMathContext.BINARY64).toString();
        }

    }

    private class SimpleFloatParam extends FloatParam {
        SimpleFloatParam(BinaryMathContext context) {
            super(context);
        }

        @Override
        BinaryMathContext ceiling() {
            return context;
        }

        @Override
        BinaryMathContext floor() {
            return context;
        }

        @Override
        BinaryMathContext direct() {
            return context;
        }

        @Override
        String getCeilString(BigFloat value) {
            return getCeilPrintable(value).toString();
        }

        @Override
        BigFloat getCeilPrintable(BigFloat value) {
            return value.round(BinaryMathContext.BINARY64);
        }

        @Override
        String getFloorString(BigFloat value) {
            return getFloorPrintable(value).toString();
        }

        @Override
        BigFloat getFloorPrintable(BigFloat value) {
            return value.round(BinaryMathContext.BINARY64);
        }

        @Override
        String getDirectString(BigFloat value) {
            return value.round(BinaryMathContext.BINARY64).toString();
        }

    }
}
