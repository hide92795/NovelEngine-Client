package hide92795.novelengine.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;

/**
 * GUIデータを外部ファイルから読み込むためのクラスです。
 *
 * @author hide92795
 */
public class LoaderGui extends Loader {

	/**
	 * GUIデータを読み込みマップに格納します。
	 *
	 * @param clickable
	 *            GUIデータを保管するマップ
	 */
	public static void load(HashMap<Integer, boolean[]> clickable) {
		File path = new File(NovelEngine.getCurrentDir(), "gui");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "gui.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			// クリック領域
			int num_c = unpacker.readInt();

			for (int i = 0; i < num_c; i++) {
				int id = unpacker.readInt();
				int size = unpacker.readInt();

				boolean[] clickable_b = new boolean[size];
				for (int j = 0; j < size; j++) {
					clickable_b[j] = unpacker.readBoolean();
				}

				clickable.put(id, clickable_b);
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
