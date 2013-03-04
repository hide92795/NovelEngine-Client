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
package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.character.DataCharacter;
import hide92795.novelengine.character.Face;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.CharacterManager;

import java.io.File;
import java.io.IOException;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * キャラクターデータを外部ファイルから読み込むためのクラスです。
 *
 * @author hide92795
 */
public class LoaderCharacter extends Loader {
	/**
	 * キャラクターデータを読み込みマップに格納します。
	 *
	 * @param manager
	 *            キャラクターを管理するマネージャー
	 */
	public static void load(CharacterManager manager) {
		File path = new File(NovelEngine.getCurrentDir(), "object");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "character.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			// 位置データ
			int position_num = unpacker.readInt();
			for (int i = 0; i < position_num; i++) {
				int positionId = unpacker.readInt();
				int x = unpacker.readInt();
				int y = unpacker.readInt();
				manager.addPosition(positionId, new int[] { x, y });
			}

			int character_num = unpacker.readInt();
			for (int i = 0; i < character_num; i++) {
				int characterId = unpacker.readInt();
				String name = unpacker.readString();

				DataCharacter character = new DataCharacter(name);

				int face_num = unpacker.readInt();
				for (int j = 0; j < face_num; j++) {
					int faceId = unpacker.readInt();
					int interval = unpacker.readInt();
					int image_num = unpacker.readInt();
					int[] images = new int[image_num];
					for (int k = 0; k < image_num; k++) {
						images[k] = unpacker.readInt();
					}
					Face face = new Face(images, interval);
					character.addFace(faceId, face);
				}
				manager.addCharacter(characterId, character);
			}

		} catch (Exception e) {
			throw new NovelEngineException(e, "");
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					throw new NovelEngineException(e, "");
				}
			}
		}
	}
}
