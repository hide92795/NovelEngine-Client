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

import hide92795.novelengine.Properties;
import hide92795.novelengine.Utils;
import hide92795.novelengine.panel.PanelStory;

/**
 * ランダムな値を生成するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryRandom extends Story {
	/**
	 * 結果を代入する変数の種類です。
	 */
	private final byte varType;
	/**
	 * 結果を代入する変数の名前です。
	 */
	private final String varName;
	/**
	 * 乱数を生成する範囲です。
	 */
	private final int num;

	/**
	 * ランダムな値を生成するストーリーデータを生成します。
	 *
	 * @param varType
	 *            結果を代入する変数の種類
	 * @param varName
	 *            結果を代入する変数の名前
	 * @param num
	 *            乱数を生成する範囲
	 */
	public StoryRandom(byte varType, String varName, int num) {
		this.varType = varType;
		this.varName = varName;
		this.num = num;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			int randomValue = Utils.getRandom(num);
			Properties p = story.engine().getConfigurationManager().getProperties(varType);
			p.setProperty(varName, randomValue);
			finish();
		}
	}
}
