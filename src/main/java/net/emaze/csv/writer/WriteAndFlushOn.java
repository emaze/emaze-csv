package net.emaze.csv.writer;

import java.io.Writer;
import net.emaze.arfio.Arfio;
import net.emaze.arfio.SoftenedWriter;
import net.emaze.dysfunctional.dispatching.actions.Action;

public class WriteAndFlushOn implements Action<String> {

    private final SoftenedWriter writer;

    public WriteAndFlushOn(Writer writer) {
        this.writer = Arfio.softened(writer);
    }

    @Override
    public void perform(String element) {
        writer.write(element);
        writer.flush();
    }
}
