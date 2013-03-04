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
package hide92795.novelengine.gui.data;

/**
 * ボタンの描画に必要なデータを保管するクラスです。
 *
 * @author hide92795
 */
public class DataButton {
	/**
	 * 通常時に表示されるイメージのIDです。
	 */
	private int imageNormalId;
	/**
	 * オンマウス時に表示されるイメージのIDです。
	 */
	private int imageOnMouseId;
	/**
	 * ボタンが無効の時に表示されるイメージのIDです。
	 */
	private int imageDisabledId;
	/**
	 * ボタンが既にクリックされた場合に表示されるイメージのIDです。
	 */
	private int imageClickedId;
	/**
	 * クリック可能な領域を表すデータのIDです。
	 */
	private int cliclableId;
	/**
	 * テキストを配置する点のX座標です。
	 */
	private int textXPos;
	/**
	 * テキストを配置する点のY座標です。
	 */
	private int textYPos;
	/**
	 * ボタンの横幅です。
	 */
	private int width;
	/**
	 * ボタンの縦幅です。
	 */
	private int height;

	/**
	 * @return imageNormalId
	 */
	public final int getImageNormalId() {
		return imageNormalId;
	}

	/**
	 * @param imageNormalId
	 *            セットする imageNormalId
	 */
	public final void setImageNormalId(int imageNormalId) {
		this.imageNormalId = imageNormalId;
	}

	/**
	 * @return imageOnMouseId
	 */
	public final int getImageOnMouseId() {
		return imageOnMouseId;
	}

	/**
	 * @param imageOnMouseId
	 *            セットする imageOnMouseId
	 */
	public final void setImageOnMouseId(int imageOnMouseId) {
		this.imageOnMouseId = imageOnMouseId;
	}

	/**
	 * @return imageDisabledId
	 */
	public final int getImageDisabledId() {
		return imageDisabledId;
	}

	/**
	 * @param imageDisabledId
	 *            セットする imageDisabledId
	 */
	public final void setImageDisabledId(int imageDisabledId) {
		this.imageDisabledId = imageDisabledId;
	}

	/**
	 * @return imageClickedId
	 */
	public final int getImageClickedId() {
		return imageClickedId;
	}

	/**
	 * @param imageClickedId
	 *            セットする imageClickedId
	 */
	public final void setImageClickedId(int imageClickedId) {
		this.imageClickedId = imageClickedId;
	}

	/**
	 * @return cliclableId
	 */
	public final int getCliclableId() {
		return cliclableId;
	}

	/**
	 * @param cliclableId
	 *            セットする cliclableId
	 */
	public final void setCliclableId(int cliclableId) {
		this.cliclableId = cliclableId;
	}

	/**
	 * @return textXPos
	 */
	public final int getTextXPos() {
		return textXPos;
	}

	/**
	 * @param textXPos
	 *            セットする textXPos
	 */
	public final void setTextXPos(int textXPos) {
		this.textXPos = textXPos;
	}

	/**
	 * @return textYPos
	 */
	public final int getTextYPos() {
		return textYPos;
	}

	/**
	 * @param textYPos
	 *            セットする textYPos
	 */
	public final void setTextYPos(int textYPos) {
		this.textYPos = textYPos;
	}

	/**
	 * @return width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            セットする width
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            セットする height
	 */
	public final void setHeight(int height) {
		this.height = height;
	}
}
