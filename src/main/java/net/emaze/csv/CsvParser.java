package net.emaze.csv;

import java.util.List;

public interface CsvParser {

    List<String> record() throws ParseException;

    String field() throws ParseException;
}
