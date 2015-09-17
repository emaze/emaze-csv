package net.emaze.csv.writer;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;
import net.emaze.arfio.Arfio;
import net.emaze.arfio.SoftenedIOException;
import net.emaze.arfio.SoftenedOutputStreamWriter;
import net.emaze.dysfunctional.Applications;
import net.emaze.dysfunctional.iterations.ConstantIterator;
import net.emaze.dysfunctional.multiplexing.InterposingIterator;
import net.emaze.dysfunctional.options.Maybe;

public abstract class CsvServletFacade {

    public static Consumer<String> lineWriter(HttpServletResponse servletResponse) {
        try {
            final SoftenedOutputStreamWriter writer = Arfio.writer(servletResponse.getOutputStream(), "UTF-8");
            return new WriteAndFlushOn(writer);
        } catch (IOException ex) {
            throw new SoftenedIOException(ex);
        }
    }

    public static void stream(Iterator<String> rows, HttpServletResponse response) {
        final Iterator<String> separatedLines = new InterposingIterator<>(rows, new ConstantIterator<>(CsvFlavour.RECORD_DELIMITER));
        final Consumer<String> writer = CsvServletFacade.lineWriter(response);
        Applications.each(separatedLines, writer);
    }

    public static void addHeaders(HttpServletResponse servletResponse, Maybe<String> filename) {
        servletResponse.setContentType("text/csv;charset=UTF-8");
        if (filename.isPresent()) {
            servletResponse.addHeader("Content-disposition", "attachment;filename=" + filename.get());
        }
    }
}
