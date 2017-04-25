package algorithm.utils;

public enum Arithmetic {
    SIMPLE("Simple"),
    INTERVAL("Interval");

    private String value;
    Arithmetic(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
