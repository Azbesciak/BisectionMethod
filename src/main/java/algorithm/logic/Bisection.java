package algorithm.logic;

import algorithm.Result;
import algorithm.abstracts.NumberWrapper;

public class Bisection<V extends Number & Comparable<V>> {
    private final Polynomial<V> polynomial;
    private final NumberWrapper<V> scopeEpsilon;
    private final NumberWrapper<V> resultEpsilon;

    /**
     * Makes instance of bisection method producer - takes constant parameters.
     *
     * @param polynomial    polynomial for which root need to be found
     * @param scopeEpsilon  epsilon of arguments compartment
     * @param resultEpsilon epsilon of acceptable result
     */
    public Bisection(Polynomial<V> polynomial, NumberWrapper<V> scopeEpsilon, NumberWrapper<V> resultEpsilon) {
        this.polynomial = polynomial;
        this.scopeEpsilon = scopeEpsilon;
        this.resultEpsilon = resultEpsilon;
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
    public Result<V> findZero(Interval<V> scope, Integer allowedIterations) {
        if (allowedIterations != null && allowedIterations < 1) {
            throw new RuntimeException(
                    "Allowed iterations less than one");
        }
        for (int iteration = 0; allowedIterations == null || iteration <= allowedIterations; iteration++) {
            if (!polynomial.canBeComputedWith(scope)) {
                throw new RuntimeException(
                        polynomial + " cannot be computed with interval " + scope + " at iteration " + iteration);
            } else {
                if (scope.isNarrowerThan(scopeEpsilon)) {
                    final Interval<V> solution = polynomial.countForInterval(scope);
                    return new Result<>(polynomial, iteration, solution, scope, Result.REASON_NARROW_SCOPE);
                } else {
                    final Interval<V> centerPoint = scope.getCenterPoint();
                    final Interval<V> result = polynomial.countForInterval(centerPoint);
                    if (result.isLowerOrEqualValueWithAbs(resultEpsilon)) {
                        return new Result<>(polynomial, iteration, result, centerPoint, Result.REASON_POINT);
                    } else {
                        scope = scope.findSubInterval(polynomial);
                    }
                }
            }
        }
        final Interval<V> solution = polynomial.countForInterval(scope);
        return new Result<>(polynomial, allowedIterations, solution, scope, Result.REASON_EXCEEDED_ITERATIONS);
    }
}