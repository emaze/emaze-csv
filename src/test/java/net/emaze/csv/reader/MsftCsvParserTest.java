package net.emaze.csv.reader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void twoLines() throws ParseException {
        final StringReader stringReader = new StringReader(";mario;rossi\n;luigi;verdi".replace('\'', '\"'));
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        Assert.assertEquals(Arrays.asList("", "mario", "rossi"), msftCsvParser.record());
        Assert.assertEquals(Arrays.asList("", "luigi", "verdi"), msftCsvParser.record());
    }
    
    @Test
    public void nonTerminalEmptyRecord() throws ParseException {
        final StringReader stringReader = new StringReader("\nmario");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertEquals(Arrays.asList(""), record);
    }
    
    @Test
    public void terminalEmptyRecord() throws ParseException {
        final StringReader stringReader = new StringReader("");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertNull(record);
    }

    @Test
    public void emptyRecord() throws ParseException {
        final StringReader stringReader = new StringReader("");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertNull(record);
    }

    @Test
    public void twoEmptyFields() throws ParseException {
        final StringReader stringReader = new StringReader(";");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertEquals(Arrays.asList("", ""), record);
    }

    @Test
    public void threeEmptyFields() throws ParseException {
        final StringReader stringReader = new StringReader(";;");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertEquals(Arrays.asList("", "", ""), record);
    }
    
    @Test
    public void emptyFieldBeforeNonEmpty() throws ParseException {
        final StringReader stringReader = new StringReader(";asd");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertEquals(Arrays.asList("", "asd"), record);
    }
    
    @Test
    public void emptyFieldAfterNonEmpty() throws ParseException {
        final StringReader stringReader = new StringReader("asd;");
        final MsftCsvParser msftCsvParser = new MsftCsvParser(stringReader);
        final List<String> record = msftCsvParser.record();
        Assert.assertEquals(Arrays.asList("asd", ""), record);
    }
}
