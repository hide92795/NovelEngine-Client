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

import hide92795.novelengine.panel.PanelStory;

/**
 * シーンの位置を表すストーリーデータです。<br>
 * このストーリーデータの終了確認が行われることはありません。
 *
 * @author hide92795
 */
public class StoryScene extends Story {
	/**
	 * シーンIDを表します。
	 */
	private final int sceneId;

	/**
	 * シーンの位置を表すストーリーデータを作成します。
	 *
	 * @param sceneId
	 *            シーンID
	 */
	public StoryScene(int sceneId) {
		this.sceneId = sceneId;
	}

	@Override
	public void init(PanelStory story) {
	}

	/**
	 * このストーリーデータが表すシーンIDです。
	 *
	 * @return シーンID
	 */
	public int getSceneId() {
		return sceneId;
	}
}
