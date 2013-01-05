package hide92795.novelengine.story;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.panel.PanelStory;

/**
 * バックグラウンドの背景色を変更ストーリーデータです。
 *
 * @author hide92795
 */
public class StoryChangeBgColor extends Story {
	/**
	 * 背景色を変更するバックグラウンドのIDです。
	 */
	private final byte target;
	/**
	 * 変更する背景色の赤成分です
	 */
	private final float r;
	/**
	 * 変更する背景色の緑成分です
	 */
	private final float g;
	/**
	 * 変更する背景色の青成分です
	 */
	private final float b;
	/**
	 * 変更する背景色のアルファ成分です
	 */
	private final float a;
	/**
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;

	/**
	 * バックグラウンドの背景色を変更ストーリーデータを生成します。
	 *
	 * @param target
	 *            背景色を変更するバックグラウンドのID
	 * @param r
	 *            変更する背景色の赤成分
	 * @param g
	 *            変更する背景色の緑成分
	 * @param b
	 *            変更する背景色の青成分
	 * @param a
	 *            変更する背景色のアルファ成分
	 */
	public StoryChangeBgColor(final byte target, final int r, final int g, final int b, final int a) {
		this.target = target;
		this.r = (float) r / 255;
		this.g = (float) g / 255;
		this.b = (float) b / 255;
		if (target == (byte) 10) {
			this.a = 1.0f;
		} else {
			this.a = a / 255;
		}
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

	@Override
	public final void init(final PanelStory story) {
		finish = false;
	}

	@Override
	public final void update(final PanelStory story, final int delta) {
		BackGround background = story.engine().getBackGroundManager().getBackGround(target);
		background.setRed(r);
		background.setGreen(g);
		background.setBlue(b);
		background.setAlpha(a);
		finish = true;
	}

}
