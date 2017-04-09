package algorithm.abstracts;

import algorithm.Constants;
import algorithm.abstracts.interfaces.Computable;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Polynomial<V extends Number & Comparable<V>, E extends Number & Comparable> implements Computable<V> {
    protected final Pattern numbersPattern = Pattern.compile(Constants.POLYNOMIAL_SPLIT_REGEX);
    protected List<Element<V, E>> elements;
    protected abstract List<Element<V, E>> prepareElements(String polynomial);

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Element element : elements) {
            result.append(element).append(" ");
        }
        return result.toString();
    }
}