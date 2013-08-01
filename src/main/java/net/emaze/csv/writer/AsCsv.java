package net.emaze.csv.writer;

import java.util.Iterator;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Strings;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;

public class AsCsv<T> implements Delegate<String, Iterator<Iterator<T>>> {

    private final CsvFlavour flavour;

    public AsCsv(CsvFlavour flavour) {
        this.flavour = flavour;
    }

    @Override
    public String perform(Iterator<Iterator<T>> lines) {
        final Iterator<String> csvLines = Applications.transform(lines, new AsCsvRow<T>(flavour));
        return Strings.interpose(csvLines, CsvFlavour.RECORD_DELIMITER);
    }
}
