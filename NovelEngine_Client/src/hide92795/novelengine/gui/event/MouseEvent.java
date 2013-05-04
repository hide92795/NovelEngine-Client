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
package hide92795.novelengine.gui.event;

import hide92795.novelengine.client.NovelEngine;

/**
 * マウスに関するイベントです。
 *
 * @author hide92795
 */
public class MouseEvent {
	/**
	 * 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトを表します。
	 */
	private NovelEngine engine;
	/**
	 * イベントが発生した地点のX座標です。
	 */
	private int x;
	/**
	 * イベントが発生した地点のY座標です。
	 */
	private int y;
	/**
	 * このイベントが既に処理されたかを表します。
	 */
	private boolean consumed;

	/**
	 * 指定された座標でのマウスイベントを作成します 。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 * @param x
	 *            イベントのX座標
	 * @param y
	 *            イベントのY座標
	 */
	public MouseEvent(NovelEngine engine, int x, int y) {
		this.engine = engine;
		this.x = x;
		this.y = y;
	}

	/**
	 * 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトを返します。
	 *
	 * @return 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public NovelEngine engine() {
		return engine;
	}

	/**
	 * このイベントの発生地点のX座標を取得します。
	 *
	 * @return イベントが発生した地点のY座標
	 */
	public int getX() {
		return x;
	}

	/**
	 * このイベントの発生地点のY座標を取得します。
	 *
	 * @return イベントが発生した地点のX座標
	 */
	public int getY() {
		return y;
	}

	/**
	 * このイベントが既に処理されたかどうかを取得します。
	 *
	 * @return このイベントが既に処理されているかどうか
	 */
	public boolean isConsumed() {
		return consumed;
	}

	/**
	 * このイベントが処理されたものとしてマークします。
	 */
	public void consume() {
		this.consumed = true;
	}

}
