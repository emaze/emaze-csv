package net.emaze.csv.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

public class WriteAndFlushOn implements Consumer<String> {

    private final Writer writer;

    public WriteAndFlushOn(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void accept(String element) {
        try {
            writer.write(element);
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
