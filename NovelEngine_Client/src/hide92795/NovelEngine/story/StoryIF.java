package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * ２つの値を比較するストーリーデータです。<br>
 * 結果によってそれぞれ指定されたシーンIDへ移動します。
 *
 * @author hide92795
 */
public class StoryIF extends Story {
	/**
	 * イコールを表します。
	 */
	public static final int IF_EQUAL = 0;
	/**
	 * 大なりを表します。
	 */
	public static final int IF_GREATER = 1;
	/**
	 * 小なりを表します。
	 */
	public static final int IF_LESS = 2;
	/**
	 * 大なりイコールを表します。
	 */
	public static final int IF_GREATER_EQUAL = 3;
	/**
	 * 小なりイコールを表します。
	 */
	public static final int IF_LESS_EQUAL = 4;
	/**
	 * 比較を行う演算子です。
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
	 * 演算結果が真の場合に移動するシーンIDです。
	 */
	private final int trueGoto;
	/**
	 * 演算結果が偽の場合に移動するシーンIDです。
	 */
	private final int falseGoto;

	/**
	 * ２つの値を比較するストーリーデータを生成します。
	 *
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
	 * @param trueGoto
	 *            演算結果が真の場合に移動するシーンID
	 * @param falseGoto
	 *            演算結果が偽の場合に移動するシーンID
	 */
	public StoryIF(final byte operator, final byte leftVarType, final String leftVarName, final byte rightVarType,
			final String rightVarName, final int trueGoto, final int falseGoto) {
		this.operator = operator;
		this.leftVarType = leftVarType;
		this.leftVarName = leftVarName;
		this.rightVarType = rightVarType;
		this.rightVarName = rightVarName;
		this.trueGoto = trueGoto;
		this.falseGoto = falseGoto;
	}

	@Override
	public final void init(final PanelStory story) {
		resetFinish();
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!isFinish()) {
			int left = story.engine().getSettingManager().getValue(leftVarType, leftVarName);
			int right = story.engine().getSettingManager().getValue(rightVarType, rightVarName);
			boolean b = evaluation(left, right);
			if (b) {
				story.moveScene(trueGoto);
			} else {
				story.moveScene(falseGoto);
			}
			finish();
		}
	}

	/**
	 * 比較を行います。
	 *
	 * @param left
	 *            演算子の左側の変数
	 * @param right
	 *            演算子の右側の変数
	 * @return 比較結果
	 */
	private boolean evaluation(final int left, final int right) {
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
		default:
			break;
		}
		return false;
	}
}
