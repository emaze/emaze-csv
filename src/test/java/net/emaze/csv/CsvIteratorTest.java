package net.emaze.csv;

import java.io.StringReader;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class CsvIteratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void bufferedReaderMustBeNonNull() {
        new CsvIterator(null);
    }

    @Test
    public void yieldsSoleRecord() {
        final CsvIterator iterator = new CsvIterator(new StringReader("a,b"));
        Assert.assertEquals(Arrays.asList("a", "b"), iterator.next());
    }

    @Test
    public void yieldsMultipleRecords() {
        final CsvIterator csvIterator = new CsvIterator(new StringReader("a\nb"));
        csvIterator.next();
        Assert.assertEquals(Arrays.asList("b"), csvIterator.next());
    }

    @Test
    public void hasNextWhenStringHasOneLineYeldsTrue() {
        final CsvIterator iterator = new CsvIterator(new StringReader("a"));
        Assert.assertEquals(true, iterator.hasNext());
    }

    @Test
    public void hasNextYieldsFalseWhenThereAreNoMoreLines() {
        final CsvIterator iterator = new CsvIterator(new StringReader("a"));
        iterator.next();
        Assert.assertEquals(false, iterator.hasNext());
    }

    @Test
    public void hasNextIsIdempotent() {
        final CsvIterator iterator = new CsvIterator(new StringReader("a\nb"));
        iterator.hasNext();
        iterator.hasNext();
        Assert.assertEquals(Arrays.asList("a"), iterator.next());
    }

    @Test
    public void skipEmptyLines() {
        final CsvIterator iterator = new CsvIterator(new StringReader("a\n\nb"));
        iterator.next();
        Assert.assertEquals(Arrays.asList("b"), iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsNoSuchElementWhenHasNoNextElement() {
        final CsvIterator iterator = new CsvIterator(new StringReader("\n"));
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeIsNotSupported() {
        final CsvIterator iterator = new CsvIterator(new StringReader("\n"));
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void throwsOnParseError() {
        final CsvIterator iterator = new CsvIterator(new StringReader(",,\n"));
        iterator.next();
    }
}
