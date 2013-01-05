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
	public StoryMoveChapter(final int chapterId) {
		this.chapterId = chapterId;
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		story.engine().prestartStory(chapterId);
	}

	@Override
	public final boolean isFinish() {
		return false;
	}
}
