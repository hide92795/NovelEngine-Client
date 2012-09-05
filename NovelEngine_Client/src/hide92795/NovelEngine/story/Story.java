package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.SettingManager;
import hide92795.novelengine.manager.SettingManager.Setting;
import hide92795.novelengine.panel.PanelStory;

public abstract class Story {

	public static final int IF_EQUAL = 0;
	public static final int IF_GREATER = 1;
	public static final int IF_LESS = 2;
	public static final int IF_GREATER_EQUAL = 3;
	public static final int IF_LESS_EQUAL = 4;

	public static final byte COMMAND_BLOCK_START = 0;
	public static final byte COMMAND_BLOCK_END = 1;
	public static final byte COMMAND_SET_CHAPTERID = 2;
	public static final byte COMMAND_SET_SCENEID = 3;
	public static final byte COMMAND_LOAD_CHAPTER = 4;
	public static final byte COMMAND_MOVE_CHAPTER = 5;
	public static final byte COMMAND_CHANGE_BG = 6;
	public static final byte COMMAND_CHANGE_CHARACTER = 7;
	public static final byte COMMAND_MOVE_CHARACTER = 8;
	public static final byte COMMAND_ACTION_CHARACTER = 9;
	public static final byte COMMAND_SHOW_CG = 10;
	public static final byte COMMAND_SHOW_WORDS = 11;
	public static final byte COMMAND_MAKE_BUTTON = 12;
	public static final byte COMMAND_IF = 13;
	public static final byte COMMAND_PLAY_BGM = 14;
	public static final byte COMMAND_STOP_BGM = 15;
	public static final byte COMMAND_PLAY_SE = 16;
	public static final byte COMMAND_SHOW_BOX = 17;
	public static final byte COMMAND_HIDE_BOX = 18;
	public static final byte COMMAND_SET_VARIABLE = 19;
	public static final byte COMMAND_SET_BACKGROUND_COLOR = 20;
	public static final byte COMMAND_EFFECT = 21;
	public static final byte COMMAND_RANDOM = 22;
	public static final byte COMMAND_CALCULATION = 23;

	public abstract boolean isFinish();

	public void init(PanelStory story) {

	}

	public void update(PanelStory story, int delta) {

	}

	public void render(NovelEngine engine) {

	}

	public void leftClick(PanelStory story, int x, int y) {

	}

	public void keyPressed(PanelStory story, int eventKey) {
	}

	public final boolean canSkip(PanelStory story) {
		Properties properties = story.engine.settingManager.getProperties(SettingManager.VARIABLE_SETTING);
		int i = properties.getProperty(Setting.SETTING_SKIPPABLE);
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}
}
