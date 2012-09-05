package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.SettingManager;
import hide92795.novelengine.panel.PanelStory;

public class StoryIF extends Story {
	public static final byte IF_EQUAL = 0;
	public static final byte IF_GREATER = 1;
	public static final byte IF_LESS = 2;
	public static final byte IF_GREATER_EQUAL = 3;
	public static final byte IF_LESS_EQUAL = 4;
	private final byte operator;
	private final byte leftVarType;
	private final int leftValue;
	private final byte rightVarType;
	private final int rightValue;
	private final int trueGoto;
	private final int falseGoto;
	private boolean finish;

	public StoryIF(byte operator, byte leftVarType, int leftValue, byte rightVarType, int rightValue, int trueGoto,
			int falseGoto) {
		this.operator = operator;
		this.leftVarType = leftVarType;
		this.leftValue = leftValue;
		this.rightVarType = rightVarType;
		this.rightValue = rightValue;
		this.trueGoto = trueGoto;
		this.falseGoto = falseGoto;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			int left = getValue(story.engine, leftVarType, leftValue);
			int right = getValue(story.engine, rightVarType, rightValue);
			boolean b = evaluation(left, right);
			if (b) {
				story.moveScene(trueGoto);
			} else {
				story.moveScene(falseGoto);
			}
			finish = true;
		}
	}

	private boolean evaluation(int left, int right) {
		switch (operator) {
		case IF_EQUAL:
			return left == right;
		case IF_GREATER:
			return left > right;
		case IF_LESS:
			return left < right;
		case IF_GREATER_EQUAL:
			return left >= right;
		case IF_LESS_EQUAL:
			return left <= right;
		}
		return false;
	}

	private int getValue(NovelEngine engine, byte type, int name) {
		if (type == SettingManager.VARIABLE_RAW) {
			return name;
		} else {
			Properties p = engine.settingManager.getProperties(type);
			return p.getProperty(name);
		}
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
