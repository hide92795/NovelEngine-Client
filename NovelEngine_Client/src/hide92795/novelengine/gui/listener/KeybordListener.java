package hide92795.novelengine.gui.listener;

import hide92795.novelengine.client.NovelEngine;

/**
 * キーボードに関するイベントを検知するための機能を提供します。
 *
 * @author hide92795
 */
public interface KeybordListener {
	/**
	 * キーボードのいずれかのキーが押下された時に呼ばれます。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 *
	 * @param key
	 *            押下されたキー
	 *
	 * @see org.lwjgl.input.Keyboard
	 */
	void onKeyPressed(NovelEngine engine, int key);

	/**
	 * キーボードの押下されていたキーが離された時に呼ばれます。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 *
	 * @param key
	 *            押下された状態から戻ったキー
	 *
	 * @see org.lwjgl.input.Keyboard
	 */
	void onKeyReleased(NovelEngine engine, int key);
}
