package algorithm.types;

import algorithm.Arithmetic;
import algorithm.Params;
import algorithm.abstracts.interfaces.WrapperBuilder;
import algorithm.types.decimal.DecimalBuilder;
import algorithm.types.floating.FloatBuilder;


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
