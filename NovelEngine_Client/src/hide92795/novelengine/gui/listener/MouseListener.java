//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
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
