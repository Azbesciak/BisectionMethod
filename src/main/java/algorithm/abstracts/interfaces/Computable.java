package algorithm.abstracts.interfaces;

import algorithm.logic.Interval;
import algorithm.abstracts.NumberWrapper;

public interface Computable<T extends Number & Comparable<T>> {
    Interval<T> countForValue(NumberWrapper<T> value);
    boolean canBeComputedWith(Interval<T> interval);
    Interval<T> countForInterval(Interval<T> interval);
}
