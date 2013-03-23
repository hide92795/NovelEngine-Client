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

import java.util.HashMap;

/**
 * キャラクターの表情などのデータを管理するクラスです。
 * 
 * @author hide92795
 */
public class DataCharacter {
	/**
	 * このキャラクターの表情データを管理するマップです。
	 */
	private HashMap<Integer, Face> faces;
	/**
	 * このキャラクターの文章を表示する際に表示される名前です。
	 */
	private final String name;
	/**
	 * このキャラクターの名前を描画するフォントのIDです。
	 */
	private final int font;
	/**
	 * このキャラクターの名前の画像データが作成されているかどうかです。
	 */
	private boolean createdNameImage;
	/**
	 * このキャラクターの名前の画像データのIDです。
	 */
	private int nameImageId;

	/**
	 * 指定された名前のキャラクターのデータを作成します。
	 * 
	 * @param name
	 *            このキャラクターの名前
	 * @param font
	 *            名前を描画するフォントのID
	 */
	public DataCharacter(String name, int font) {
		this.name = name;
		this.font = font;
		faces = new HashMap<Integer, Face>();
	}

	/**
	 * 表情データを追加します。
	 * 
	 * @param id
	 *            表情データのID
	 * @param face
	 *            追加する表情データ
	 */
	public final void addFace(int id, Face face) {
		faces.put(id, face);
	}

	/**
	 * 指定されたIDの表情データを取得します。
	 * 
	 * @param faceId
	 *            表情データのID
	 * @return 表情データ
	 */
	public final Face getFace(int faceId) {
		return faces.get(faceId);
	}

	/**
	 * このキャラクターの名前を取得します。
	 * 
	 * @return このキャラクターの名前
	 */
	public final String getName() {
		return name;
	}

	/**
	 * このキャラクターの名前を描画するフォントのIDを取得します。
	 * 
	 * @return このキャラクターの名前を描画するフォントのID
	 */
	public int getFont() {
		return font;
	}

	/**
	 * このキャラクターの名前の画像データが作成されているかどうかを取得します。
	 * 
	 * @return このキャラクターの名前の画像データが作成されているかどうか
	 */
	public boolean isCreatedNameImage() {
		return createdNameImage;
	}

	/**
	 * このキャラクターの名前の画像データのIDを取得します。
	 * 
	 * @return このキャラクターの名前の画像データのID
	 */
	public int getNameImageId() {
		return nameImageId;
	}

	/**
	 * このキャラクターの名前の画像データのIDを設定します。
	 * 
	 * @param nameImageId
	 *            このキャラクターの名前の画像データのID
	 */
	public void setNameImageId(int nameImageId) {
		this.nameImageId = nameImageId;
		createdNameImage = true;
	}

}
