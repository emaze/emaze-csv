package net.emaze.csv.reader;

import net.emaze.csv.writer.CsvFlavour;
import java.io.Reader;

public class CsvParserFactory {

    public CsvParser create(CsvFlavour fmt, Reader reader) {
        return CsvFlavour.RFC == fmt ? new RfcCsvParser(reader) : new MsftCsvParser(reader);
    }
}
