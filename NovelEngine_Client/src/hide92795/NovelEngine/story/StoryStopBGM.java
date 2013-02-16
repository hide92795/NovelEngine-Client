package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * BGMの再生を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryStopBGM extends Story {
	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			story.engine().getSoundManager().stop("BGM");
			finish();
		}
	}
}
