package net.emaze.csv;

import java.io.StringReader;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class CsvParserTest {

    @Test
    public void unquotedFields() throws ParseException {
        final StringReader stringReader = new StringReader("mario,luigi".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario", "luigi"), csvParser.record());
    }

    @Test
    public void quotedFields() throws ParseException {
        final StringReader stringReader = new StringReader("'mario','luigi'".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario", "luigi"), csvParser.record());
    }

    @Test
    public void encodeOfDoubleQuotes() throws ParseException {
        final StringReader stringReader = new StringReader("'mario''rossi','luigi'".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\"rossi", "luigi"), csvParser.record());
    }

    @Test
    public void commasWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario,rossi','luigi'".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario,rossi", "luigi"), csvParser.record());
    }

    @Test
    public void newlineWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario\nrossi','luigi'".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\nrossi", "luigi"), csvParser.record());
    }

    @Test
    public void carriageReturnWithinQuotedFieldsArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("'mario\rrossi','luigi'".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("mario\rrossi", "luigi"), csvParser.record());
    }

    @Test
    public void whitespacesArePreserved() throws ParseException {
        final StringReader stringReader = new StringReader("  mario  ,  luigi  ".replace('\'', '\"'));
        final CsvParser csvParser = new CsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("  mario  ", "  luigi  "), csvParser.record());
    }
}
