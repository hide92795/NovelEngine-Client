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
package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

/**
 * キューデータを表す抽象クラスです。
 *
 * @author hide92795
 */
public abstract class QueueData {
	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトを表します。
	 */
	private NovelEngine engine;

	/**
	 * キューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public QueueData(NovelEngine engine) {
		this.engine = engine;
	}

	/**
	 * キューを実行します。
	 */
	public abstract void execute();

	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトを返します。
	 *
	 * @return engine 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public NovelEngine engine() {
		return engine;
	}
}
