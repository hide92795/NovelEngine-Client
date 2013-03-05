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
 * 指定時間待機するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryWait extends Story {
	/**
	 * 待機する時間を表します。
	 */
	private final int waitTimeMs;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;

	/**
	 * 指定時間待機するストーリーデータを生成します。
	 *
	 * @param waitTimeMs
	 *            待機する時間
	 */
	public StoryWait(int waitTimeMs) {
		this.waitTimeMs = waitTimeMs;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		elapsedTime = 0;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			elapsedTime += delta;
			if (elapsedTime >= waitTimeMs) {
				finish();
			}
		}
	}

}
