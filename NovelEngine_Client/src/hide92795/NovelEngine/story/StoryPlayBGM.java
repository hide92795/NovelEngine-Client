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
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;
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
	public StoryPlayBGM(final int id) {
		this.id = id;
	}

	@Override
	public final void init(final PanelStory story) {
		finish = false;
		sound = story.engine().getSoundManager().getSound(id);
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!finish) {
			sound.playAsBGM(story.engine());
			finish = true;
		}
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

}
