package algorithm.abstracts.interfaces;

import algorithm.abstracts.NumberWrapper;

public interface WrapperBuilder <V extends Number & Comparable<V>>{
     NumberWrapper<V> getWrapper(String value);
}
