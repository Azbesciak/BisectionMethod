package algorithm.floating;

import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;

class FloatConstant {
    static final BinaryMathContext ROUND_FLOOR_64X = BinaryMathContext.BINARY64.withRoundingMode(RoundingMode.FLOOR);
    static final BinaryMathContext ROUND_CEIL_64X = BinaryMathContext.BINARY64.withRoundingMode(RoundingMode.CEILING);
    static final BigFloat ZERO_64X = new BigFloat("0", BinaryMathContext.BINARY64);
    static final BigFloat ONE_64X = new BigFloat("1", BinaryMathContext.BINARY64);
    static final BigFloat TWO_64X = new BigFloat("2", BinaryMathContext.BINARY64);

}
