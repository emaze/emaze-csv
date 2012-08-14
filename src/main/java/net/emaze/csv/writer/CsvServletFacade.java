package net.emaze.csv.writer;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import net.emaze.arfio.Arfio;
import net.emaze.arfio.SoftenedIOException;
import net.emaze.arfio.SoftenedOutputStreamWriter;
import net.emaze.dysfunctional.dispatching.actions.Action;
import net.emaze.dysfunctional.options.Maybe;

public abstract class CsvServletFacade {

    public static Action<String> lineWriter(HttpServletResponse servletResponse) {
        try {
            final SoftenedOutputStreamWriter writer = Arfio.writer(servletResponse.getOutputStream(), "UTF-8");
            return new WriteAndFlushOn(writer);
        } catch (IOException ex) {
            throw new SoftenedIOException(ex);
        }
    }

    public static void addHeaders(HttpServletResponse servletResponse, Maybe<String> filename) {
        servletResponse.setContentType("text/csv;charset=UTF-8");
        if (filename.hasValue()) {
            servletResponse.addHeader("Content-disposition", "attachment;filename=" + filename);
        }
    }

}
