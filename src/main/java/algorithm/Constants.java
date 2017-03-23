package algorithm;

public class Constants {
	public final static int BASE_PRECISION = 16;
	private static final String E_REGEX = "([eE]([+-])?)?\\d*";
	public static final String NUMBER_VARIABLE_SPLIT_REGEX = "([^\\d+(,.\\d{1,2})?]+)";
	public static final String ELEMENTS_SPLIT_REGEX = "[^\\d,\\.+\\-eE]+";
	public static final String NUMBER_VARIABLE_REGEX = "^\\d*([,\\.]\\d*)?" + E_REGEX + "$";
	public static final String TOTAL_NUMBER_VARIABLE_REGEX = "^-?\\d*([,\\.]\\d*)?" + E_REGEX + "$";
	public static final String SIGNS_STREAM_REGEX = "([^-+]+)";
	public static final String POLYNOMIAL_SPLIT_REGEX = "\\s+(?=[^\\])}]*([\\[({]|$))";
	public static final String INTEGER_REGEX = "\\d*";
	public static final String SMALL_VALUE_REGEX = INTEGER_REGEX + E_REGEX;
}
