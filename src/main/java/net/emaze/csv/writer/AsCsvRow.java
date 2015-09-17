package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.function.Function;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Strings;
import net.emaze.dysfunctional.contracts.dbc;

public class AsCsvRow<T> implements Function<Iterator<T>, String> {

    private final CsvFlavour csvFlavour;

    public AsCsvRow(CsvFlavour csvFlavour) {
        dbc.precondition(csvFlavour != null, "csvFlavour cannot be null");
        this.csvFlavour = csvFlavour;
    }

    @Override
    public String apply(Iterator<T> rowValues) {
        final Iterator<String> fieldsInARow = Applications.transform(rowValues, new AsCsvField<T>(csvFlavour));
        return Strings.interpose(fieldsInARow, csvFlavour.getFieldDelimiter());
    }
}