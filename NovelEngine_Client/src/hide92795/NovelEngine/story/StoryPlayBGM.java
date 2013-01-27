package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.sound.SoundPlayer;

/**
 * BGMの再生を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryPlayBGM extends Story {
	/**
	 * 再生を行う音楽のIDです。
	 */
	private final int id;
	/**
	 * 再生するBGMを表す {@link hide92795.novelengine.sound.SoundPlayer SoundPlayer} オブジェクトです。
	 */
	private SoundPlayer sound;

	/**
	 * BGMの再生を行うストーリーデータを生成します。
	 *
	 * @param id
	 *            再生を行う音楽のID
	 */
	public StoryPlayBGM(int id) {
		this.id = id;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		sound = story.engine().getSoundManager().getSound(id);
		sound.init();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			sound.playAsBGM(story.engine());
			finish();
		}
	}
}
