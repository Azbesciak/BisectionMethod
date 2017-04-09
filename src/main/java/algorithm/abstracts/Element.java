package algorithm.abstracts;

import algorithm.abstracts.interfaces.Compartmental;

public abstract class Element<V extends Number & Comparable, E extends Number & Comparable> {
    protected final static int SINGLE_VARIABLE = 0;
    protected final static int JUST_VALUE = 1;
    protected final static int VALUE_WITH_POWER = 2;
    protected final static int INTERVAL_AS_VALUE = 3;
    protected final static int INTERVAL_WITH_EXPONENT = 4;

    protected Compartmental<V> factor;
    protected E exponent;

    protected abstract void prepareIntervalElement(String[] array);
    protected abstract void prepareSingleValueElement(String value);

    public Compartmental<V> getFactor() {
        return factor;
    }

    public void setFactor(Compartmental<V> factor) {
        this.factor = factor;
    }

    public E getExponent() {
        return exponent;
    }

    public void setExponent(E exponent) {
        this.exponent = exponent;
    }

}
