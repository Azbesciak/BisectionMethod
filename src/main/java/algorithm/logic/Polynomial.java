package algorithm.logic;

import algorithm.Constants;
import algorithm.abstracts.NumberWrapper;
import algorithm.abstracts.interfaces.WrapperBuilder;
import algorithm.abstracts.interfaces.Computable;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Polynomial<V extends Number & Comparable<V>> implements Computable<V> {
    private final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_SPLIT_REGEX);
    private List<Element<V>> elements;
    private final WrapperBuilder builder;
        public Polynomial(String polynomial, WrapperBuilder builder) {
            this.builder = builder;
            this.elements = prepareElements(polynomial);
        }

        /**
         * Counts value of the polynomial for given argument
         *
         * @param wrapper  wrapped given argument
         * @return scope of values for given argument
         */
        public Interval<V> countForValue(NumberWrapper<V> wrapper) {
            Interval<V> result = new Interval<>(builder.getWrapper("0"));
            for (Element<V> element : elements) {
                final Interval<V> factor = element.getFactor();
                final NumberWrapper<V> powUpper = wrapper.powCeiling(element.getExponent());
                final NumberWrapper<V> powLower = wrapper.powFloor(element.getExponent());
                final Interval<V> toAddLower = factor.multiply(powLower);
                final Interval<V> toAddUpper = factor.multiply(powUpper);
                result = result.add(toAddLower.sum(toAddUpper)); //sum += aixi^i
            }
            return result;
        }

        /**
         * Checks, if a product of computations for given scope's borders is negative - that means there is zero point
         * between them.
         *
         * @param interval  Given to computations interval - scope
         * @return if computations can be made for given decimalInterval
         */
        public boolean canBeComputedWith(Interval<V> interval) {
            final Interval<V> lowerValue = countForValue(interval.getLower());
            final Interval<V> higherValue = countForValue(interval.getUpper());
            return lowerValue.getLower()
                    .multiplyFloor(higherValue.getUpper())
                    .lessThan(builder.getWrapper("0"));
        }

        /**
         * Counts result of polynomial for given as decimalCompartmental argument
         *
         * @param interval  given scope
         * @return calculation results for values at the edges of compartment
         */
        public Interval<V> countForInterval(Interval<V> interval) {
            final Interval<V> lowerResult = countForValue(interval.getLower());
            final Interval<V> higherResult = countForValue(interval.getUpper());
            return lowerResult.sum(higherResult);
        }

        public boolean isAcceptableScope(Interval<V> interval, String epsilon) {
            final NumberWrapper<V> eps = builder.getWrapper(epsilon);
            final Interval<V> lowerResult = countForValue(interval.getLower());
            final Interval<V> higherResult = countForValue(interval.getUpper());

            return lowerResult.isLowerOrEqualValueWithAbs(eps) ||
                    higherResult.isLowerOrEqualValueWithAbs(eps);
        }

        public boolean isAcceptableResult(Interval<V> result, String epsilon) {
            final NumberWrapper<V> eps = builder.getWrapper(epsilon);
            return result.isLowerOrEqualValueWithAbs(eps);
        }

        private List<Element<V>> prepareElements(String polynomial) {

            return numbersPattern.splitAsStream(polynomial)
                    .map(t -> new Element<V>(t.split(Constants.ELEMENTS_SPLIT_REGEX), builder))
                    .collect(Collectors.toList());

        }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Element element : elements) {
            result.append(element).append(" ");
        }
        return result.toString();
    }
}