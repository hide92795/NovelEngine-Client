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
package hide92795.novelengine.panel;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

/**
 * エンジンが再起不可能な例外が発生した場合に表示されるパネルです。
 *
 * @author hide92795
 */
public class PanelCrashInfo extends Panel {

	/**
	 * パネルを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param exception
	 *            発生した例外
	 */
	public PanelCrashInfo(NovelEngine engine, Exception exception) {
		super(engine);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void init() {
	}

	@Override
	public void render(NovelEngine engine) {
		Renderer.renderColor(1.0f, 0.5f, 1.0f);
	}

	@Override
	public void update(int delta) {
	}
}
