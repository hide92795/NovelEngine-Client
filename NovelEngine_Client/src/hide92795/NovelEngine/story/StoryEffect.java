package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;

import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

/**
 * エフェクトを実行するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryEffect extends Story {
	/**
	 * エフェクトをかけるレイヤーのIDです。
	 */
	private final byte target;
	/**
	 * エフェクトを実行するまでの待機時間です。
	 */
	private final int delay;
	/**
	 * このストーリーデータで実行するエフェクトです。
	 */
	private final BackGroundEffect backGroundEffect;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;
	/**
	 * 指定された時間以上待機したかどうかを表します。
	 */
	private boolean timeElapsed;

	/**
	 * @param target
	 *            エフェクトを実行するレイヤーのID
	 * @param delay
	 *            エフェクトを実行するまでの待機時間
	 * @param backGroundEffect
	 *            実行するエフェクト
	 */
	public StoryEffect(final byte target, final int delay, final BackGroundEffect backGroundEffect) {
		this.target = target;
		this.delay = delay;
		this.backGroundEffect = backGroundEffect;
	}

	@Override
	public final void init(final PanelStory story) {
		backGroundEffect.init(story, target);
		resetFinish();
		timeElapsed = false;
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		if (!isFinish()) {
			if (timeElapsed) {
				backGroundEffect.update(this, delta);
			} else {
				if (elapsedTime > delay) {
					timeElapsed = true;
				} else {
					elapsedTime += delta;
				}
			}
		}
	}

	@Override
	public final void render(final NovelEngine engine) {
		if (timeElapsed) {
			backGroundEffect.render(engine);
		}
	}

	/**
	 * エフェクトが終了した時に呼ばれます。
	 */
	public final void effectFinish() {
		finish();
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
			backGroundEffect.skip();
		}
	}

}
