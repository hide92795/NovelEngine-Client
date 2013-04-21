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
package hide92795.novelengine.box;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.SystemSettings;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.panel.PanelStory;

import org.newdawn.slick.opengl.Texture;

/**
 * メッセージボックスの描画に関するデータを保存します。<br>
 * このクラスは起動時に位置計算用のコードが埋め込まれた非abstractクラスとして実行されます。
 * 
 * @author hide92795
 */
public abstract class Box {
	/**
	 * 表示時を表す定数です。
	 */
	public static final int SHOWED = 0;
	/**
	 * 非表示時を表す定数です。
	 */
	public static final int HIDED = 1;
	/**
	 * 表示中を表す定数です。
	 */
	public static final int SHOWING = 2;
	/**
	 * 非表示へ移行中を表す定数です。
	 */
	public static final int HIDING = 3;
	/**
	 * 名前表示を左寄りすることを表します。
	 */
	public static final int NAME_LEFT = 0;
	/**
	 * 名前表示を中央に配置することを表します。
	 */
	public static final int NAME_CENTER = 1;
	/**
	 * このクラスのコンストラクターの引数を表します。
	 */
	// imageId, showX, showY, showAlpha, hideX, hideY
	// hideAlpha, nameType, nameX, nameY, areaLightUpX, areaLightUpY, areaRightDownX, areaRightDownY
	public static final Class<?>[] CONSTRUCTOR = { int.class, int.class, int.class, float.class, int.class, int.class,
			float.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class };
	/**
	 * このメッセージボックスの画像IDです。
	 */
	private final int imageId;
	/**
	 * 表示時のX座標です。
	 */
	private final int showX;
	/**
	 * 表示時のY座標です。
	 */
	private final int showY;
	/**
	 * 表示時のアルファ値です。
	 */
	private final float showAlpha;
	/**
	 * 非表示時のX座標です。
	 */
	private final int hideX;
	/**
	 * 非表示時のY座標です。
	 */
	private final int hideY;
	/**
	 * 非表示時のアルファ値です。
	 */
	private final float hideAlpha;
	/**
	 * 名前の配置タイプです。
	 */
	private final int nameType;
	/**
	 * 名前を表示するX座標です。
	 */
	private final int nameX;
	/**
	 * 名前を表示するY座標です。
	 */
	private final int nameY;
	/**
	 * 文字表示エリアの左上のX座標です。
	 */
	private final int areaLightUpX;
	/**
	 * 文字表示エリアの左上のY座標です。
	 */
	private final int areaLightUpY;
	/**
	 * 文字表示エリアの右下のX座標です。
	 */
	private final int areaRightDownX;
	/**
	 * 文字表示エリアの右下のY座標です。
	 */
	private final int areaRightDownY;
	/**
	 * 文字表示エリア横幅です。
	 */
	private final int areaWidth;
	/**
	 * 文字表示エリア高さです。
	 */
	private final int areaHeight;
	/**
	 * 更新時のモード
	 */
	private int mode;
	/**
	 * 現在のX座標
	 */
	private float x;
	/**
	 * 現在のY座標
	 */
	private float y;
	/**
	 * 現在のアルファ値
	 */
	private float alpha;
	/**
	 * 処理が始まってから経過した時間です。
	 */
	private int elapsedTime;
	/**
	 * 開閉完了の通知を受け取るリスナーです。
	 */
	private BoxProcessListener listener;

	/**
	 * メッセージボックスを生成します。
	 * 
	 * @param imageId
	 *            このメッセージボックスの画像ID
	 * @param showX
	 *            表示時のX座標
	 * @param showY
	 *            表示時のY座標
	 * @param showAlpha
	 *            表示時のアルファ値
	 * @param hideX
	 *            非表示時のX座標
	 * @param hideY
	 *            非表示時のY座標
	 * @param hideAlpha
	 *            非表示時のアルファ値
	 * @param nameType
	 *            名前の配置タイプ
	 * @param nameX
	 *            名前を表示するX座標
	 * @param nameY
	 *            名前を表示するY座標
	 * @param areaLightUpX
	 *            文字表示エリアの左上のX座標
	 * @param areaLightUpY
	 *            文字表示エリアの左上のY座標
	 * @param areaRightDownX
	 *            文字表示エリアの右下のX座標
	 * @param areaRightDownY
	 *            文字表示エリアの右下のY座標
	 */
	public Box(int imageId, int showX, int showY, float showAlpha, int hideX, int hideY, float hideAlpha, int nameType,
			int nameX, int nameY, int areaLightUpX, int areaLightUpY, int areaRightDownX, int areaRightDownY) {
		this.imageId = imageId;
		this.showX = showX;
		this.showY = showY;
		this.showAlpha = showAlpha;
		this.hideX = hideX;
		this.hideY = hideY;
		this.hideAlpha = hideAlpha;
		this.nameType = nameType;
		this.nameX = nameX;
		this.nameY = nameY;
		this.areaLightUpX = areaLightUpX;
		this.areaLightUpY = areaLightUpY;
		this.areaRightDownX = areaRightDownX;
		this.areaRightDownY = areaRightDownY;
		this.areaWidth = this.areaRightDownX - this.areaLightUpX;
		this.areaHeight = this.areaRightDownY - this.areaLightUpY;
		this.mode = 1;
	}

	/**
	 * メッセージボックスの位置を更新します。
	 * 
	 * @param story
	 *            現在、画面を描画している {@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public final void update(PanelStory story, int delta) {
		switch (mode) {
		case SHOWED: {
			x = showX;
			y = showY;
			alpha = showAlpha;
			break;
		}
		case HIDED: {
			x = hideX;
			y = hideY;
			alpha = hideAlpha;
			break;
		}
		case SHOWING: {
			elapsedTime += delta;
			float ratio = updateShowRatio(elapsedTime);
			if (ratio >= 1.0f) {
				setMode(SHOWED);
				ratio = 1.0f;
				if (listener != null) {
					listener.onProcessFinish();
				}
			}
			x = updateShowX(ratio);
			y = updateShowY(ratio);
			alpha = updateShowAlpha(ratio);
			break;
		}
		case HIDING: {
			elapsedTime += delta;
			float ratio = updateHideRatio(elapsedTime);
			if (ratio >= 1.0f) {
				setMode(HIDED);
				ratio = 1.0f;
				if (listener != null) {
					checkWordsHide(story);
					listener.onProcessFinish();
				}
			}
			x = updateHideX(ratio);
			y = updateHideY(ratio);
			alpha = updateHideAlpha(ratio);
			break;
		}
		default:
			break;
		}
	}

	/**
	 * ボックスを閉じた際に文章を削除するかどうかを確認し、削除する場合は削除を行います。
	 * 
	 * @param story
	 *            現在、画面を描画している {@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 */
	private void checkWordsHide(PanelStory story) {
		int i = story.engine().getConfigurationManager()
				.getValue(ConfigurationManager.VARIABLE_RENDER, SystemSettings.RENDER_WORDS_CLEAR_ON_CLOSE_BOX);
		if (i == 1) {
			story.setWords(null);
			story.setInternalData(PanelStory.INTERNAL_DATA_WORDS_LINE, -1);
		}
	}

	/**
	 * メッセージボックスの描画を行います。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public final void render(NovelEngine engine) {
		Texture t = engine.getImageManager().getImage(imageId);
		if (t != null) {
			Renderer.renderImage(t, alpha, x, y);
		}
	}

	/**
	 * 開閉完了の通知を受け取るリスナーを設定します。
	 * 
	 * @param listener
	 *            開閉完了の通知を受け取るリスナー
	 */
	public final void setListener(BoxProcessListener listener) {
		this.listener = listener;
	}

	/**
	 * 描画モードを設定します。
	 * 
	 * @param mode
	 *            描画モード
	 */
	public final void setMode(int mode) {
		this.mode = mode;
		this.elapsedTime = 0;
	}

	/**
	 * 描画モードを取得します。
	 * 
	 * @return 描画モード
	 */
	public final int getMode() {
		return mode;
	}

	/**
	 * 表示時の更新割合を取得します。
	 * 
	 * @param time
	 *            処理が始まってから経過した時間
	 * @return 更新割合
	 */
	protected abstract float updateShowRatio(int time);

	/**
	 * 表示時のX座標を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return X座標
	 */
	protected abstract float updateShowX(float ratio);

	/**
	 * 表示時のY座標を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return Y座標
	 */
	protected abstract float updateShowY(float ratio);

	/**
	 * 表示時のアルファ値を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return アルファ値
	 */
	protected abstract float updateShowAlpha(float ratio);

	/**
	 * 隠す時の更新割合を取得します。
	 * 
	 * @param time
	 *            処理が始まってから経過した時間
	 * @return 更新割合
	 */
	protected abstract float updateHideRatio(int time);

	/**
	 * 隠す時のX座標を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return X座標
	 */
	protected abstract float updateHideX(float ratio);

	/**
	 * 隠す時のY座標を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return Y座標
	 */
	protected abstract float updateHideY(float ratio);

	/**
	 * 隠す時のアルファ値を更新します。
	 * 
	 * @param ratio
	 *            更新割合
	 * @return アルファ値
	 */
	protected abstract float updateHideAlpha(float ratio);

	/**
	 * 現在のX座標を取得します。
	 * 
	 * @return 現在のX座標
	 */
	public final float getX() {
		return x;
	}

	/**
	 * 現在のY座標を取得します。
	 * 
	 * @return 現在のY座標
	 */
	public final float getY() {
		return y;
	}

	/**
	 * 文字表示エリアの左上のX座標を取得します。
	 * 
	 * @return 文字表示エリアの左上のX座標
	 */
	public final int getAreaLightUpX() {
		return areaLightUpX;
	}

	/**
	 * 文字表示エリアの左上のY座標を取得します。
	 * 
	 * @return 文字表示エリアの左上のY座標
	 */
	public final int getAreaLightUpY() {
		return areaLightUpY;
	}

	/**
	 * 文字表示エリアの右下のX座標を取得します。
	 * 
	 * @return 文字表示エリアの右下のX座標
	 */
	public final int getAreaRightDownX() {
		return areaRightDownX;
	}

	/**
	 * 文字表示エリアの右下のY座標を取得します。
	 * 
	 * @return 文字表示エリアの右下のY座標
	 */
	public final int getAreaRightDownY() {
		return areaRightDownY;
	}

	/**
	 * 文字表示エリアの横幅を取得します。
	 * 
	 * @return 文字表示エリアの横幅
	 */
	public final int getAreaWidth() {
		return areaWidth;
	}

	/**
	 * 文字表示エリアの高さを取得します。
	 * 
	 * @return 文字表示エリアの高さ
	 */
	public final int getAreaHeight() {
		return areaHeight;
	}

	/**
	 * 名前の配置タイプを取得します。
	 * 
	 * @return 名前の配置タイプ
	 */
	public final int getNameType() {
		return nameType;
	}

	/**
	 * 名前を表示するX座標を取得します。
	 * 
	 * @return 名前を表示するX座標
	 */
	public final int getNameX() {
		return nameX;
	}

	/**
	 * 名前を表示するY座標を取得します。
	 * 
	 * @return 名前を表示するY座標
	 */
	public final int getNameY() {
		return nameY;
	}
}
