package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.panel.PanelStory;

/**
 * 計算を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryCalculation extends Story {
	/**
	 * 加算を表す定数です。
	 */
	public static final byte OPERATOR_PLUS = 0;
	/**
	 * 減算を表す定数です。
	 */
	public static final byte OPERATOR_MINUS = 1;
	/**
	 * 乗算を表す定数です。
	 */
	public static final byte OPERATOR_TIMES = 2;
	/**
	 * 除算を表す定数です。
	 */
	public static final byte OPERATOR_DIVIDED = 3;
	/**
	 * モジュラ計算を表す定数です。
	 */
	public static final byte OPERATOR_MODULO = 4;
	/**
	 * 論理積演算を表す定数です。
	 */
	public static final byte OPERATOR_AND = 5;
	/**
	 * 論理和演算を表す定数です。
	 */
	public static final byte OPERATOR_OR = 6;
	/**
	 * 排他的論理和演算を表す定数です。
	 */
	public static final byte OPERATOR_XOR = 7;
	/**
	 * 左算術シフト演算を表す定数です。
	 */
	public static final byte OPERATOR_SHIFT_LEFT = 8;
	/**
	 * 右算術シフト演算を表す定数です。
	 */
	public static final byte OPERATOR_SHIFT_RIGHT = 9;
	/**
	 * 左論理シフト演算を表す定数です。
	 */
	public static final byte OPERATOR_SHIFT_RIGHT_UNSIGNED = 10;
	/**
	 * 演算結果を代入する変数の種類です。
	 */
	private final byte varType;
	/**
	 * 演算結果を代入する変数の名前です。
	 */
	private final String varName;
	/**
	 * このストーリーデータが行う演算子を表します。
	 */
	private final byte operator;
	/**
	 * 演算子の左側の変数の種類です。
	 */
	private final byte leftVarType;
	/**
	 * 演算子の左側の変数の名前です。
	 */
	private final String leftVarName;
	/**
	 * 演算子の右側の変数の種類です。
	 */
	private final byte rightVarType;
	/**
	 * 演算子の右側の変数の名前です。
	 */
	private final String rightVarName;

	/**
	 * 計算を行うストーリーデータを生成します。
	 *
	 * @param varType
	 *            演算結果を代入する変数の種類
	 * @param varName
	 *            演算結果を代入する変数の名前
	 * @param operator
	 *            ストーリーデータが行う演算子
	 * @param leftVarType
	 *            演算子の左側の変数の種類
	 * @param leftVarName
	 *            演算子の左側の変数の名前
	 * @param rightVarType
	 *            演算子の右側の変数の種類
	 * @param rightVarName
	 *            演算子の右側の変数の名前
	 */
	public StoryCalculation(byte varType, String varName, byte operator, byte leftVarType, String leftVarName,
			byte rightVarType, String rightVarName) {
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
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			int leftValue = story.engine().getSettingManager().getValue(leftVarType, leftVarName);
			int rightValue = story.engine().getSettingManager().getValue(rightVarType, rightVarName);
			int value = calculate(operator, leftValue, rightValue);
			Properties p = story.engine().getSettingManager().getProperties(varType);
			p.setProperty(varName, value);
			finish();
		}
	}

	/**
	 * 計算を行います。
	 *
	 * @param operator
	 *            計算を行う演算子
	 * @param leftValue
	 *            演算子の左側の値
	 * @param rightValue
	 *            演算子の右側の値
	 * @return 計算結果
	 */
	private int calculate(byte operator, int leftValue, int rightValue) {
		switch (operator) {
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
		default:
			break;
		}
		return 0;
	}
}
