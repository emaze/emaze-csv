package net.emaze.csv;

import java.io.Reader;

public class CsvParserFactory {
    public enum Format {RFC, MSFT};
    
    public CsvParser create(Format fmt, Reader reader) {
        return Format.RFC == fmt ? new RfcCsvParser(reader) : new MsftCsvParser(reader);
    }
}
