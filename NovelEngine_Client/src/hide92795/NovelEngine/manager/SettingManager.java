package hide92795.novelengine.manager;

import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;

import java.awt.Color;
import java.io.File;

public class SettingManager {
	public static final byte VARIABLE_RAW = -1;
	public static final byte VARIABLE_GLOBAL = 0;
	public static final byte VARIABLE_PRIVATE = 1;
	public static final byte VARIABLE_TEMP = 2;
	public static final byte VARIABLE_SYSTEM = 3;
	public static final byte VARIABLE_SETTING = 4;
	private Properties global;
	private Properties setting;

	public SettingManager() {
		global = new Properties();
		setting = new Properties();
		loadGrobalSetting();
		loadSetting();
	}

	public Properties getProperties(byte type) {
		switch (type) {
		case VARIABLE_SETTING:
			return setting;
		default:
			break;
		}
		return global;
	}

	private void loadSetting() {
		//setting.setProperty(Setting.SETTING_PRESTART_BACKGROUND_COLOR, -1);
		setting.setProperty(Setting.SETTING_PRESTART_BACKGROUND_COLOR, Color.blue.getRGB());
		setting.setProperty(Setting.SETTING_SKIPPABLE, 1);
	}

	private void loadGrobalSetting() {
		try {
			File file = NovelEngine.getCurrentDir();
			file = new File(file, "global.nef");
			global.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Setting {
		public static final int SETTING_PRESTART_BACKGROUND_COLOR = "prestart_bgcolor".hashCode();
		public static final int SETTING_SKIPPABLE = "skippable".hashCode();

		public static final int SYSTEM_YEAR = "year".hashCode();
		public static final int SYSTEM_MONTH = "month".hashCode();
		public static final int SYSTEM_DAY = "day".hashCode();
		public static final int SYSTEM_HOUR = "hour".hashCode();
		public static final int SYSTEM_MINUTE = "minute".hashCode();
		public static final int SYSTEM_SECOND = "second".hashCode();
	}
}
