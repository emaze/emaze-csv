package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.function.Function;
import net.emaze.dysfunctional.iterations.ArrayIterator;

public class ArrayAsIterator<T> implements Function<T[], Iterator<T>> {

    @Override
    public Iterator<T> apply(T[] t) {
        return new ArrayIterator<>(t);
    }
}
