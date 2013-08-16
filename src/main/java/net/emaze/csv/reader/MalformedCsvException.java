package net.emaze.csv.reader;

public class MalformedCsvException extends IllegalArgumentException {

    public MalformedCsvException(String message) {
        super(message);
    }

    public MalformedCsvException(Throwable cause) {
        super(cause);
    }
}
