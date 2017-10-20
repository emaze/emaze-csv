package net.emaze.csv.writer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;
import net.emaze.arfio.Arfio;
import net.emaze.arfio.SoftenedIOException;
import net.emaze.arfio.SoftenedOutputStreamWriter;
import org.jooq.lambda.Seq;

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
        final Consumer<String> writer = CsvServletFacade.lineWriter(response);
        Seq.seq(rows).intersperse(CsvFlavour.RECORD_DELIMITER).forEach(writer);
    }

    public static void addHeaders(HttpServletResponse servletResponse, Optional<String> filename) {
        servletResponse.setContentType("text/csv;charset=UTF-8");
        filename.ifPresent(it -> servletResponse.addHeader("Content-disposition", "attachment;filename=" + it));
    }
}
