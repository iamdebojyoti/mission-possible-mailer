package live.missionpossible.types;

import java.util.HashMap;
import java.util.Map;

public enum AppMode {

	REGISTRATION, DONATION;
	
	private static final Map<String, AppMode> lookup = new HashMap<>();

	static {
		for (AppMode appMode : AppMode.values()) {
			lookup.put(appMode.name(), appMode);
		}
	}

	public static AppMode get(String value) {
		return lookup.get(value);
	}
}
