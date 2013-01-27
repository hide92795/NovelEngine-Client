package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;

import java.io.File;
import java.io.IOException;
import javax.crypto.CipherInputStream;

/**
 * 音楽データを外部ファイルから読み込むクラスです。
 *
 * @author hide92795
 */
public class LoaderSound extends Loader {
	/**
	 * 指定された音楽IDの音楽を読み込みます。
	 *
	 * @param id
	 *            読み込む対象の音楽ID
	 * @return 読み込んだデータが格納されたbyte配列
	 */
	public static byte[] load(int id) {
		CipherInputStream cis = null;
		byte[] data = null;
		try {
			File path = new File(NovelEngine.getCurrentDir(), "sound");
			cis = Loader.createCipherInputStream(new File(path, id + ".nea"));
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
