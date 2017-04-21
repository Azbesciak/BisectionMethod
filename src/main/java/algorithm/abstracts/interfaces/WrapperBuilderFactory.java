package algorithm.abstracts.interfaces;

import algorithm.Arithmetic;
import algorithm.Params;
import algorithm.abstracts.WrapperBuilder;
import algorithm.decimal.DecimalBuilder;
import algorithm.floating.FloatBuilder;


public class WrapperBuilderFactory {
    public static WrapperBuilder getWrapper(Params params) {
        final Arithmetic arithmetic = params.getArithmetic();
        switch (arithmetic) {
            case EXTENDED:
                return new DecimalBuilder(params);
            case FLOATING:
            default:
                return new FloatBuilder(params);
        }
    }
}
