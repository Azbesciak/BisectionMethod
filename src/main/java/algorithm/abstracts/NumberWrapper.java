package algorithm.abstracts;



public abstract class NumberWrapper<V extends Number & Comparable<V>>
        extends Number implements Comparable<NumberWrapper<V>> {
    protected V value;

    protected NumberWrapper(V value) {
        this.value = value;
    }

    public abstract NumberWrapper<V> addCeiling(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> addFloor(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> minusFloor(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> minusCeiling(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> minus(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> divideCeiling(String value);
    public abstract NumberWrapper<V> divideCeiling(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> divideFloor(String value);
    public abstract NumberWrapper<V> divideFloor(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> multiplyFloor(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> multiplyCeiling(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> abs();
    public abstract NumberWrapper<V> ceil();
    public abstract NumberWrapper<V> floor();
    public abstract NumberWrapper<V> powCeiling(String exponent);
    public abstract NumberWrapper<V> powFloor(String exponent);
    public abstract NumberWrapper<V> max(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> min(NumberWrapper<V> wrapper);
    public abstract NumberWrapper<V> negate();

    public abstract boolean lessThan(NumberWrapper<V> wrapper);
    public abstract boolean lessThan(V value);
    public abstract boolean lessThanOrEqualTo(NumberWrapper<V> wrapper);
    public abstract boolean lessThanOrEqualTo(V value);
    public abstract boolean equalTo(NumberWrapper<V> wrapper);
    public abstract boolean equalTo(V value);
    public abstract boolean greaterThan(NumberWrapper<V> wrapper);
    public abstract boolean greaterThan(V value);

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
