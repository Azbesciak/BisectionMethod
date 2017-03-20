package algorithm;

public class Result {
	public int getIteration() {
		return iteration;
	}

	public Interval getResult() {
		return result;
	}

	public Interval getScope() {
		return scope;
	}

	public String getReason() {
		return reason;
	}

	private final int iteration;
	private final Interval result;
	private final Interval scope;
	private final String reason;
	static final String REASON_NARROW_SCOPE = "narrow enough interval";
	static final String REASON_POINT = "good enough point";
	static final String REASON_EXCEEDED_ITERATIONS = "exceeded allowed iterations number";
	public Result(int iteration, Interval result, Interval scope, String reason) {
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
				"\nscope  :  " + scope +
				"\nreason : " + reason;
	}
}
