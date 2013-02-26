package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * チャプターを移動するためのストーリーデータです。
 *
 * @author hide92795
 */
public class StoryMoveChapter extends Story {
	/**
	 * 移動先のチャプターIDを表します。
	 */
	private final int chapterId;

	/**
	 * チャプターを移動するためのストーリーデータを生成します。
	 *
	 * @param chapterId
	 *            移動先のチャプターID
	 */
	public StoryMoveChapter(int chapterId) {
		this.chapterId = chapterId;
	}

	@Override
	public void init(PanelStory story) {
	}

	@Override
	public void update(PanelStory story, int delta) {
		story.engine().prestartStory(chapterId);
	}
}
