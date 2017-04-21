package algorithm;

import algorithm.logic.Interval;
import algorithm.logic.Polynomial;

public class Result<V extends Number & Comparable<V>> {
	public int getIteration() {
		return iteration;
	}

	public Interval<V> getResult() {
		return result;
	}

	public Interval<V> getScope() {
		return scope;
	}

	public String getReason() {
		return reason;
	}

	private final int iteration;

	public Polynomial getPolynomial() {
		return polynomial;
	}

	private final Polynomial<V> polynomial;
	private final Interval<V> result;
	private final Interval<V> scope;
	private final String reason;
	public static final String REASON_NARROW_SCOPE = "narrow enough interval";
	public static final String REASON_POINT = "good enough point";
	public static final String REASON_EXCEEDED_ITERATIONS = "exceeded allowed iterations number";
	public Result(Polynomial<V> polynomial, int iteration, Interval<V> result, Interval<V> scope, String reason) {
		this.polynomial = polynomial;
		this.iteration = iteration;
		this.result = result;
		this.scope = scope;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Result at " +
				"iteration " + iteration +
				"\nresult : " + result +
				"\nscope  : " + scope +
				"\nreason : " + reason;
	}
}
