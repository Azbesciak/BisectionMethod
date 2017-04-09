package algorithm.abstracts.interfaces;

import algorithm.Params;
import algorithm.Result;


public interface Factory<V extends Number & Comparable<V>, E extends Number & Comparable<E>> {
    Result<V, E> prepareResult(Params params);
}
