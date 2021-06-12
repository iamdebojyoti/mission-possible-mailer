package live.missionpossible.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface ExcelParser<T> {

	List<T> parse(File file, String sheetName) throws InvalidFormatException, IOException ;
}
