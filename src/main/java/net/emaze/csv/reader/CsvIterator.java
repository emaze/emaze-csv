package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.options.Box;

public class CsvIterator implements Iterator<List<String>> {

    private final Box<List<String>> box = Box.empty();
    private final CsvParser csvParser;

    public CsvIterator(CsvParser csvParser) {
        dbc.precondition(csvParser != null, "csvParser must be non-null");
        this.csvParser = csvParser;
    }

    private void prefetch() {
        if (box.isEmpty()) {
            try {
                final List<String> record = csvParser.record();
                final boolean hasMoreRecords = (null != record);
                if (hasMoreRecords) {
                    box.setContent(record);
                }
            } catch (ParseException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public boolean hasNext() {
        prefetch();
        return box.hasContent();
    }

    @Override
    public List<String> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return box.unload().value();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
