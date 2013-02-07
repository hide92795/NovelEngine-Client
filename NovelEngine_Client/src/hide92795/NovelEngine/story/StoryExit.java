package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * ゲームを終了するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryExit extends Story {
	/**
	 * ゲームの終了時に確認を行うかを表します。
	 */
	private boolean confirm;

	/**
	 * ゲームを終了するストーリーデータを作成します。
	 *
	 * @param confirm
	 *            終了を確認するかどうか
	 */
	public StoryExit(boolean confirm) {
		this.confirm = confirm;
	}

	@Override
	public void init(PanelStory story) {
		story.engine().exit();
	}

}
