package net.emaze.csv.writer;

import java.io.Writer;
import java.util.function.Consumer;
import net.emaze.arfio.Arfio;
import net.emaze.arfio.SoftenedWriter;

public class WriteAndFlushOn implements Consumer<String> {

    private final SoftenedWriter writer;

    public WriteAndFlushOn(Writer writer) {
        this.writer = Arfio.softened(writer);
    }

    @Override
    public void accept(String element) {
        writer.write(element);
        writer.flush();
    }
}
