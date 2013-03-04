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
package hide92795.novelengine.story;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.panel.PanelStory;

/**
 * バックグラウンドの背景色を変更ストーリーデータです。
 *
 * @author hide92795
 */
public class StoryChangeBgColor extends Story {
	/**
	 * 背景色を変更するバックグラウンドのIDです。
	 */
	private final byte target;
	/**
	 * 変更する背景色の赤成分です
	 */
	private final float r;
	/**
	 * 変更する背景色の緑成分です
	 */
	private final float g;
	/**
	 * 変更する背景色の青成分です
	 */
	private final float b;
	/**
	 * 変更する背景色のアルファ成分です
	 */
	private final float a;

	/**
	 * バックグラウンドの背景色を変更ストーリーデータを生成します。
	 *
	 * @param target
	 *            背景色を変更するバックグラウンドのID
	 * @param r
	 *            変更する背景色の赤成分
	 * @param g
	 *            変更する背景色の緑成分
	 * @param b
	 *            変更する背景色の青成分
	 * @param a
	 *            変更する背景色のアルファ成分
	 */
	public StoryChangeBgColor(byte target, int r, int g, int b, int a) {
		this.target = target;
		this.r = (float) r / 255;
		this.g = (float) g / 255;
		this.b = (float) b / 255;
		if (target == (byte) 10) {
			this.a = 1.0f;
		} else {
			this.a = a / 255;
		}
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			BackGround background = story.engine().getBackGroundManager().getBackGround(target);
			background.setRed(r);
			background.setGreen(g);
			background.setBlue(b);
			background.setAlpha(a);

			finish();
		}

	}

}
