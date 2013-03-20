package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;

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
	public static void load(NovelEngine engine, HashMap<Integer, Font> fonts) {
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
				int[] data = { fontId, size };
				map.put(id, data);
			}

			int defaultId = unpacker.readInt();
			Properties p = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
			p.setProperty(Setting.RENDER_FONT, defaultId);

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

				Font base = fontdata.get(fontId);
				Font font = base.deriveFont(size);

				fonts.put(id, font);
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
