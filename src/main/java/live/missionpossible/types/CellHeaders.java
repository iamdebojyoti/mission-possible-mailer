package live.missionpossible.types;

import java.util.HashMap;
import java.util.Map;

public enum CellHeaders {

	NAME("Full Name"), 
	EVENT("Event you want to register for"), 
	EMAIL_ID("Email ID"),
	REGISTRATION_ID("Registration ID");

	private String value;

	private static final Map<String, CellHeaders> lookup = new HashMap<>();

	static {
		for (CellHeaders cell : CellHeaders.values()) {
			lookup.put(cell.getValue(), cell);
		}
	}

	private CellHeaders(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CellHeaders get(String value) {
		return lookup.get(value);
	}

}
