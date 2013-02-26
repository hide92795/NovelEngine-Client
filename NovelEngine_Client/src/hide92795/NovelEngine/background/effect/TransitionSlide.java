package hide92795.novelengine.background.effect;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryEffect;

/**
 * 移動による画面効果を提供します。
 *
 * @author hide92795
 */
public class TransitionSlide extends BackGroundEffect {
	/**
	 * このエフェクトのIDを表します。
	 */
	public static final int ID = "slide".hashCode();
	/**
	 * このエフェクトのインスタンス化に必要な引数のクラス一覧です。
	 */
	public static final Class<?>[] CONSTRUCTOR = { int.class, int.class, int.class };
	/**
	 * このエフェクトが完了しているかどうかを表します。
	 */
	private boolean finish;
	/**
	 * 移動後のX座標を表します。
	 */
	private int movedX;
	/**
	 * 移動後のY座標を表します。
	 */
	private int movedY;
	/**
	 * エフェクトに掛ける時間を表します。単位はミリ秒です。
	 */
	private int totalTime;
	/**
	 * X軸方向の変移を表します。
	 */
	private int fluctuationX;
	/**
	 * Y軸方向の変移を表します。
	 */
	private int fluctuationY;
	/**
	 * 移動前のX座標を表します。
	 */
	private int beforeX;
	/**
	 * 移動前のY座標を表します。
	 */
	private int beforeY;
	/**
	 * エフェクト開始から経過した時間を表します。単位はミリ秒です。
	 */
	private int elapsedTime;
	/**
	 * フェード処理をおこうなう対象の{@link hide92795.novelengine.background.BackGround
	 * BackGround} オブジェクトです。
	 */
	private BackGround background;

	/**
	 * 変化後の座標と変化に掛ける時間を指定してエフェクトを生成します。
	 *
	 * @param movedX
	 *            変化後の{@link hide92795.novelengine.background.BackGround
	 *            BackGround} オブジェクトのX座標
	 * @param movedY
	 *            変化後の{@link hide92795.novelengine.background.BackGround
	 *            BackGround} オブジェクトのY座標
	 * @param totalTime
	 *            変化に掛ける時間
	 */
	public TransitionSlide(final int movedX, final int movedY, final int totalTime) {
		this.movedX = movedX;
		this.movedY = movedY;
		this.totalTime = totalTime;
	}

	@Override
	public final void init(final PanelStory story, final byte target) {
		this.finish = false;
		this.beforeX = background.getX();
		this.beforeY = background.getY();
		this.fluctuationX = movedX - beforeX;
		this.fluctuationY = movedY - beforeY;
	}

	@Override
	public final void update(final StoryEffect story, final int delta) {
		if (finish) {
			story.effectFinish();
		} else {
			float per = (float) elapsedTime / totalTime;
			if (elapsedTime >= totalTime) {
				finish = true;
				per = 1.0f;
			}
			int nextPosX = Math.round(beforeX + (fluctuationX * per));
			int nextPosY = Math.round(beforeY + (fluctuationY * per));
			background.setX(nextPosX);
			background.setY(nextPosY);
			elapsedTime += delta;
		}
	}

	@Override
	public final void render(final NovelEngine engine) {
	}

	@Override
	public final void skip() {
		background.setX(movedX);
		background.setY(movedY);
		finish = true;
	}
}
