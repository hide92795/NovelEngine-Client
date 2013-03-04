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
import hide92795.novelengine.character.EntityCharacter;
import hide92795.novelengine.panel.PanelStory;

/**
 * 指定された背景にキャラクターを配置するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryChangeCharacter extends Story {
	/**
	 * 配置するキャラクターのIDです。
	 */
	private final int characterId;
	/**
	 * キャラクターを配置する背景のIDです。
	 */
	private final byte target;
	/**
	 * キャラクターを配置するまでの遅延時間です。
	 */
	private final int delay;
	/**
	 * 配置する位置を表すIDです。
	 */
	private final int positionId;
	/**
	 * キャラクターの表情を表すIDです。
	 */
	private final int faceId;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;
	/**
	 *
	 */
	private EntityCharacter character;

	/**
	 * @param characterId
	 *            配置するキャラクターのID
	 * @param target
	 *            キャラクターを配置する背景のID
	 * @param delay
	 *            キャラクターを配置するまでの遅延時間
	 * @param positionId
	 *            配置する位置を表すID
	 * @param faceId
	 *            キャラクターの表情を表すID
	 */
	public StoryChangeCharacter(int characterId, byte target, int delay, int positionId, int faceId) {
		this.characterId = characterId;
		this.target = target;
		this.delay = delay;
		this.positionId = positionId;
		this.faceId = faceId;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		elapsedTime = 0;
		character = new EntityCharacter(story.engine(), characterId, faceId, positionId);
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (elapsedTime > delay) {
			BackGround background = story.engine().getBackGroundManager().getBackGround(target);
			background.setCharacter(character);
			finish();
		}
		elapsedTime += delta;
	}

}
