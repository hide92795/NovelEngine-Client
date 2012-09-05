package hide92795.novelengine.loader;

import hide92795.novelengine.client.NovelEngine;

import java.io.File;
import java.io.IOException;
import javax.crypto.CipherInputStream;

public class LoaderSound extends Loader {
	public static byte[] load(int id) {
		CipherInputStream cis = null;
		byte[] data = null;
		try {
			File path = new File(NovelEngine.getCurrentDir(), "sound");
			cis = Loader.createCipherInputStream(new File(path, id + ".nea"));
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
