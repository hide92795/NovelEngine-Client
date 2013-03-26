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
package hide92795.novelengine.background;

import hide92795.novelengine.Effectable;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryBackGroundEffect;

/**
 * 背景及び画面に対して視覚効果を提供するクラスです。
 * 
 * @author hide92795
 */
public abstract class BackGroundEffect extends Effectable {
	/**
	 * エフェクトを初期化します。
	 * 
	 * @param story
	 *            現在、画面を描画している{@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 * @param target
	 *            描画するレイヤーID
	 */
	public abstract void init(PanelStory story, byte target);

	/**
	 * エフェクトを更新します。
	 * 
	 * @param story
	 *            このエフェクトを実行している{@link hide92795.novelengine.story.StoryBackGroundEffect StoryBackGroundEffect} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public abstract void update(StoryBackGroundEffect story, int delta);

	/**
	 * エフェクトを描画します。
	 * 
	 * @param engine
	 *            　実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public abstract void render(NovelEngine engine);

	/**
	 * このエフェクトの実行元の{@link hide92795.novelengine.story.StoryBackGroundEffect StoryBackGroundEffect}
	 * オブジェクトが、マウス及びキーボードイベントによりスキップされた際に呼び出されます。
	 */
	public abstract void skip();
}
