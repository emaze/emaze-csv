package net.emaze.csv.writer;

import net.emaze.dysfunctional.dispatching.delegates.Delegate;

public class AsCsvField<T> implements Delegate<String, T> {

    private static final String TEXT_DELIMITER = "\"";
    private static final String ESCAPED_TEXT_DELIMITER = TEXT_DELIMITER + TEXT_DELIMITER;

    @Override
    public String perform(T property) {
        return TEXT_DELIMITER + render(property).replace(TEXT_DELIMITER, ESCAPED_TEXT_DELIMITER) + TEXT_DELIMITER;
    }

    private String render(Object aValue) {
        if (aValue == null) {
            return "";
        }
        return aValue.toString();
    }
}
