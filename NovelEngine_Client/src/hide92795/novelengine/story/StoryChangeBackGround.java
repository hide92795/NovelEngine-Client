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
 * バックグラウンドの画像を変更ストーリーデータです。
 * 
 * @author hide92795
 */
public class StoryChangeBackGround extends Story {
	/**
	 * 変更後の画像IDです。
	 */
	private final int nextBgId;
	/**
	 * 画像を変更するレイヤーのIDです。
	 */
	private final byte target;
	/**
	 * イメージを配置する左上のX座標です。
	 */
	private final int x;
	/**
	 * イメージを配置する左上のY座標です。
	 */
	private final int y;
	/**
	 * イメージを配置する時の拡大率です。
	 */
	private final int magnification;
	/**
	 * イメージを変更するまでの待機時間です。
	 */
	private final int delay;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;

	/**
	 * バックグラウンドの画像を変更するストーリーデータを生成します。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
	 * @param bgId
	 *            変更後の画像
	 * @param target
	 *            変更するレイヤーのID
	 * @param x
	 *            イメージを配置する左上のX座標
	 * @param y
	 *            イメージを配置する左上のY座標
	 * @param magnification
	 *            イメージを配置する時の拡大率
	 * @param delay
	 *            イメージを変更するまでの待機時間
	 */
	public StoryChangeBackGround(int line, int bgId, byte target, int x, int y, int magnification, int delay) {
		super(line);
		this.nextBgId = bgId;
		this.target = target;
		this.x = x;
		this.y = y;
		this.magnification = magnification;
		this.delay = delay;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (elapsedTime >= delay && !isFinish()) {
			// 背景変更
			BackGround background = story.engine().getBackGroundManager().getBackGround(target);
			background.setImageId(nextBgId);
			background.setX(x);
			background.setY(y);
			background.setMagnificartion(magnification);

			finish();
		}
		elapsedTime += delta;
	}
}
