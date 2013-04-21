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
import hide92795.novelengine.Properties;
import hide92795.novelengine.SystemSettings;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.FontManager;
import hide92795.novelengine.manager.FontManager.DataFont;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * フォントデータを外部ファイルから読み込みためのクラスです。
 * 
 * @author hide92795
 */
public class LoaderFont extends Loader {

	/**
	 * フォントデータを読み込み、マップに追加します。
	 * 
	 * @param fonts
	 *            フォントデータを格納するマップ
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public static void load(NovelEngine engine, HashMap<Integer, DataFont> fonts) {
		HashMap<Integer, int[]> map = new HashMap<Integer, int[]>();

		File path = new File(NovelEngine.getCurrentDir(), "object");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "font.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			// フォントデータ
			// 一旦別のマップに退避させる
			int font_num = unpacker.readInt();
			for (int i = 0; i < font_num; i++) {
				int id = unpacker.readInt();
				int fontId = unpacker.readInt();
				int size = unpacker.readInt();
				int decorationType = unpacker.readInt();

				if (decorationType == FontManager.TEXT_NONE) {
					int inner = unpacker.readInt();
					int[] data = { fontId, size, decorationType, inner };
					map.put(id, data);
				} else {
					int inner = unpacker.readInt();
					int edge = unpacker.readInt();
					int[] data = { fontId, size, decorationType, inner, edge };
					map.put(id, data);
				}
			}

			int defaultId = unpacker.readInt();
			Properties p = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
			p.setProperty(SystemSettings.RENDER_FONT, defaultId);

			HashMap<Integer, Font> fontdata = new HashMap<Integer, Font>();

			ZipInputStream zis = new ZipInputStream(cis);
			for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
				String name = ze.getName();
				int fontId = Integer.parseInt(name);
				Font f = Font.createFont(Font.TRUETYPE_FONT, zis);
				fontdata.put(fontId, f);
			}

			// フォント登録
			Set<Integer> s = map.keySet();
			for (int id : s) {
				int[] data = map.get(id);
				int fontId = data[0];
				int size = data[1];
				int decorationType = data[2];

				Color inner;
				Color edge;
				if (decorationType == FontManager.TEXT_NONE) {
					inner = new Color(data[3]);
					edge = null;
				} else {
					inner = new Color(data[3]);
					edge = new Color(data[4]);
				}

				Font base = fontdata.get(fontId);
				Font font = base.deriveFont((float) size);

				DataFont dataFont = new DataFont(font, decorationType, inner, edge);

				fonts.put(id, dataFont);
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
