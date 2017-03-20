package algorithm;

import java.math.BigDecimal;

class Element {

	private BigDecimal factor;
	private Integer exponent;
	private String stringify;
	Element(String[] array) {
		if (array.length >= 1) {
			String factor = array[0];
//				if (factor.contains("V"))
			if ("".equals(factor)) {
				factor = "1";
			} else {
				factor = factor.replace(",", ".");
			}
			this.factor = new BigDecimal(factor);
		}
		if (array.length == 2) {
			exponent = Integer.valueOf(array[1]);
		} else {
			exponent = 0;
		}
		if (array.length == 0) {
			this.factor = BigDecimal.ONE;
			this.exponent = 1;
		}
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
	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
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
		String result = String.valueOf(factor);
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