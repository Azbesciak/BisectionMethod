package algorithm.types.decimal;

import algorithm.utils.Params;
import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;

import java.math.BigDecimal;

public class DecimalBuilder implements WrapperBuilder<BigDecimal> {

    private final Integer precision;

    public DecimalBuilder(Params params) {
        precision = Integer.valueOf(params.getPrecision());
    }

    @Override
    public NumberWrapper<BigDecimal> getWrapper(String value) {
        return new DecimalWrapper(value, precision);
    }
}
