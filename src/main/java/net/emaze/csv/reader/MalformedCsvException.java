package net.emaze.csv.reader;

public class MalformedCsvException extends IllegalArgumentException {

    public MalformedCsvException() {
    }

    public MalformedCsvException(String s) {
        super(s);
    }

    public MalformedCsvException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedCsvException(Throwable cause) {
        super(cause);
    }
}
