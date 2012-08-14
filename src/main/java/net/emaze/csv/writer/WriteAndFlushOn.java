package net.emaze.csv.writer;

import java.io.IOException;
import java.io.Writer;
import net.emaze.dysfunctional.dispatching.actions.Action;

public class WriteAndFlushOn implements Action<String> {
    private final Writer writer;

    public WriteAndFlushOn(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void perform(String element) {
        try {
            writer.write(element);
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
