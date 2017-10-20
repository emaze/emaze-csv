package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class CsvIterator implements Iterator<List<String>> {

    private final AtomicReference<List<String>> box = new AtomicReference<>();
    private final CsvParser csvParser;

    public CsvIterator(CsvParser csvParser) {
        Objects.requireNonNull(csvParser, "csvParser must be non-null");
        this.csvParser = csvParser;
    }

    private void prefetch() {
        if (box.get() == null) {
            try {
                final List<String> record = csvParser.record();
                final boolean hasMoreRecords = (null != record);
                if (hasMoreRecords) {
                    box.set(record);
                }
            } catch (ParseException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public boolean hasNext() {
        prefetch();
        return box.get() != null;
    }

    @Override
    public List<String> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return box.getAndSet(null);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
