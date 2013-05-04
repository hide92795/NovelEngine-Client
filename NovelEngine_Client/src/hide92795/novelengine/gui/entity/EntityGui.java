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
package hide92795.novelengine.gui.entity;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.Clickable;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.gui.listener.KeybordListener;
import hide92795.novelengine.panel.Panel;

/**
 * ユーザーがマウス及びキーボードなどで操作可能なオブジェクトを提供します。
 * 
 * @author hide92795
 */
public abstract class EntityGui implements KeybordListener, Clickable {
	/**
	 * GUIオブジェクトを配置する点のX座標です。
	 */
	private int x;
	/**
	 * GUIオブジェクトを配置する点のY座標です。
	 */
	private int y;

	/**
	 * このGuiオブジェクトがクリック可能かを表します。
	 */
	private boolean enabled;

	/**
	 * このGuiオブジェクトを描画するかしないかを表します。
	 */
	private boolean visible;

	/**
	 * このGUIオブジェクトを初期化します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public abstract void init(NovelEngine engine);

	/**
	 * GUIオブジェクトを更新します。
	 * 
	 * @param panel
	 *            現在、画面を描画している{@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public abstract void update(Panel panel, int delta);

	/**
	 * GUIオブジェクトを描画します。
	 * 
	 * @param engine
	 *            　実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public abstract void render(NovelEngine engine);

	/**
	 * クリックされた点がこのGuiオブジェクトのクリック範囲内かを判定します。
	 * 
	 * @param x
	 *            クリックされたウィンドウ内のX座標
	 * @param y
	 *            クリックされたウィンドウ内のY座標
	 * @return クリック位置がGuiの範囲内かつクリック可能な領域ならtrue
	 */
	public abstract boolean isClickableAt(int x, int y);

	@Override
	public void onLeftClickStart(MouseEvent event) {
	}

	@Override
	public void onRightClickStart(MouseEvent event) {
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
	}

	@Override
	public void onRightClickFinish(MouseEvent event) {
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int key) {
	}

	@Override
	public void onKeyReleased(NovelEngine engine, int key) {
	}

	/**
	 * このGuiオブジェクトのX座標を返します。
	 * 
	 * @return GuiオブジェクトのX座標
	 */
	public int getX() {
		return x;
	}

	/**
	 * このGuiオブジェクトのX座標を設定します。
	 * 
	 * @param x
	 *            GuiオブジェクトのX 座標
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * このGuiオブジェクトのY座標を返します。
	 * 
	 * @return GuiオブジェクトのY座標
	 */
	public int getY() {
		return y;
	}

	/**
	 * このGuiオブジェクトのY座標を設定します。
	 * 
	 * @param y
	 *            GuiオブジェクトのY座標
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * このGuiオブジェクトが有効かを返します。
	 * 
	 * @return このGuiオブジェクトが有効ならtrue
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * このGuiオブジェクトが現在有効か無効かを設定します。
	 * 
	 * @param enabled
	 *            このGuiオブジェクトを有効にするならtrue、無効にするならfalse
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * このGuiオブジェクトが描画されるかを設定します。
	 * 
	 * @return このGuiオブジェクトが描画されるならtrue
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * このGuiオブジェクトが描画されるかどうかを設定します。
	 * 
	 * @param visible
	 *            このGuiオブジェクトを描画するならtrue、しないならfalse
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
