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
package hide92795.novelengine.character;

/**
 * キャラクターを配置できる位置を表すクラスです。
 *
 * @author hide92795
 */
public class Position {
	/**
	 * この位置データのIDです。
	 */
	private final int id;
	/**
	 * この位置データが表すX座標です。
	 */
	private final int x;
	/**
	 * この位置データが表すY座標です。
	 */
	private final int y;

	/**
	 * キャラクターを配置できる位置を表すデータを生成します。
	 *
	 * @param id
	 *            位置データのID
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public Position(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	/**
	 * この位置データのIDを取得します。
	 *
	 * @return この位置データのID
	 */
	public final int getId() {
		return id;
	}

	/**
	 * この位置データが表すX座標を取得します。
	 *
	 * @return X座標
	 */
	public final int getX() {
		return x;
	}

	/**
	 * この位置データが表すY座標を取得します。
	 *
	 * @return Y座標
	 */
	public final int getY() {
		return y;
	}

}
