package algorithm.abstracts;

public interface WrapperBuilder <V extends Number & Comparable<V>>{
     NumberWrapper<V> getWrapper(String value);
}
