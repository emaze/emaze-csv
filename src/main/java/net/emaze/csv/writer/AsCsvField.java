package net.emaze.csv.writer;

import java.util.Objects;
import java.util.function.Function;

public class AsCsvField<T> implements Function<T, String> {

    private final String textDelimiter;
    private final String escapedTextDelimiter;

    public AsCsvField(CsvFlavour csvFlavour) {
        Objects.requireNonNull(csvFlavour, "csvFlavour cannot be null");
        this.textDelimiter = csvFlavour.getTextDelimiter();
        this.escapedTextDelimiter = csvFlavour.getEscapedTextDelimiter();
    }

    @Override
    public String apply(T property) {
        if (property == null) {
            return "";
        }
        return textDelimiter + render(property).replace(textDelimiter, escapedTextDelimiter) + textDelimiter;
    }

    private String render(Object property) {
        return property.toString().replaceAll("\\r\\n|\\r|\\n", "\r\n");
    }
}
