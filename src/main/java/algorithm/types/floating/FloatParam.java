package algorithm.types.floating;

import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

abstract class FloatParam {
    protected final BinaryMathContext context;

    FloatParam(BinaryMathContext context) {
        this.context = context;
    }

    abstract BinaryMathContext ceiling();
    abstract BinaryMathContext floor();
    abstract BinaryMathContext direct();

    abstract String getCeilString(BigFloat value);
    abstract BigFloat getCeilPrintable(BigFloat value);
    abstract String getFloorString(BigFloat value);
    abstract BigFloat getFloorPrintable(BigFloat value);
    abstract String getDirectString(BigFloat value);
}
