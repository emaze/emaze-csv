package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Consumers;
import net.emaze.dysfunctional.Filtering;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.dispatching.logic.Predicate;

public class CsvConsumer {

    public static void assertValidHeader(Iterator<List<String>> lines, List<String> validHeader, int mandatoryFieldsCount) throws MalformedCsvException {
        try {
            final List<String> header = lines.next();
            if (!new IsValidHeader(validHeader, mandatoryFieldsCount).accept(header)) {
                throw new MalformedCsvException(String.format("Expected CSV with header %s, given %s", validHeader, header));
            }
        } catch (NoSuchElementException ex) {
            throw new MalformedCsvException("Unexpected empty CSV");
        }
    }

    /**
     * It accepts headers with the columns in the same order of the valid header, having at least the mandatory fields.
     */
    public static class IsValidHeader implements Predicate<List<String>> {

        private final List<String> validHeader;
        private final int mandatoryFieldsCount;

        public IsValidHeader(List<String> validHeader, int mandatoryFieldsCount) {
            this.validHeader = normalize(validHeader);
            this.mandatoryFieldsCount = mandatoryFieldsCount;
        }

        @Override
        public boolean accept(List<String> header) {
            final List<String> normalizedHeader = normalize(header);
            final int expectedHeaderSize = Math.max(mandatoryFieldsCount, header.size());
            final List<String> expectedHeader = Consumers.all(Filtering.take(expectedHeaderSize, validHeader));
            return normalizedHeader.equals(expectedHeader);
        }

        private static List<String> normalize(List<String> values) {
            return Applications.map(values, new Delegate<String, String>() {
                @Override
                public String perform(String value) {
                    return value.trim().toUpperCase();
                }
            });
        }
    }
}
