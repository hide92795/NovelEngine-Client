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
import hide92795.novelengine.client.NovelEngine;

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
	 * このクラスのコンストラクターの引数を表します。
	 */
	public static final Class<?>[] CONSTRUCTOR = { int.class, int.class, int.class, float.class, int.class, int.class,
			float.class };
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
	 * 更新時のモード
	 */
	private int mode;
	/**
	 * 現在のX座標
	 */
	private int x;
	/**
	 * 現在のY座標
	 */
	private int y;
	/**
	 * 現在のアルファ値
	 */
	private float alpha;
	/**
	 * 処理が始まってから経過した時間です。
	 */
	private int elapsedTime;

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
	 */
	public Box(int imageId, int showX, int showY, float showAlpha, int hideX, int hideY, float hideAlpha) {
		this.imageId = imageId;
		this.showX = showX;
		this.showY = showY;
		this.showAlpha = showAlpha;
		this.hideX = hideX;
		this.hideY = hideY;
		this.hideAlpha = hideAlpha;
		this.mode = 1;
	}

	/**
	 * メッセージボックスの位置を更新します。
	 *
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public final void update(int delta) {
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
			x = Math.round(updateShowX(ratio));
			y = Math.round(updateShowY(ratio));
			alpha = updateShowAlpha(ratio);
			break;
		}
		case HIDING: {
			elapsedTime += delta;
			float ratio = updateHideRatio(elapsedTime);
			x = Math.round(updateHideX(ratio));
			y = Math.round(updateHideY(ratio));
			alpha = updateHideAlpha(ratio);
			break;
		}
		default:
			break;
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

}
