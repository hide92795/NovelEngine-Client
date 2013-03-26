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
package hide92795.novelengine.background.effect;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryBackGroundEffect;

/**
 * フェードによる画面効果を提供します。
 *
 * @author hide92795
 */
public class TransitionFade extends BackGroundEffect {
	/**
	 * このエフェクトのIDを表します。
	 */
	public static final int ID = "fade".hashCode();
	/**
	 * このエフェクトのインスタンス化に必要な引数のクラス一覧です。
	 */
	public static final Class<?>[] CONSTRUCTOR = { int.class, int.class };
	/**
	 * フェード前の{@link hide92795.novelengine.background.BackGround BackGround} オブジェクトのアルファ値です。
	 */
	private int firstAlpha;
	/**
	 * フェード後に{@link hide92795.novelengine.background.BackGround BackGround} オブジェクトに設定するアルファ値です。
	 */
	private int endAlpha;
	/**
	 * エフェクトに掛ける時間を表します。単位はミリ秒です。
	 */
	private int totalTime;
	/**
	 * エフェクト開始から経過した時間を表します。単位はミリ秒です。
	 */
	private int elapsedTime;
	/**
	 * フェード処理をおこうなう対象の{@link hide92795.novelengine.background.BackGround BackGround} オブジェクトです。
	 */
	private BackGround background;
	/**
	 * このエフェクトが完了しているかどうかを表します。
	 */
	private boolean finish;
	/**
	 * このエフェクトで変化するアルファ値の量を表します。
	 */
	private int changeAlpha;

	/**
	 * 変化後のアルファ値と変化に掛ける時間を指定してエフェクトを生成します。
	 *
	 * @param endAlpha
	 *            フェード後のアルファ値
	 * @param totalTime
	 *            変化に掛ける時間
	 */
	public TransitionFade(int endAlpha, int totalTime) {
		this.endAlpha = endAlpha;
		this.totalTime = totalTime;
	}

	@Override
	public void init(PanelStory story, byte target) {
		finish = false;
		elapsedTime = 0;
		background = story.engine().getBackGroundManager().getBackGround(target);
		float now = background.getAlpha();
		firstAlpha = (int) (now * 255);
		changeAlpha = endAlpha - firstAlpha;
	}

	@Override
	public void update(StoryBackGroundEffect story, int delta) {
		if (finish) {
			story.effectFinish();
		} else {
			float per = (float) elapsedTime / totalTime;
			if (elapsedTime >= totalTime) {
				finish = true;
				per = 1.0f;
			}
			int fluctuation = (int) (changeAlpha * per);
			int alpha_255 = firstAlpha + fluctuation;
			background.setAlpha((float) alpha_255 / 255);
			elapsedTime += delta;
		}

	}

	@Override
	public void render(NovelEngine engine) {
	}

	@Override
	public void skip() {
		background.setAlpha((float) endAlpha / 255);
		finish = true;
	}

}
