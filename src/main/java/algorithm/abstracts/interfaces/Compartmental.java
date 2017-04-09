package algorithm.abstracts.interfaces;

public interface Compartmental<T extends Number & Comparable> {
    Compartmental<T> getCenterPoint();
    Compartmental<T> findSubInterval(Computable<T> polynomial);
    boolean isLowerOrEqualValueWithAbs(T value);
    boolean isNarrowerThan(T width);
    Compartmental<T> add(Compartmental<T> other);
    Compartmental<T> sum(Compartmental<T> other);
    Compartmental<T> multiply(Compartmental<T> other);
    Compartmental<T> multiply(T other);
    Compartmental<T> negate();
    T getLower();
    T getUpper();
    public String getIntervalWithDelta();
}
