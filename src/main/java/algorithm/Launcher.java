package algorithm;

import algorithm.decimal.DecimalFactory;
import algorithm.floating.FloatFactory;

/**
 * Integration layer controller and logic - prepares data to computations
 */
public class Launcher {

	/**
	 * Main method - parses data from view and starts computations. All decimal points need to be as dot ('.').

	 */
	public static Result launch(Params params) {
        switch (params.getArithmetic()) {
            case EXTENDED: {
                return new DecimalFactory().prepareResult(params);
            }
            case FLOATING:
            default: {
                return new FloatFactory().prepareResult(params);
            }
        }
	}
}
