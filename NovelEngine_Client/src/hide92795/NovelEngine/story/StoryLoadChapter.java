package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * チャプターのロードを開始するためのストーリーデータです。
 *
 * @author hide92795
 */
public class StoryLoadChapter extends Story {
	/**
	 * ロードを行うチャプターのIDを表します。
	 */
	private final int loadChapterId;

	/**
	 * チャプターのロードを開始するためのストーリーデータを生成します。
	 *
	 * @param chapterId
	 *            ロードを行うチャプターのID
	 */
	public StoryLoadChapter(final int chapterId) {
		this.loadChapterId = chapterId;
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		// TODO
		// story.engine.loadStory(loadChapterId);
	}

	@Override
	public final boolean isFinish() {
		return true;
	}
}
