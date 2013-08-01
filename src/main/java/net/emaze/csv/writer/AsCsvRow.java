package net.emaze.csv.writer;

import java.util.Iterator;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Strings;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;

public class AsCsvRow<T> implements Delegate<String, Iterator<T>> {

    private final CsvFlavour csvFlavour;

    public AsCsvRow(CsvFlavour csvFlavour) {
        this.csvFlavour = csvFlavour;
    }

    @Override
    public String perform(Iterator<T> rowValues) {
        final Iterator<String> fieldsInARow = Applications.transform(rowValues, new AsCsvField<T>(csvFlavour));
        return Strings.interpose(fieldsInARow, csvFlavour.getFieldDelimiter());
    }
}