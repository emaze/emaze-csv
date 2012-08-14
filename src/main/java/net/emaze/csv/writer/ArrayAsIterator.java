package net.emaze.csv.writer;

import java.util.Iterator;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.iterations.ArrayIterator;

public class ArrayAsIterator<T> implements Delegate<Iterator<String>, String[]> {

    @Override
    public Iterator<String> perform(String[] t) {
        return new ArrayIterator<String>(t);
    }

}
