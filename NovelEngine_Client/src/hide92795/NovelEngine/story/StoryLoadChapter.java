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
	public StoryLoadChapter(int chapterId) {
		this.loadChapterId = chapterId;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			DataStory dataStory = story.engine().getStoryManager().getStory(loadChapterId);
			if (dataStory == null) {
				story.engine().loadStory(loadChapterId);
			}
			finish();
		}
	}
}
