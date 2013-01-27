package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.Utils;
import hide92795.novelengine.panel.PanelStory;

/**
 * ランダムな値を生成するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryRandom extends Story {
	/**
	 * 結果を代入する変数の種類です。
	 */
	private final byte varType;
	/**
	 * 結果を代入する変数の名前です。
	 */
	private final String varName;
	/**
	 * 乱数を生成する範囲です。
	 */
	private final int num;

	/**
	 * ランダムな値を生成するストーリーデータを生成します。
	 *
	 * @param varType
	 *            結果を代入する変数の種類
	 * @param varName
	 *            結果を代入する変数の名前
	 * @param num
	 *            乱数を生成する範囲
	 */
	public StoryRandom(byte varType, String varName, int num) {
		this.varType = varType;
		this.varName = varName;
		this.num = num;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			int randomValue = Utils.getRandom(num);
			Properties p = story.engine().getSettingManager().getProperties(varType);
			p.setProperty(varName, randomValue);
			finish();
		}
	}
}
