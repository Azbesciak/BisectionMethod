package algorithm.types.floating;

import algorithm.Params;
import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;
import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

public class FloatBuilder implements WrapperBuilder<BigFloat> {

    private final BinaryMathContext context;

    public FloatBuilder(Params params) {
        context = BinaryMathContext.BINARY64;
    }

    @Override
    public NumberWrapper<BigFloat> getWrapper(String value) {
        return new FloatWrapper(value, context);
    }
}
