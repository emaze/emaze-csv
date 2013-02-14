package net.emaze.csv.writer;

import java.util.Iterator;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.iterations.ArrayIterator;

public class ArrayAsIterator<T> implements Delegate<Iterator<T>, T[]> {

    @Override
    public Iterator<T> perform(T[] t) {
        return new ArrayIterator<>(t);
    }
}
