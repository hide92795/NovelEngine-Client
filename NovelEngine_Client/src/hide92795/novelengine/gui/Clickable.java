package hide92795.novelengine.gui;

import hide92795.novelengine.gui.event.MouseEvent;

/**
 * マウスによる操作を可能なことを示すインターフェースです。
 *
 * @author hide92795
 */
public interface Clickable {
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
