package hide92795.novelengine.story;

/**
 * シーンの位置を表すストーリーデータです。
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
	public StoryScene(final int sceneId) {
		this.sceneId = sceneId;
	}

	/**
	 * このストーリーデータが表すシーンIDです。
	 *
	 * @return シーンID
	 */
	public final int getSceneId() {
		return sceneId;
	}

	@Override
	public final boolean isFinish() {
		return true;
	}
}
