package net.emaze.csv.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.emaze.dysfunctional.Maps;
import org.junit.Assert;
import org.junit.Test;

public class FieldsToMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void columnsMustBeNonNull() {
        final FieldsToMap fieldsToMap = new FieldsToMap(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valuesMustBeNonNull() {
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name"));
        fieldsToMap.perform(null);
    }

    @Test
    public void yieldsColumnsAndDataPairwise() {
        final List<String> values = Arrays.asList("mario", "30");
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name", "age"));
        final Map<String, String> got = fieldsToMap.perform(values);
        final Map<String, String> expected = Maps.<String, String>builder().add("name", "mario").add("age", "30").toMap();
        Assert.assertEquals(expected, got);
    }

    @Test
    public void fillMissingValueWithEmptyString() {
        final List<String> values = Arrays.asList("mario");
        final List<String> columns = Arrays.asList("name", "age");
        final FieldsToMap fieldsToMap = new FieldsToMap(columns);
        final Map<String, String> got = fieldsToMap.perform(values);
        final Map<String, String> expected = Maps.<String, String>builder().add("name", "mario").add("age", "").toMap();
        Assert.assertEquals(expected, got);
    }

    @Test
    public void ignoresLinesWithMoreColumnsThanSpecified() {
        final List<String> values = Arrays.asList("mario", "30", "roma");
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name", "age"));
        final Map<String, String> got = fieldsToMap.perform(values);
        final Map<String, String> expected = Maps.<String, String>builder().add("name", "mario").add("age", "30").toMap();
        Assert.assertEquals(expected, got);
    }
}
