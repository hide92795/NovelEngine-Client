package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * BGMの再生を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryStopBGM extends Story {
	/**
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!finish) {
			// story.stopBGM();
		}
	}

	@Override
	public final void init(final PanelStory story) {
		finish = false;
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

}
