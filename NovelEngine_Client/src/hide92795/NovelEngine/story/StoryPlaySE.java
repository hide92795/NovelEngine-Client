package hide92795.novelengine.story;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;

import soundly.XSound;

/**
 * SEを再生するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryPlaySE extends Story {
	/**
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;
	/**
	 * 再生を行うSEのIDです。
	 */
	private final int seId;
	/**
	 * SEの再生終了を待つかどうかを表します。
	 */
	private final boolean wait;
	/**
	 * 再生するSEを表す {@link soundly.XSound XSound} オブジェクトです。
	 */
	private XSound sound;
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
	public StoryPlaySE(final int seId, final boolean wait) {
		this.seId = seId;
		this.wait = wait;
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

	@Override
	public final void init(final PanelStory story) {
		finish = false;
		played = false;
		sound = story.engine().getSoundManager().getSound(seId);
		sound.seek(0.0f);
		sound.rewind();
		sound.setMusic(false);
		sound.setLooping(false);
		sound.setVolume(1.0f);
	}

	@Override
	public final void update(final PanelStory panelStory, final int delta) {
		if (!finish) {
			if (!played) {
				sound.queue();
				played = true;
			}
			if (wait) {
				if (sound.isStopped()) {
					sound.stop();
					finish = true;
				}
			} else {
				finish = true;
			}
		}
	}

	@Override
	public final void onKeyPressed(final NovelEngine engine, final int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public final void onLeftClickStart(final MouseEvent event) {
		skip(event.engine());
	}

	/**
	 * スキップ可能な場合にスキップを行います。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(final NovelEngine engine) {
		if (canSkip(engine)) {
			sound.stop();
		}
	}
}
