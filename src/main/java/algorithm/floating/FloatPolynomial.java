package algorithm.floating;

import algorithm.Constants;
import algorithm.abstracts.Element;
import algorithm.abstracts.Polynomial;
import algorithm.abstracts.interfaces.Compartmental;
import org.kframework.mpfr.BigFloat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static algorithm.floating.FloatConstant.ROUND_CEIL_64X;
import static algorithm.floating.FloatConstant.ROUND_FLOOR_64X;
import static algorithm.floating.FloatConstant.ZERO_64X;

class FloatPolynomial extends Polynomial<BigFloat, BigFloat>{
    FloatPolynomial() {
        elements = new ArrayList<>();
    }

    FloatPolynomial(String polynomial) {
        this.elements = prepareElements(polynomial);
    }

    /**
     * Counts value of the polynomial for given argument
     *
     * @param value     given argument
     * @return scope of values for given argument
     */
    public Compartmental<BigFloat> countForValue(BigFloat value) {
        Compartmental<BigFloat> result = new FloatInterval("0");
        for (Element<BigFloat, BigFloat> element : elements) {
            final Compartmental<BigFloat> factor = element.getFactor();
            final BigFloat powLower = value.pow(element.getExponent(), ROUND_FLOOR_64X);
            final BigFloat powUpper = value.pow(element.getExponent(), ROUND_CEIL_64X);
            final Compartmental<BigFloat> toAddLower = factor.multiply(powLower);
            final Compartmental<BigFloat> toAddUpper = factor.multiply(powUpper);
            result = result.add(toAddLower.sum(toAddUpper)); //sum += aixi^i
        }
        return result;
    }

    /**
     * Checks, if a product of computations for given scope's borders is negative - that means there is zero point
     * between them.
     *
     * @param compartmental  Given to computations compartmental - scope
     * @return if computations can be made for given compartmental
     */
    public boolean canBeComputedWith(Compartmental<BigFloat> compartmental) {
        final Compartmental<BigFloat> lowerValue = countForValue(compartmental.getLower());
        final Compartmental<BigFloat> higherValue = countForValue(compartmental.getUpper());
        return lowerValue
                .getLower()
                .multiply(higherValue.getUpper(), ROUND_FLOOR_64X)
                .lessThan(ZERO_64X);
    }

    /**
     * Counts result of polynomial for given as compartmental argument
     *
     * @param compartmental  given scope
     * @return calculation results for values at the edges of compartment
     */
    public Compartmental<BigFloat> countForInterval(Compartmental<BigFloat> compartmental) {
        final Compartmental<BigFloat> lowerResult = countForValue(compartmental.getLower());
        final Compartmental<BigFloat> higherResult = countForValue(compartmental.getUpper());
        return lowerResult.sum(higherResult);
    }

    public boolean isAcceptableScope(Compartmental<BigFloat> interval, BigFloat epsilon) {

        final Compartmental<BigFloat> lowerResult = countForValue(interval.getLower());
        final Compartmental<BigFloat> higherResult = countForValue(interval.getUpper());

        return lowerResult.isLowerOrEqualValueWithAbs(epsilon) ||
                higherResult.isLowerOrEqualValueWithAbs(epsilon);
    }

    public boolean isAcceptableResult(Compartmental<BigFloat> result, BigFloat epsilon) {
        return result.isLowerOrEqualValueWithAbs(epsilon);
    }

    @Override
    protected List<Element<BigFloat, BigFloat>> prepareElements(String polynomial) {
        return numbersPattern.splitAsStream(polynomial)
                .map(t -> new FloatElement(t.split(Constants.ELEMENTS_SPLIT_REGEX)))
                .collect(Collectors.toList());
    }
}

