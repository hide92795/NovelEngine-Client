package hide92795.novelengine.story;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.sound.SoundPlayer;

import org.lwjgl.input.Keyboard;

/**
 * SEを再生するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryPlaySE extends Story {
	/**
	 * 再生を行うSEのIDです。
	 */
	private final int seId;
	/**
	 * SEの再生終了を待つかどうかを表します。
	 */
	private final boolean wait;
	/**
	 * 再生するSEを表す {@link hide92795.novelengine.sound.SoundPlayer SoundPlayer} オブジェクトです。
	 */
	private SoundPlayer sound;
	/**
	 * SEの再生を開始したかどうかを表します。
	 */
	private boolean played;

	/**
	 * SEを再生するストーリーデータを生成します。
	 *
	 * @param seId
	 *            再生を行うSEのID
	 * @param wait
	 *            SEの再生終了を待つかどうか
	 */
	public StoryPlaySE(int seId, boolean wait) {
		this.seId = seId;
		this.wait = wait;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		played = false;
		sound = story.engine().getSoundManager().getSound(seId);
		sound.init();
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		if (!isFinish()) {
			if (!played) {
				sound.playAsSE();
				played = true;
			}
			if (wait) {
				if (sound.isStopped()) {
					sound.stop();
					finish();
				}
			} else {
				finish();
			}
		}
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		skip(event.engine());
	}

	/**
	 * スキップ可能な場合にスキップを行います。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine)) {
			sound.stop();
		}
	}
}
