package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import org.jooq.lambda.Seq;

public class AsCsv<T> implements Function<Iterator<Iterator<T>>, String> {

    private final CsvFlavour flavour;

    public AsCsv(CsvFlavour flavour) {
        Objects.requireNonNull(flavour, "flavour cannot be null");
        this.flavour = flavour;
    }

    @Override
    public String apply(Iterator<Iterator<T>> lines) {
        return Seq.seq(lines).map(new AsCsvRow<>(flavour)).toString(CsvFlavour.RECORD_DELIMITER);
    }
}
