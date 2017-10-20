package net.emaze.csv.reader;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.jooq.lambda.Seq;

public class FieldsToMap implements Function<List<String>, Map<String, String>> {

    private final List<String> columns;

    public FieldsToMap(List<String> columns) {
        Objects.requireNonNull(columns, "columns must be non-null");
        this.columns = columns;
    }

    @Override
    public Map<String, String> apply(List<String> values) {
        Objects.requireNonNull(values, "values must be non-null");
        final Seq<String> allValues = Seq.seq(values).concat(Seq.generate("")).take(columns.size());
        return Seq.toMap(Seq.zip(columns, allValues));
    }
}
