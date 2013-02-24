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
