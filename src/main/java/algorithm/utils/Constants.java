package algorithm.utils;

import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;

public class Constants {
	private static final String E_REGEX = "([eE]([+-])?)?\\d*";
	public static final String NUMBER_VARIABLE_SPLIT_REGEX = "([^\\d+(,.\\d{1,2})?]+)";
	public static final String ELEMENTS_SPLIT_REGEX = "[^\\d,\\.+\\-eE]+";
	public static final String NUMBER_VARIABLE_REGEX = "^\\d*([,\\.]\\d*)?" + E_REGEX + "$";
	public static final String TOTAL_NUMBER_VARIABLE_REGEX = "^-?\\d*([,\\.]\\d*)?" + E_REGEX + "$";
	public static final String POLYNOMIAL_SPLIT_REGEX = "\\s+(?=[^\\])}]*([\\[({]|$))";
	public static final String INTEGER_REGEX = "\\d*";
	public static final String SMALL_VALUE_REGEX = INTEGER_REGEX + E_REGEX;

	private static final String SIGNS_REGEX = "(\\s*[-+]?\\s*)";
	private static final String DECIMAL_VALUE_REGEX = SIGNS_REGEX + "\\d+([\\.,]\\d+([eE][+-]?\\d+)?)?\\s*";
	private static final String INTERVAL_REGEX = "\\[" + DECIMAL_VALUE_REGEX + ";" + DECIMAL_VALUE_REGEX + "]";

	private static final String VARIABLE_REGEX = "\\s*([\\w&&[^eE]](\\^\\d+)?\\s*)";
	private static final String INTERVAL_OR_VALUE_REGEX =
			"(" + SIGNS_REGEX  + INTERVAL_REGEX + "|" + DECIMAL_VALUE_REGEX + ")";
	public static final String POLYNOMIAL_TEST_REGEX = "(" + INTERVAL_OR_VALUE_REGEX + VARIABLE_REGEX + "|" +
			SIGNS_REGEX + VARIABLE_REGEX + "|" + INTERVAL_OR_VALUE_REGEX + ")+";

	public static final String TYPE_FLOATING_ARITHMETIC = "Floating-point";
}
