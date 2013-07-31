package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.emaze.dysfunctional.Iterations;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.dispatching.delegates.Provider;
import net.emaze.dysfunctional.iterations.ReadOnlyIterator;

public class CsvRowsIterator extends ReadOnlyIterator<String> {

    private final Provider<Iterator<Iterator<String>>> pageProvider;
    private final Delegate<String, Iterator<String>> rowRenderer;
    private Iterator<Iterator<String>> prefetchedPage;

    public CsvRowsIterator(Iterator<String> headerColumns, Provider<Iterator<Iterator<String>>> pageProvider, CsvFlavour csvFlavour) {
        this.pageProvider = pageProvider;
        this.rowRenderer = new AsCsvRow<>(csvFlavour);
        this.prefetchedPage = headerColumns.hasNext() ? Iterations.iterator(headerColumns) : Iterations.<Iterator<String>>iterator();
    }

    @Override
    public boolean hasNext() {
        prefetchIfNeeded();
        return prefetchedPage.hasNext();
    }

    private void prefetchIfNeeded() {
        if (prefetchedPage == null || !prefetchedPage.hasNext()) {
            prefetchedPage = pageProvider.provide();
        }
    }

    @Override
    public String next() {
        prefetchIfNeeded();
        if (!prefetchedPage.hasNext()) {
            throw new NoSuchElementException();
        }
        final Iterator<String> valuesForEntity = prefetchedPage.next();
        return rowRenderer.perform(valuesForEntity);
    }
}
