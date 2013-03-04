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
import hide92795.novelengine.panel.PanelStory;

/**
 * 変数への代入を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryAssignment extends Story {
	/**
	 * 値を代入する変数の種類です。
	 */
	private final byte varType;
	/**
	 * 値を代入する変数の名前です。
	 */
	private final String varName;
	/**
	 * 代入する値です。
	 */
	private final int value;

	/**
	 * 変数に値を代入するストーリーデータを生成します。
	 *
	 * @param varType
	 *            値を代入する変数の種類
	 * @param varName
	 *            値を代入する変数の名前
	 * @param value
	 *            代入する値
	 */
	public StoryAssignment(byte varType, String varName, int value) {
		this.varType = varType;
		this.varName = varName;
		this.value = value;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			Properties p = story.engine().getConfigurationManager().getProperties(varType);
			p.setProperty(varName, value);
			finish();
		}
	}
}
