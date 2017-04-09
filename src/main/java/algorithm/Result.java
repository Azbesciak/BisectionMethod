package algorithm;

import algorithm.abstracts.Polynomial;
import algorithm.abstracts.interfaces.Compartmental;

public class Result<V extends Number & Comparable<V>, E extends Number & Comparable<E>> {
	public int getIteration() {
		return iteration;
	}

	public Compartmental<V> getResult() {
		return result;
	}

	public Compartmental<V> getScope() {
		return scope;
	}

	public String getReason() {
		return reason;
	}

	private final int iteration;

	public Polynomial getPolynomial() {
		return polynomial;
	}

	private final Polynomial<V, E> polynomial;
	private final Compartmental<V> result;
	private final Compartmental<V> scope;
	private final String reason;
	public static final String REASON_NARROW_SCOPE = "narrow enough interval";
	public static final String REASON_POINT = "good enough point";
	public static final String REASON_EXCEEDED_ITERATIONS = "exceeded allowed iterations number";
	public Result(Polynomial<V, E> polynomial, int iteration, Compartmental<V> result, Compartmental<V> scope, String reason) {
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
