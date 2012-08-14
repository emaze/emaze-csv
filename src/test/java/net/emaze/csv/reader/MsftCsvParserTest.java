package net.emaze.csv.reader;

import java.io.StringReader;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MsftCsvParserTest {

    @Test
    public void unquotedFields() throws ParseException {
        final StringReader stringReader = new StringReader("mario;luigi".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario", "luigi"), msftCsvParser.record());
    }

    @Test
    public void quotedFields() throws ParseException {
        final StringReader stringReader = new StringReader("'mario';'luigi'".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario", "luigi"), msftCsvParser.record());
    }

    @Test
    public void encodeOfDoubleQuotes() throws ParseException {
        final StringReader stringReader = new StringReader("'mario''rossi';'luigi'".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\"rossi", "luigi"), msftCsvParser.record());
    }

    @Test
    public void commasWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario;rossi';'luigi'".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario;rossi", "luigi"), msftCsvParser.record());
    }

    @Test
    public void newlineWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario\nrossi';'luigi'".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\nrossi", "luigi"), msftCsvParser.record());
    }

    @Test
    public void carriageReturnWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario\rrossi';'luigi'".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\rrossi", "luigi"), msftCsvParser.record());
    }

    @Test
    public void whitespacesArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("  mario  ;  luigi  ".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("  mario  ", "  luigi  "), msftCsvParser.record());
    }
}
