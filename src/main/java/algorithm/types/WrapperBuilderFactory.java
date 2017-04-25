package algorithm.types;

import algorithm.utils.NumberType;
import algorithm.utils.Params;
import algorithm.abstracts.interfaces.WrapperBuilder;
import algorithm.types.decimal.DecimalBuilder;
import algorithm.types.floating.FloatBuilder;


public class WrapperBuilderFactory {
    public static WrapperBuilder getWrapper(Params params) {
        final NumberType numberType = params.getNumberType();
        switch (numberType) {
            case ARBITRARY:
                return new DecimalBuilder(params);
            case FLOATING:
            default:
                return new FloatBuilder(params);
        }
    }
}
