package algorithm.abstracts;

import algorithm.abstracts.interfaces.Compartmental;

public abstract class Interval<T extends Number & Comparable> implements Compartmental<T> {
    protected T lower;
    protected T upper;
    protected T delta;

    protected abstract void setDelta(T lower, T upper);

    private boolean isPoint() {
        return lower.equals(upper);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    private String toSimpleString() {
        if (isPoint()) {
            return lower.toString();
        }
        return "[" + lower +", " + upper+ "]";
    }
}
