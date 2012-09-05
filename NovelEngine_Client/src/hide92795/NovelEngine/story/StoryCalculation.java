package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.SettingManager;
import hide92795.novelengine.panel.PanelStory;

public class StoryCalculation extends Story {
	public static final byte OPERATOR_PLUS = 0;
	public static final byte OPERATOR_MINUS = 1;
	public static final byte OPERATOR_TIMES = 2;
	public static final byte OPERATOR_DIVIDED = 3;
	public static final byte OPERATOR_MODULO = 4;
	public static final byte OPERATOR_AND = 5;
	public static final byte OPERATOR_OR = 6;
	public static final byte OPERATOR_XOR = 7;
	public static final byte OPERATOR_SHIFT_LEFT = 8;
	public static final byte OPERATOR_SHIFT_RIGHT = 9;
	public static final byte OPERATOR_SHIFT_RIGHT_UNSIGNED = 10;
	private boolean finish;
	private final byte varType;
	private final int varName;
	private final byte operator;
	private final byte leftVarType;
	private final int leftVarName;
	private final byte rightVarType;
	private final int rightVarName;

	public StoryCalculation(byte varType, int varName, byte operator, byte leftVarType, int leftVarName,
			byte rightVarType, int rightVarName) {
		this.varType = varType;
		this.varName = varName;
		this.operator = operator;
		this.leftVarType = leftVarType;
		this.leftVarName = leftVarName;
		this.rightVarType = rightVarType;
		this.rightVarName = rightVarName;
	}
	
	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			int leftValue = getValue(story.engine, leftVarType, leftVarName);
			int rightValue = getValue(story.engine, rightVarType, rightVarName);
			int value = calculate(operator, leftValue, rightValue);
			Properties p = story.engine.settingManager.getProperties(varType);
			p.setProperty(varName, value);
			finish = true;
		}
	}

	private int calculate(byte operator1, int leftValue, int rightValue) {
		switch (operator1) {
		case OPERATOR_PLUS:
			return leftValue + rightValue;
		case OPERATOR_MINUS:
			return leftValue - rightValue;
		case OPERATOR_TIMES:
			return leftValue * rightValue;
		case OPERATOR_DIVIDED:
			return leftValue / rightValue;
		case OPERATOR_MODULO:
			return leftValue % rightValue;
		case OPERATOR_AND:
			return leftValue & rightValue;
		case OPERATOR_OR:
			return leftValue | rightValue;
		case OPERATOR_XOR:
			return leftValue ^ rightValue;
		case OPERATOR_SHIFT_LEFT:
			return leftValue << rightValue;
		case OPERATOR_SHIFT_RIGHT:
			return leftValue >> rightValue;
		case OPERATOR_SHIFT_RIGHT_UNSIGNED:
			return leftValue >>> rightValue;
		}
		return 0;
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
