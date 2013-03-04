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
