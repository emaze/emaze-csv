package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CsvIterator implements Iterator<List<String>> {

    private final CsvParser parser;
    private List<String> prefetch = null;

    public CsvIterator(CsvParser parser) {
        Objects.requireNonNull(parser, "CSV parser cannot be null");
        this.parser = parser;
    }

    private void prefetch() {
        if (prefetch == null) {
            try {
                prefetch = parser.record();
            } catch (ParseException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public boolean hasNext() {
        prefetch();
        return prefetch != null;
    }

    @Override
    public List<String> next() {
        prefetch();
        if (prefetch == null) throw new NoSuchElementException();
        final List<String> next = prefetch;
        prefetch = null;
        return next;
    }
}
