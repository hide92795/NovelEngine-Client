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
package hide92795.novelengine.manager;

import hide92795.novelengine.loader.LoaderGui;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.manager.gui.ButtonManager;

import java.util.HashMap;

/**
 * オブジェクトの描画データを保存するマネージャーです。
 *
 * @author hide92795
 */
public class GuiManager {
	/**
	 * クリック可能な領域を表すboolean配列のマップです。
	 */
	private HashMap<Integer, boolean[]> clickable;
	/**
	 * ボタンデータを格納するマネージャーです。
	 */
	private final ButtonManager buttonManager;

	/**
	 * {@link hide92795.novelengine.manager.GuiManager GuiManager} のオブジェクトを生成します。
	 */
	public GuiManager() {
		clickable = new HashMap<Integer, boolean[]>();
		buttonManager = new ButtonManager();
	}

	/**
	 * 指定されたIDのクリック可能な領域を表す配列を返します。
	 *
	 * @param clickableId
	 *            クリック可能な領域のID
	 * @return クリック可能な領域を表す配列
	 */
	public boolean[] getClickable(int clickableId) {
		return clickable.get(clickableId);
	}

	/**
	 * ボタンを管理するマネージャーを取得します。
	 *
	 * @return buttonManager ボタンを管理するマネージャー
	 */
	public ButtonManager getButtonManager() {
		return buttonManager;
	}

	/**
	 * GUIに関連するデータを読み込みます。
	 *
	 * @param loader
	 *            リソースをロードするためのローダー
	 */
	public void loadResource(LoaderResource loader) {
		LoaderGui.load(clickable);
		buttonManager.load(loader);
	}
}
