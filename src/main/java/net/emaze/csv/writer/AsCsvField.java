package net.emaze.csv.writer;

import net.emaze.dysfunctional.dispatching.delegates.Delegate;

public class AsCsvField<T> implements Delegate<String, T> {

    private final String textDelimiter;
    private final String escapedTextDelimiter;

    public AsCsvField(CsvFlavour csvFlavour) {
        this.textDelimiter = csvFlavour.getTextDelimiter();
        this.escapedTextDelimiter = csvFlavour.getEscapedTextDelimiter();
    }

    /**
     * @deprecated It exists only for backward compatibility reasons
     */
    @Deprecated
    public AsCsvField() {
        this.textDelimiter = "\"";
        this.escapedTextDelimiter = "\"\"";
    }

    @Override
    public String perform(T property) {
        return textDelimiter + render(property).replace(textDelimiter, escapedTextDelimiter) + textDelimiter;
    }

    private String render(Object aValue) {
        if (aValue == null) {
            return "";
        }
        return aValue.toString();
    }
}
