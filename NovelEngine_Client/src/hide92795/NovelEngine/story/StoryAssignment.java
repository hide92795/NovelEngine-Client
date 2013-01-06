package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.panel.PanelStory;

/**
 * 変数への代入を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryAssignment extends Story {
	/**
	 * 値を代入する変数の種類です。
	 */
	private final byte varType;
	/**
	 * 値を代入する変数の名前です。
	 */
	private final String varName;
	/**
	 * 代入する値です。
	 */
	private final int value;

	/**
	 * 変数に値を代入するストーリーデータを生成します。
	 *
	 * @param varType
	 *            値を代入する変数の種類
	 * @param varName
	 *            値を代入する変数の名前
	 * @param value
	 *            代入する値
	 */
	public StoryAssignment(final byte varType, final String varName, final int value) {
		this.varType = varType;
		this.varName = varName;
		this.value = value;
	}

	@Override
	public final void init(final PanelStory story) {
		resetFinish();
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!isFinish()) {
			Properties p = story.engine().getSettingManager().getProperties(varType);
			p.setProperty(varName, value);
			finish();
		}
	}
}
