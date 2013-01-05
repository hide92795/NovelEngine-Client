package hide92795.novelengine.gui.event;

import hide92795.novelengine.client.NovelEngine;

/**
 * マウスに関するイベントです。
 *
 * @author hide92795
 */
public class MouseEvent {
	/**
	 * 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトを表します。
	 */
	private final NovelEngine engine;
	/**
	 * イベントが発生した地点のX座標
	 */
	private final int x;
	/**
	 * イベントが発生した地点のY座標
	 */
	private final int y;
	/**
	 * このイベントが既に処理されたかを表します。
	 */
	private boolean consumed;

	/**
	 * 指定された座標でのマウスイベントを作成します 。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 * @param x
	 *            イベントのX座標
	 * @param y
	 *            イベントのY座標
	 */
	public MouseEvent(final NovelEngine engine, final int x, final int y) {
		this.engine = engine;
		this.x = x;
		this.y = y;
	}

	/**
	 * 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトを返します。
	 *
	 * @return 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public final NovelEngine engine() {
		return engine;
	}

	/**
	 * このイベントの発生地点のX座標を取得します。
	 *
	 * @return イベントが発生した地点のY座標
	 */
	public final int getX() {
		return x;
	}

	/**
	 * このイベントの発生地点のY座標を取得します。
	 *
	 * @return イベントが発生した地点のX座標
	 */
	public final int getY() {
		return y;
	}

	/**
	 * このイベントが既に処理されたかどうかを取得します。
	 *
	 * @return このイベントが既に処理されているかどうか
	 */
	public final boolean isConsumed() {
		return consumed;
	}

	/**
	 * このイベントが処理されたものとしてマークします。
	 */
	public final void consume() {
		this.consumed = true;
	}

}
