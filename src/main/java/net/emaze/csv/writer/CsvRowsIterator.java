package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.emaze.csv.writer.CsvFlavour;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Iterations;
import net.emaze.dysfunctional.Strings;
import net.emaze.dysfunctional.dispatching.delegates.Provider;
import net.emaze.dysfunctional.iterations.ReadOnlyIterator;

public class CsvRowsIterator extends ReadOnlyIterator<String> {
    private final Provider<Iterator<Iterator<String>>> pageProvider;
    private final CsvFlavour csvFlavour;
    private Iterator<Iterator<String>> prefetchedPage;

    public CsvRowsIterator(Iterator<String> headerColumns, Provider<Iterator<Iterator<String>>> pageProvider, CsvFlavour csvFlavour) {
        this.pageProvider = pageProvider;
        this.csvFlavour = csvFlavour;
        this.prefetchedPage = Iterations.iterator(headerColumns);
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
        Iterator<String> valuesForEntity = prefetchedPage.next();
        Iterator<String> fieldsInARow = Applications.transform(valuesForEntity, new AsCsvField<String>());
        return Strings.interpose(fieldsInARow, csvFlavour.getFieldDelimiter());
    }

}
