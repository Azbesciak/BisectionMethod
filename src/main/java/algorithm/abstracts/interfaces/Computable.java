package algorithm.abstracts.interfaces;

public interface Computable<T extends Number & Comparable> {
    Compartmental<T> countForValue(T value);
    boolean canBeComputedWith(Compartmental<T> compartmental);
    Compartmental<T> countForInterval(Compartmental<T> compartmental);
}
