package algorithm;

import java.math.BigDecimal;

class Bisection {
	private final Polynomial polynomial;
	private final BigDecimal scopeEpsilon;
	private final BigDecimal resultEpsilon;
	private final int precision;

	Bisection(Polynomial polynomial, BigDecimal scopeEpsilon, BigDecimal resultEpsilon) {
		this.polynomial = polynomial;
		this.scopeEpsilon = scopeEpsilon;
		this.resultEpsilon = resultEpsilon;
		this.precision = Constants.BASE_PRECISION;
	}

	/**
	 * Makes instance of bisection method producer - takes constant parameters.
	 *
	 * @param polynomial    polynomial for which root need to be found
	 * @param scopeEpsilon  epsilon of arguments compartment
	 * @param resultEpsilon epsilon of acceptable result
	 * @param precision     decimal precision of computations
	 */
	Bisection(Polynomial polynomial, BigDecimal scopeEpsilon, BigDecimal resultEpsilon, Integer precision) {
		this.polynomial = polynomial;
		this.scopeEpsilon = scopeEpsilon;
		this.resultEpsilon = resultEpsilon;
		this.precision = precision != null ? precision : Constants.BASE_PRECISION;
	}

	/**
	 * Using bisection method - looks for the root of the function basing on negative product of sub-compartment's
	 * bounds arguments. Works till either given iterations are reached or required result found.
	 * Accepts result when:
	 * 1) if value of the polynomial for argument at the center of scope is acceptable (less than epsilon)
	 * 2) else - if product of results for scope's bounds arguments is negative:
	 * a) if scope is narrower than given epsilon - returns this scope and result for its
	 * b) else finds sub-compartment and goes back to the point 1.
	 *
	 * @param scope             given scope of arguments
	 * @param allowedIterations iterations which can me made to find root of polynomial
	 * @return scope of arguments where polynomial's root can be found with given epsilon or the best reached in
	 * given iterations.
	 */
	Result findZero(Interval scope, Integer allowedIterations) {
		if (allowedIterations != null && allowedIterations < 1) {
			throw new RuntimeException(
					"Allowed iterations less than one");
		}
		return findZero(scope, 0, allowedIterations);
	}

	/**
	 * @param iteration actual iteration
	 * @see Bisection findZero
	 */
	private Result findZero(Interval scope, int iteration, Integer allowedIterations) {
		if (allowedIterations != null && iteration >= allowedIterations) {
			final Interval solution = polynomial.countForInterval(scope, precision);
			return new Result(iteration, solution, scope, Result.REASON_EXCEEDED_ITERATIONS);
		} else if (!polynomial.canBeComputedWith(scope, precision)) {
			throw new RuntimeException(
					polynomial + " cannot be computed with interval " + scope + " at iteration " + iteration);
		} else {
			iteration++;
			if (scope.isNarrowerThan(scopeEpsilon)) {
				final Interval solution = polynomial.countForInterval(scope, precision);
				return new Result(iteration, solution, scope, Result.REASON_NARROW_SCOPE);
			} else {
				final BigDecimal centerPoint = scope.getCenterPoint();
				final Interval result = polynomial.countForValue(centerPoint, precision);
				if (result.isLowerOrEqualValueWithAbs(resultEpsilon)) {
					return new Result(iteration, result, new Interval(centerPoint, precision), Result.REASON_POINT);
				} else {
					final Interval subInterval = scope.findSubInterval(polynomial);
					return findZero(subInterval, iteration, allowedIterations);
				}
			}
		}
	}

}