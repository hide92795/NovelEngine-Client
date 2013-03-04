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

import org.newdawn.slick.opengl.Texture;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

/**
 * メッセージボックスの描画に関するデータを保存します。<br>
 * このクラスは起動時に位置計算用のコードが埋め込まれた非abstractクラスとして実行されます。
 *
 * @author hide92795
 */
public abstract class Box {
	public static final int SHOWED = 0;
	public static final int HIDED = 1;
	public static final int SHOWING = 2;
	public static final int HIDING = 3;
	public static final Class<?>[] CONSTRUCTOR = { int.class, int.class, int.class, int.class, int.class, float.class,
			float.class };
	/**
	 * このメッセージボックスの画像IDです。
	 */
	private final int imageId;
	private final int showX;
	private final int hideX;
	private final int showY;
	private final int hideY;
	private final float showAlpha;
	private final float hideAlpha;
	private int mode;
	private int x;
	private int y;
	private float alpha;
	private int elapsedTime;

	public Box(int imageId, int showX, int hideX, int showY, int hideY, float showAlpha, float hideAlpha) {
		this.imageId = imageId;
		this.hideX = hideX;
		this.showX = showX;
		this.showY = showY;
		this.hideY = hideY;
		this.showAlpha = showAlpha;
		this.hideAlpha = hideAlpha;
	}

	public final void update(int delta) {
		switch (mode) {
		case SHOWED:
			x = showX;
			y = showY;
			alpha = showAlpha;
			break;
		case HIDED:
			x = hideX;
			y = hideY;
			alpha = hideAlpha;
			break;
		case SHOWING:
			elapsedTime += delta;
			x = updateShowX(elapsedTime);
			y = updateShowY(elapsedTime);
			alpha = updateShowAlpha(elapsedTime);
			break;
		case HIDING:
			elapsedTime += delta;
			x = updateHideX(elapsedTime);
			y = updateHideY(elapsedTime);
			alpha = updateHideAlpha(elapsedTime);
			break;
		default:
			break;
		}
	}

	public final void render(NovelEngine engine) {
		Texture t = engine.getImageManager().getImage(imageId);
		if (t != null) {
			Renderer.renderImage(t, alpha, x, y);
		}
	}

	protected abstract int updateShowX(int time);

	protected abstract int updateShowY(int time);

	protected abstract int updateHideX(int time);

	protected abstract int updateHideY(int time);

	protected abstract float updateShowAlpha(int time);

	protected abstract float updateHideAlpha(int time);

}
