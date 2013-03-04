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
package hide92795.novelengine.manager;

import hide92795.novelengine.box.Box;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderBox;
import hide92795.novelengine.loader.LoaderResource;

import java.util.HashMap;

/**
 * メッセージボックスを管理するマネージャーです。
 *
 * @author hide92795
 */
public class BoxManager {
	/**
	 * メッセージボックスを管理するためのマップです。
	 */
	private HashMap<Integer, Box> boxes;

	/**
	 * {@link hide92795.novelengine.manager.BoxManager BoxManager} のオブジェクトを生成します。
	 */
	public BoxManager() {
		boxes = new HashMap<Integer, Box>();
	}

	/**
	 * メッセージボックスに関連するデータを読み込みます。
	 *
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param loader
	 *            リソースをロードするためのローダー
	 */
	public void load(NovelEngine engine, LoaderResource loader) {
		LoaderBox.load(engine, loader, boxes);
	}
}
