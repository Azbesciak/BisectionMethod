package algorithm.decimal;

import algorithm.Constants;
import algorithm.abstracts.Element;
import algorithm.abstracts.Polynomial;
import algorithm.abstracts.interfaces.Compartmental;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class DecimalPolynomial extends Polynomial<BigDecimal, Integer> {
	private final int precision;

	DecimalPolynomial(String polynomial, Integer precision) {
		this.precision = precision;
		this.elements = prepareElements(polynomial);
	}

	/**
	 * Counts value of the polynomial for given argument
	 *
	 * @param value     given argument
	 * @return scope of values for given argument
	 */
	public Compartmental<BigDecimal> countForValue(BigDecimal value) {
		Compartmental<BigDecimal> result = new DecimalInterval(BigDecimal.ZERO, precision);
		for (Element<BigDecimal, Integer> element : elements) {
			final Compartmental<BigDecimal> factor = element.getFactor();
			final BigDecimal pow = value.pow(element.getExponent());
			final BigDecimal powUpper = pow.setScale(precision, BigDecimal.ROUND_CEILING);
			final BigDecimal powLower = pow.setScale(precision, BigDecimal.ROUND_FLOOR);
			final Compartmental<BigDecimal> toAddLower = factor.multiply(powLower);
			final Compartmental<BigDecimal> toAddUpper = factor.multiply(powUpper);
			result = result.add(toAddLower.sum(toAddUpper)); //sum += aixi^i
		}
		return result;
	}

	/**
	 * Checks, if a product of computations for given scope's borders is negative - that means there is zero point
	 * between them.
	 *
	 * @param decimalInterval  Given to computations decimalInterval - scope
	 * @return if computations can be made for given decimalInterval
	 */
	public boolean canBeComputedWith(Compartmental<BigDecimal> decimalInterval) {
		final Compartmental<BigDecimal> lowerValue = countForValue(decimalInterval.getLower());
		final Compartmental<BigDecimal> higherValue = countForValue(decimalInterval.getUpper());
		return lowerValue
				.getLower()
				.multiply(higherValue.getUpper())
				.compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * Counts result of polynomial for given as decimalCompartmental argument
	 *
	 * @param decimalCompartmental  given scope
	 * @return calculation results for values at the edges of compartment
	 */
	public Compartmental<BigDecimal> countForInterval(Compartmental<BigDecimal> decimalCompartmental) {
		final Compartmental<BigDecimal> lowerResult = countForValue(decimalCompartmental.getLower());
		final Compartmental<BigDecimal> higherResult = countForValue(decimalCompartmental.getUpper());
		return lowerResult.sum(higherResult);
	}

	public boolean isAcceptableScope(DecimalInterval decimalInterval, BigDecimal epsilon) {

		final Compartmental<BigDecimal> lowerResult = countForValue(decimalInterval.getLower());
		final Compartmental<BigDecimal> higherResult = countForValue(decimalInterval.getUpper());

		return lowerResult.isLowerOrEqualValueWithAbs(epsilon) ||
				higherResult.isLowerOrEqualValueWithAbs(epsilon);
	}

	public boolean isAcceptableResult(DecimalInterval result, BigDecimal epsilon) {
		return result.isLowerOrEqualValueWithAbs(epsilon);
	}

	@Override
	protected List<Element<BigDecimal, Integer>> prepareElements(String polynomial) {
		return numbersPattern.splitAsStream(polynomial)
				.map(t -> new DecimalElement(t.split(Constants.ELEMENTS_SPLIT_REGEX), precision))
				.collect(Collectors.toList());

	}
}
