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
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;

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
	public StoryRandom(final byte varType, final String varName, final int num) {
		this.varType = varType;
		this.varName = varName;
		this.num = num;
	}

	@Override
	public final void init(final PanelStory story) {
		finish = false;
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!finish) {
			int randomValue = Utils.getRandom(num);
			Properties p = story.engine().getSettingManager().getProperties(varType);
			p.setProperty(varName, randomValue);
			finish = true;
		}
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

}
