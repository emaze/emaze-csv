package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.Consumers;
import net.emaze.dysfunctional.Filtering;

public class CsvConsumer {

    public static void assertValidHeader(Iterator<List<String>> lines, List<String> validHeader, int mandatoryFieldsCount) throws MalformedCsvException {
        try {
            final List<String> header = lines.next();
            if (!new IsValidHeader(validHeader, mandatoryFieldsCount).test(header)) {
                throw new MalformedCsvException(String.format("Expected CSV with header %s, given %s", validHeader, header));
            }
        } catch (NoSuchElementException ex) {
            throw new MalformedCsvException("Unexpected empty CSV");
        }
    }

    /**
     * It accepts headers with the columns in the same order of the valid header, having at least the mandatory fields.
     * Additional columns are considered valid (they will ignored).
     */
    public static class IsValidHeader implements Predicate<List<String>> {

        private final List<String> validHeader;
        private final int mandatoryFieldsCount;

        public IsValidHeader(List<String> validHeader, int mandatoryFieldsCount) {
            this.validHeader = normalize(validHeader);
            this.mandatoryFieldsCount = mandatoryFieldsCount;
        }

        @Override
        public boolean test(List<String> header) {
            final int expectedHeaderSize = Math.max(mandatoryFieldsCount, Math.min(header.size(), validHeader.size()));
            final List<String> normalizedHeader = Consumers.all(Filtering.take(expectedHeaderSize, normalize(header)));
            final List<String> expectedHeader = Consumers.all(Filtering.take(expectedHeaderSize, validHeader));
            return normalizedHeader.equals(expectedHeader);
        }

        private static List<String> normalize(List<String> values) {
            return Applications.map(values, (String value) -> value.trim().toUpperCase());
        }
    }
}
