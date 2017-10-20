package net.emaze.csv.reader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class FieldsToMapTest {

    @Test(expected = NullPointerException.class)
    public void columnsMustBeNonNull() {
        new FieldsToMap(null);
    }

    @Test(expected = NullPointerException.class)
    public void valuesMustBeNonNull() {
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name"));
        fieldsToMap.apply(null);
    }

    @Test
    public void yieldsColumnsAndDataPairwise() {
        final List<String> values = Arrays.asList("mario", "30");
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name", "age"));
        final Map<String, String> got = fieldsToMap.apply(values);
        final Map<String, String> expected = new HashMap<String, String>() {{
            put("name", "mario");
            put("age", "30");
        }};
        Assert.assertEquals(expected, got);
    }

    @Test
    public void fillMissingValueWithEmptyString() {
        final List<String> values = Arrays.asList("mario");
        final List<String> columns = Arrays.asList("name", "age");
        final FieldsToMap fieldsToMap = new FieldsToMap(columns);
        final Map<String, String> got = fieldsToMap.apply(values);
        final Map<String, String> expected = new HashMap<String, String>() {{
            put("name", "mario");
            put("age", "");
        }};
        Assert.assertEquals(expected, got);
    }

    @Test
    public void ignoresLinesWithMoreColumnsThanSpecified() {
        final List<String> values = Arrays.asList("mario", "30", "roma");
        final FieldsToMap fieldsToMap = new FieldsToMap(Arrays.asList("name", "age"));
        final Map<String, String> got = fieldsToMap.apply(values);
        final Map<String, String> expected = new HashMap<String, String>() {{
            put("name", "mario");
            put("age", "30");
        }};
        Assert.assertEquals(expected, got);
    }
}
