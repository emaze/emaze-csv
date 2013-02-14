package net.emaze.csv.reader;

import java.io.Reader;
import net.emaze.csv.writer.CsvFlavour;

public class CsvParserFactory {

    public CsvParser create(CsvFlavour fmt, Reader reader) {
        return CsvFlavour.RFC == fmt ? new RfcCsvParser(reader) : new MsftCsvParser(reader);
    }
}
