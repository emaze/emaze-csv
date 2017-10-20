package net.emaze.csv.writer;

import org.junit.Assert;
import org.junit.Test;

public class AsCsvFieldTest {

    public static final CsvFlavour flavour = CsvFlavour.MSFT;

    @Test
    public void shouldReturnEmptyStringOnNull() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply(null);
        Assert.assertEquals("", got);
    }

    @Test
    public void shouldEscapeTextDelimiter() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a\"b");
        Assert.assertEquals("\"a\"\"b\"", got);
    }

    @Test
    public void shouldPutTextInsideTextDelimiter() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a");
        Assert.assertEquals("\"a\"", got);
    }

    @Test
    public void shouldReplaceSingleLFWithCRLF() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a\nb");
        Assert.assertEquals("\"a\r\nb\"", got);
    }

    @Test
    public void shouldReplaceSingleCRWithCRLF() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a\rb");
        Assert.assertEquals("\"a\r\nb\"", got);
    }

    @Test
    public void shouldIgnoreCRLF() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a\r\nb");
        Assert.assertEquals("\"a\r\nb\"", got);
    }

    @Test
    public void shouldReplaceAllLFWithCRLF() {
        final AsCsvField<String> subject = new AsCsvField<>(flavour);
        final String got = subject.apply("a\nb\nc");
        Assert.assertEquals("\"a\r\nb\r\nc\"", got);
    }

}