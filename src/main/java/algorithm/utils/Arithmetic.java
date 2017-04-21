package algorithm.utils;

public enum Arithmetic {
    FLOATING("Floating-point"),
    EXTENDED("Extended");

    private String value;
    Arithmetic(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
