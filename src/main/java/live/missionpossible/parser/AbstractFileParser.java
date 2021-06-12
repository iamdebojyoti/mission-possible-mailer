package live.missionpossible.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import live.missionpossible.models.User;
import live.missionpossible.types.CellHeaders;

public abstract class AbstractFileParser implements ExcelParser<User> {

	@Override
	public List<User> parse(File file, String sheetName) throws InvalidFormatException, IOException {
		List<User> users = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheet(sheetName);

			boolean isStarted = true;
			EnumMap<CellHeaders, Integer> cellHeadsMap = new EnumMap<>(CellHeaders.class);

			for (Row row : sheet) {
				if (isStarted) {
					row.cellIterator().forEachRemaining(cell -> {
						if (cell.getCellTypeEnum() == CellType.STRING) {
							CellHeaders headers = CellHeaders.get(cell.getStringCellValue());
							if(headers!=null) {
								cellHeadsMap.put(headers, cell.getColumnIndex());
							}
						}
					});
					isStarted = false;
				} else {
					users.add(prepareUser(row, cellHeadsMap));
				}
			}
		}

		doPostProcess(users);
		
		return users;
	}

	private User prepareUser(Row row, EnumMap<CellHeaders, Integer> cellHeadsMap) {
		User user = new User();
		for (Map.Entry<CellHeaders, Integer> entry : cellHeadsMap.entrySet()) {
			String value = row.getCell(entry.getValue()).getStringCellValue();

			switch (entry.getKey()) {
			case NAME:
				user.setName(value);
				break;
			case EVENT:
				user.setEventName(value);
				break;
			case EMAIL_ID:
				user.setEmailId(value);
				break;
			case REGISTRATION_ID:
				user.setRegistrationId(value);
				break;
			}
		}
		
		if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getEmailId())
				|| StringUtils.isEmpty(user.getRegistrationId())) {
			System.out.println("[ERROR] Either name, email or registration id is empty");
			throw new IllegalArgumentException("Either name, email or registration id is empty");
		}

		return user;
	}
	
	protected abstract void doPostProcess(List<User> users);

}
