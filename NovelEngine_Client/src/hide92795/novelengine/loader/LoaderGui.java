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
		File path = new File(NovelEngine.getCurrentDir(), "object");
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
