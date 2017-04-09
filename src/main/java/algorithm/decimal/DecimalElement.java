package algorithm.decimal;

import algorithm.abstracts.Element;
import algorithm.abstracts.interfaces.Compartmental;

import java.math.BigDecimal;

class DecimalElement extends Element<BigDecimal, Integer>{
	private int precision;

	DecimalElement(String[] array, int precision) {
		this.precision = precision;
		switch(array.length) {

			case VALUE_WITH_POWER: {
				exponent = Integer.valueOf(array[1]);
			}
			case JUST_VALUE: {
				prepareSingleValueElement(array[0]);
				break;
			}
			case INTERVAL_WITH_EXPONENT: {
				exponent = Integer.valueOf(array[3]);
			}
			case INTERVAL_AS_VALUE: {
				prepareIntervalElement(array);
			}
			default: break;
		}
		if (factor == null) {
			factor = new DecimalInterval(BigDecimal.ONE, precision);
		}
		if (exponent == null) {
			exponent = 0;
		}
	}

	protected void prepareSingleValueElement(String value) {
		String factor = value;
		if ("+".equals(factor) || "".equals(factor)) {
			factor = "1";
		} else if ("-".equals(factor)) {
			factor = "-1";
		}
		final BigDecimal temp = new BigDecimal(factor);
		final BigDecimal lower = temp.setScale(precision, BigDecimal.ROUND_FLOOR);
		final BigDecimal upper = temp.setScale(precision, BigDecimal.ROUND_CEILING);
		this.factor = new DecimalInterval(lower, upper, precision);
	}

	protected void prepareIntervalElement(String [] array) {
		final DecimalInterval decimalInterval = new DecimalInterval(array[1], array[2], precision);
		factor = "-".equals(array[0]) ? decimalInterval.negate() : decimalInterval;
	}

	public void multiplyBySign(String sign) {
		if ("-".equals(sign)) {
			factor = factor.negate();
		}
	}

	private String makeString(int rootPosition) {
		String result = "";
		if (rootPosition >= 0) {
			result += "\u221A";
		}
		result += factor;
		if (exponent > 0) {
			if ("1".equals(result)) {
				result = "";
			} else if ("-1".equals(result)) {
				result = "-";
			}
			result += "x";
			if (exponent > 1) {
				result += "^" + exponent;
			}
		}
		return result;
	}

	public Compartmental<BigDecimal> getFactor() {
		return factor;
	}

	public void setFactor(Compartmental<BigDecimal> factor) {
		this.factor = factor;
	}

	public Integer getExponent() {
		return exponent;
	}

	public void setExponent(Integer exponent) {
		this.exponent = exponent;
	}

	public int getPrecision() {
		return precision;
	}

	@Override
	public String toString() {
		String result = factor.toString();
		if (exponent > 0) {
			if ("1".equals(result)) {
				result = "";
			} else if ("-1".equals(result)) {
				result = "-";
			}
			result += "x";
			if (exponent > 1) {
				result += "^" + exponent;
			}
		}
		return result;
	}

}