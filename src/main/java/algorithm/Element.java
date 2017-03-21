package algorithm;

import java.math.BigDecimal;

class Element {

	private final static int SINGLE_VARIABLE = 0;
	private final static int JUST_VALUE = 1;
	private final static int VALUE_WITH_POWER = 2;
	private final static int INTERVAL_AS_VALUE = 3;
	private final static int INTERVAL_WITH_EXPONENT = 4;

	private Interval factor;
	private Integer exponent;
	private String stringify;
	private int precision;
	Element(String[] array, int precision) {
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
			factor = new Interval(BigDecimal.ONE, precision);
		}
		if (exponent == null) {
			exponent = 1;
		}
	}

	private void prepareSingleValueElement(String value) {
		String factor = value;
		if ("+".equals(factor) || "".equals(factor)) {
			factor = "1";
		} else if ("-".equals(factor)) {
			factor = "-1";
		}
		final BigDecimal temp = new BigDecimal(factor);
		this.factor = new Interval(temp.setScale(precision, BigDecimal.ROUND_DOWN),
								   temp.setScale(precision, BigDecimal.ROUND_UP),
								   precision);
	}

	private void prepareIntervalElement(String [] array) {
		final Interval interval = new Interval(array[1], array[2], precision);
		factor = "-".equals(array[0]) ? interval.negate() : interval;
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

	public Interval getFactor() {
		return factor;
	}

	public void setFactor(Interval factor) {
		this.factor = factor;
	}

	public Integer getExponent() {
		return exponent;
	}

	public void setExponent(Integer exponent) {
		this.exponent = exponent;
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