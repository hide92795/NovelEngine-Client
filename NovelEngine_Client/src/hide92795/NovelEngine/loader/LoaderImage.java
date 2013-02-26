package hide92795.novelengine.loader;

import java.io.File;
import java.io.IOException;
import javax.crypto.CipherInputStream;
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
	public static byte[] load(final int id) {
		CipherInputStream cis = null;
		byte[] data = null;
		try {
			File path = new File(NovelEngine.getCurrentDir(), "img");
			cis = Loader.createCipherInputStream(new File(path, id + ".nei"));
			data = Loader.readAll(cis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
}
