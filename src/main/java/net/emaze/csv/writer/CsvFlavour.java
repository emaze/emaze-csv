package net.emaze.csv.writer;

public enum CsvFlavour {
    RFC(",", "\"", "\"\""),
    MSFT(";", "\"", "\"\"");

    public static final String RECORD_DELIMITER = "\r\n";
    
    private CsvFlavour(String fieldDelimiter, String textDelimiter, String escapedTextDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        this.textDelimiter = textDelimiter;
        this.escapedTextDelimiter = escapedTextDelimiter;
    }
    private final String fieldDelimiter;
    private final String textDelimiter;
    private final String escapedTextDelimiter;

    public String getEscapedTextDelimiter() {
        return escapedTextDelimiter;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public String getTextDelimiter() {
        return textDelimiter;
    }
    
}
