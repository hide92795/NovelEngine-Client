package hide92795.novelengine.gui.listener;

import hide92795.novelengine.gui.event.MouseEvent;

/**
 * マウスに関するイベントを検知するための機能を提供します。
 *
 * @author hide92795
 */
public interface MouseListener {
	/**
	 * マウスの左ボタンが押下され始めた時に呼ばれます。
	 *
	 * @param event
	 *            発生したマウスイベント
	 */
	void onLeftClickStart(MouseEvent event);

	/**
	 * マウスの右ボタンが押下され始めた時に呼ばれます。
	 *
	 * @param event
	 *            発生したマウスイベント
	 */
	void onRightClickStart(MouseEvent event);

	/**
	 * マウスの左ボタンが押下された状態から戻った時に呼ばれます。
	 *
	 * @param event
	 *            発生したマウスイベント
	 */
	void onLeftClickFinish(MouseEvent event);

	/**
	 * マウスの右ボタンが押下された状態から戻った時に呼ばれます。
	 *
	 * @param event
	 *            発生したマウスイベント
	 */
	void onRightClickFinish(MouseEvent event);
}
