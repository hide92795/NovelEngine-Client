package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * シーンの位置を表すストーリーデータです。<br>
 * このストーリーデータの終了確認が行われることはありません。
 *
 * @author hide92795
 */
public class StoryScene extends Story {
	/**
	 * シーンIDを表します。
	 */
	private final int sceneId;

	/**
	 * シーンの位置を表すストーリーデータを作成します。
	 *
	 * @param sceneId
	 *            シーンID
	 */
	public StoryScene(int sceneId) {
		this.sceneId = sceneId;
	}

	@Override
	public void init(PanelStory story) {
	}

	/**
	 * このストーリーデータが表すシーンIDです。
	 *
	 * @return シーンID
	 */
	public int getSceneId() {
		return sceneId;
	}
}
