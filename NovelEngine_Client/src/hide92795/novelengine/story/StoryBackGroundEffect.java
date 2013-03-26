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

import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;

/**
 * 背景エフェクトを実行するストーリーデータです。
 * 
 * @author hide92795
 */
public class StoryBackGroundEffect extends Story {
	/**
	 * エフェクトをかけるレイヤーのIDです。
	 */
	private final byte target;
	/**
	 * エフェクトを実行するまでの待機時間です。
	 */
	private final int delay;
	/**
	 * このストーリーデータで実行する背景エフェクトです。
	 */
	private final BackGroundEffect backGroundEffect;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;
	/**
	 * 指定された時間以上待機したかどうかを表します。
	 */
	private boolean timeElapsed;

	/**
	 * エフェクトを実行するストーリーデータを生成します。
	 * 
	 * @param target
	 *            エフェクトを実行するレイヤーのID
	 * @param delay
	 *            エフェクトを実行するまでの待機時間
	 * @param backGroundEffect
	 *            実行するエフェクト
	 */
	public StoryBackGroundEffect(byte target, int delay, BackGroundEffect backGroundEffect) {
		this.target = target;
		this.delay = delay;
		this.backGroundEffect = backGroundEffect;
	}

	@Override
	public void init(PanelStory story) {
		backGroundEffect.init(story, target);
		resetFinish();
		timeElapsed = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			if (timeElapsed) {
				backGroundEffect.update(this, delta);
			} else {
				if (elapsedTime > delay) {
					timeElapsed = true;
				} else {
					elapsedTime += delta;
				}
			}
		}
	}

	@Override
	public void render(NovelEngine engine) {
		if (timeElapsed) {
			backGroundEffect.render(engine);
		}
	}

	/**
	 * エフェクトが終了した時に呼ばれます。
	 */
	public void effectFinish() {
		finish();
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		skip(event.engine());
	}

	/**
	 * スキップ可能な場合にスキップを行います。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine)) {
			backGroundEffect.skip();
		}
	}

}
