package net.emaze.csv.writer;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import org.jooq.lambda.Seq;

public class AsCsvRow<T> implements Function<Iterator<T>, String> {

    private final CsvFlavour csvFlavour;

    public AsCsvRow(CsvFlavour csvFlavour) {
        Objects.requireNonNull(csvFlavour, "csvFlavour cannot be null");
        this.csvFlavour = csvFlavour;
    }

    @Override
    public String apply(Iterator<T> rowValues) {
        return Seq.seq(rowValues).map(new AsCsvField<T>(csvFlavour)).toString(csvFlavour.getFieldDelimiter());
    }
}