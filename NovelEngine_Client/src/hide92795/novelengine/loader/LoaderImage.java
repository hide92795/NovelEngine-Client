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
import javax.crypto.CipherInputStream;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;

/**
 * 画像データを外部ファイルから読み込むクラスです。
 *
 * @author hide92795
 */
public class LoaderImage extends Loader {
	/**
	 * 指定された画像IDの画像を読み込みます。
	 *
	 * @param id
	 *            読み込む対象の画像ID
	 * @return 読み込んだデータが格納されたbyte配列
	 */
	public static byte[] load(int id) {
		CipherInputStream cis = null;
		byte[] data = null;
		try {
			File path = new File(NovelEngine.getCurrentDir(), "img");
			cis = Loader.createCipherInputStream(new File(path, id + ".nei"));
			data = Loader.readAll(cis);
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
		return data;
	}
}
