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

import hide92795.novelengine.gui.entity.EntityButton;
import hide92795.novelengine.manager.gui.ButtonManager.PositionSet;
import hide92795.novelengine.panel.PanelStory;

/**
 * ボタンを生成するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryButton extends Story {
	/**
	 * ボタンの位置を表すIDです。
	 */
	private int positionId;
	/**
	 * ボタンの配列です。
	 */
	private EntityButton[] buttons;
	/**
	 * ボタンの削除に使用する番号です。
	 */
	private int[] buttonIds;

	/**
	 * ボタンがクリックされ、処理が終了段階に入った場合はtrueです。
	 */
	private boolean endPhase;
	/**
	 * クリック後の処理が終わった後に移動するシーンのIDです。
	 */
	private int jumpSceneId;

	/**
	 * ボタンを生成するストーリーデータを生成します。
	 *
	 * @param positionId
	 *            ボタンの位置を表すID
	 * @param buttons
	 *            ボタンの配列
	 */
	public StoryButton(int positionId, EntityButton[] buttons) {
		this.positionId = positionId;
		this.buttons = buttons;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		int buttonNum = buttons.length;
		buttonIds = new int[buttonNum];
		PositionSet positions = story.engine().getGuiManager().getButtonManager().getPositionSet(positionId);
		for (int i = 0; i < buttonNum; i++) {
			int[] position = positions.getPosition(i);
			EntityButton button = buttons[i];
			button.setX(position[0]);
			button.setY(position[1]);
			button.setOwner(this);
			button.setId(i);
			button.init(story.engine());
			int id = story.addGui(button);
			buttonIds[i] = id;
		}
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			if (endPhase) {
				story.moveScene(jumpSceneId);
				finish();
			}
		}

	}

	/**
	 * このストーリーデーターが管理するボタンのどれかがクリックされたことを表します。
	 *
	 * @param id
	 *            ボタンのID
	 * @param jumpSceneId
	 *            移動先のシーンID
	 */
	public void buttonClicked(int id, int jumpSceneId) {
		if (!endPhase) {
			endPhase = true;
			this.jumpSceneId = jumpSceneId;
			for (EntityButton button : buttons) {
				button.setIgnoreMouseEvent(true);
			}
		}
	}
}
