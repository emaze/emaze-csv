package net.emaze.csv.writer;

import java.util.function.Function;
import net.emaze.dysfunctional.contracts.dbc;

public class AsCsvField<T> implements Function<T, String> {

    private final String textDelimiter;
    private final String escapedTextDelimiter;

    public AsCsvField(CsvFlavour csvFlavour) {
        dbc.precondition(csvFlavour != null, "csvFlavour cannot be null");
        this.textDelimiter = csvFlavour.getTextDelimiter();
        this.escapedTextDelimiter = csvFlavour.getEscapedTextDelimiter();
    }

    @Override
    public String apply(T property) {
        return textDelimiter + render(property).replace(textDelimiter, escapedTextDelimiter) + textDelimiter;
    }

    private String render(Object aValue) {
        if (aValue == null) {
            return "";
        }
        return aValue.toString();
    }
}
