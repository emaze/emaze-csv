package net.emaze.csv.reader;

import java.util.Arrays;
import net.emaze.csv.reader.CsvConsumer.IsValidHeader;
import org.junit.Assert;
import org.junit.Test;

public class IsValidHeaderTest {

    @Test
    public void headerWithAllMandatoryFieldsIsValid() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList("A", "B"));
        Assert.assertTrue(valid);
    }

    @Test
    public void headerWithoutSomeMandatoryFieldsIsInvalid() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList("A", "C"));
        Assert.assertFalse(valid);
    }

    @Test
    public void headerWithDifferentColumnOrderIsInvalid() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList("B", "A", "C"));
        Assert.assertFalse(valid);
    }

    @Test
    public void headerWithAdditionalColumnsIsValid() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList("A", "B", "C", "D", "E"));
        Assert.assertTrue(valid);
    }

    @Test
    public void headerWithAllFieldsIsValid() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList("A", "B", "C"));
        Assert.assertTrue(valid);
    }

    @Test
    public void labelsAreTrimmed() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "B", "C"), 2);
        final boolean valid = predicate.accept(Arrays.asList(" A ", "  B ", " C "));
        Assert.assertTrue(valid);
    }

    @Test
    public void labelsAreCaseInsensitive() {
        final IsValidHeader predicate = new IsValidHeader(Arrays.asList("A", "b", "c"), 2);
        final boolean valid = predicate.accept(Arrays.asList("a", "B", "c"));
        Assert.assertTrue(valid);
    }
}
