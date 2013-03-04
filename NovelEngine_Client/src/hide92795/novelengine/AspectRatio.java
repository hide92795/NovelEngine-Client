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
package hide92795.novelengine;

import hide92795.novelengine.client.NovelEngine;

import org.lwjgl.opengl.GL11;

/**
 * 画面のアスペクト比を管理するクラスです。
 *
 * @author hide92795
 */
public class AspectRatio {
	/**
	 * 横のアスペクト比を表します。
	 */
	private int ratioWidth;
	/**
	 * 縦のアスペクト比を表します。
	 */
	private int ratioHeight;

	/**
	 * 指定したアスペクト比でオブジェクトを生成します。
	 *
	 * @param width
	 *            横のアスペクト比
	 * @param height
	 *            縦のアスペクト比
	 */
	public AspectRatio(int width, int height) {
		this.ratioWidth = width;
		this.ratioHeight = height;
	}

	/**
	 * ウィンドウのViewportをアスペクト比に合わせて調整します。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 * @param width
	 *            リサイズ後のウィンドウの横幅
	 * @param height
	 *            リサイズ後のウィンドウの縦幅
	 */
	public void adjust(NovelEngine engine, int width, int height) {
		int x = 0;
		int y = 0;
		int w = width;
		int h = height;
		// 横を基準に縦の長さを求める
		int adjHeight = Math.round((float) width * ratioHeight / ratioWidth);
		if (height > adjHeight) {
			// 横はそのまま 縦を短く
			x = 0;
			y = (height - adjHeight) / 2;
			w = width;
			h = adjHeight;
		} else if (height < adjHeight) {
			// 縦はそのまま 横を短く
			int adjWidth = Math.round((float) height * ratioWidth / ratioHeight);
			x = (width - adjWidth) / 2;
			y = 0;
			w = adjWidth;
			h = height;
		}
		float magnification = (float) w / engine.getDefaultWidth();
		engine.setX(x);
		engine.setY(y);
		engine.setWidth(w);
		engine.setHeight(h);
		engine.setMagnification(magnification);
		GL11.glViewport(x, y, w, h);
	}
}
