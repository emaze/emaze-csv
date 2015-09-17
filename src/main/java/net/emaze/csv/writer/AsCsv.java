package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.function.Function;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Strings;
import net.emaze.dysfunctional.contracts.dbc;

public class AsCsv<T> implements Function<Iterator<Iterator<T>>, String> {

    private final CsvFlavour flavour;

    public AsCsv(CsvFlavour flavour) {
        dbc.precondition(flavour != null, "flavour cannot be null");
        this.flavour = flavour;
    }

    @Override
    public String apply(Iterator<Iterator<T>> lines) {
        final Iterator<String> csvLines = Applications.transform(lines, new AsCsvRow<T>(flavour));
        return Strings.interpose(csvLines, CsvFlavour.RECORD_DELIMITER);
    }
}
