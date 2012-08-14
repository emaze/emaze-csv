package net.emaze.csv.reader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.emaze.dysfunctional.Consumers;
import net.emaze.dysfunctional.Filtering;
import net.emaze.dysfunctional.Multiplexing;
import net.emaze.dysfunctional.Zips;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.iterations.ConstantIterator;

public class FieldsToMap implements Delegate<Map<String, String>, List<String>> {

    private final List<String> columns;

    public FieldsToMap(List<String> columns) {
        dbc.precondition(columns != null, "columns must be non-null");
        this.columns = columns;
    }

    @Override
    public Map<String, String> perform(List<String> values) {
        dbc.precondition(values != null, "values must be non-null");
        final int missingValuesCount = Math.max(0, columns.size() - values.size());
        final Iterator<String> missingValues = Filtering.take(missingValuesCount, new ConstantIterator<String>(""));
        final Iterator<String> allValues = Multiplexing.chain(values.iterator(), missingValues);
        return Consumers.dict(Zips.shortest(columns.iterator(), allValues));
    }
}
