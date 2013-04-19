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
public class StoryChangeBackGroundColor extends Story {
	/**
	 * 背景色を変更するバックグラウンドのIDです。
	 */
	private final byte target;
	/**
	 * 変更する背景色の赤成分です
	 */
	private final byte r;
	/**
	 * 変更する背景色の緑成分です
	 */
	private final byte g;
	/**
	 * 変更する背景色の青成分です
	 */
	private final byte b;
	/**
	 * 変更する背景色のアルファ成分です
	 */
	private final byte a;

	/**
	 * バックグラウンドの背景色を変更ストーリーデータを生成します。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
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
	public StoryChangeBackGroundColor(int line, byte target, byte r, byte g, byte b, byte a) {
		super(line);
		this.target = target;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
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
