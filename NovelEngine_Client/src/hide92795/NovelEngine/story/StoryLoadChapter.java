package hide92795.novelengine.story;

import hide92795.novelengine.loader.item.DataStory;
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
	public final void init(final PanelStory story) {
		resetFinish();
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!isFinish()) {
			DataStory dataStory = story.engine().getStoryManager().getStory(loadChapterId);
			if (dataStory == null) {
				story.engine().loadStory(loadChapterId);
			}
			finish();
		}
	}
}
