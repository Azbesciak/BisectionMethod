package algorithm;

import java.math.BigDecimal;

class Bisection {
	private final Polynomial polynomial;
	private final BigDecimal scopeEpsilon;
	private final BigDecimal resultEpsilon;
	private final int precision;

	public Bisection(Polynomial polynomial, BigDecimal scopeEpsilon, BigDecimal resultEpsilon) {
		this.polynomial = polynomial;
		this.scopeEpsilon = scopeEpsilon;
		this.resultEpsilon = resultEpsilon;
		this.precision = Constants.BASE_PRECISION;
	}

	public Bisection(Polynomial polynomial, BigDecimal scopeEpsilon, BigDecimal resultEpsilon, Integer precision) {
		this.polynomial = polynomial;
		this.scopeEpsilon = scopeEpsilon;
		this.resultEpsilon = resultEpsilon;
		this.precision = precision != null ? precision : Constants.BASE_PRECISION;
	}

	public Result findZero(Interval scope, Integer allowedIterations) {
		if (allowedIterations != null && allowedIterations < 1) {
			throw new RuntimeException(
					"Allowed iterations less than one");
		}
		return findZero(scope,  0, allowedIterations);
	}

	private Result findZero(Interval scope, int iteration, Integer allowedIterations) {

		if (allowedIterations != null && iteration >= allowedIterations) {
			final Interval solution = polynomial.countForInterval(scope, precision);
			return new Result(iteration, solution, scope, Result.REASON_EXCEEDED_ITERATIONS);
		} else if (!polynomial.canBeComputedWith(scope, precision)) {
//				if (polynomial.isAcceptableScope(scope)) {
//					final algorithm.Interval solution = polynomial.countForValue(scope.getCenterPoint());
//					new algorithm.Result(iteration, solution, scope);
//				}
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
					return findZero( subInterval,  iteration, allowedIterations);
				}
			}
		}

//			final BigDecimal centerPoint = scope.getCenterPoint();
//			final algorithm.Interval result = polynomial.countForValue(centerPoint);
//			if (result.isPoint() && result.lower.equals(BigDecimal.ZERO)) {
//				System.out.println("iteracja " + iteration);
//				return result;
//			} else {
//				final algorithm.Interval subInterval = scope.findSubInterval(polynomial);
////				if (polynomial.isAcceptableScope(subInterval)) {
//				if (subInterval.isNarrowerThan(epsilon)){
//					System.out.println(polynomial.countForValue(subInterval.lower));
//					System.out.println("iteracja " + iteration);
////					System.out.println(polynomial.countForValue(subInterval.higher));
//					return subInterval;
//				} else {
//					return findZero(polynomial, subInterval, epsilon, ++iteration);
//				}
//			}
	}

}