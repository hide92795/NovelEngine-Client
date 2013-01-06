package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.sound.SoundPlayer;

/**
 * BGMの再生を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryStopBGM extends Story {
	@Override
	public final void init(final PanelStory story) {
		resetFinish();
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!isFinish()) {
			SoundPlayer.stopPrimaryBGM();
			finish();
		}
	}
}
