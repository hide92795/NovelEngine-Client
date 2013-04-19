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

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

import org.msgpack.packer.Packer;
import org.msgpack.unpacker.UnpackerIterator;
import org.newdawn.slick.opengl.Texture;

/**
 * 画面上の１キャラクターを表し、キャラクターの描画を行うクラスです。
 * 
 * @author hide92795
 */
public class EntityCharacter {
	/**
	 * 表示中のキャラクターのIDです。
	 */
	private final int characterId;
	/**
	 * 表示中のキャラクターの表情です。
	 */
	private final Face face;
	/**
	 * 表示するX座標です。
	 */
	private int x;
	/**
	 * 表示するY座標です。
	 */
	private int y;

	/**
	 * キャラクターを作成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param characterId
	 *            表示中のキャラクターのID
	 * @param faceId
	 *            表示中のキャラクターの表情
	 * @param positionId
	 *            表示する位置を表すポジションID
	 */
	public EntityCharacter(NovelEngine engine, int characterId, int faceId, int positionId) {
		this.characterId = characterId;
		this.face = engine.getCharacterManager().getCharacter(characterId).getFace(faceId);
		int[] position = engine.getCharacterManager().getPosition(positionId);
		this.x = position[0];
		this.y = position[1];
	}

	/**
	 * キャラクターを作成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param characterId
	 *            表示中のキャラクターのID
	 * @param faceId
	 *            表示中のキャラクターの表情
	 * @param x
	 *            表示する位置のX座標
	 * @param y
	 *            表示する位置のY座標
	 */
	public EntityCharacter(NovelEngine engine, int characterId, int faceId, int x, int y) {
		this.characterId = characterId;
		this.face = engine.getCharacterManager().getCharacter(characterId).getFace(faceId);
		this.x = x;
		this.y = y;
	}

	public void update(int delta) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void render(NovelEngine engine, byte alpha) {
		Texture texture = engine.getImageManager().getImage(face.getImage(0));
		if (texture != null) {
			Renderer.renderImage(texture, alpha, x, y);
		}
	}

	/**
	 * 現在のこのキャラクターの状態を保存します。
	 * 
	 * @param character
	 *            保存するキャラクター
	 * @param packer
	 *            保存先のPacker
	 * @throws Exception
	 *             何らかのエラーが発生した場合
	 */
	public static final void save(EntityCharacter character, Packer packer) throws Exception {
		packer.write(character.characterId);
		packer.write(character.face.getFaceId());
		packer.write(character.x);
		packer.write(character.y);
	}

	/**
	 * キャラクターをロードします。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param iterator
	 *            ロード元のUnpackerIterator
	 * @return ロードしたキャラクター
	 * @throws Exception
	 *             何らかのエラーが発生した場合
	 */
	public static final EntityCharacter load(NovelEngine engine, UnpackerIterator iterator) throws Exception {
		int characterId = iterator.next().asIntegerValue().getInt();
		int faceId = iterator.next().asIntegerValue().getInt();
		int x = iterator.next().asIntegerValue().getInt();
		int y = iterator.next().asIntegerValue().getInt();
		EntityCharacter chracter = new EntityCharacter(engine, characterId, faceId, x, y);
		return chracter;
	}
}
