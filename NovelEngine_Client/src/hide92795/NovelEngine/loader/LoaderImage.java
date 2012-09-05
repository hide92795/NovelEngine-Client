package hide92795.novelengine.loader;

import java.io.File;
import java.io.IOException;
import javax.crypto.CipherInputStream;
import hide92795.novelengine.client.NovelEngine;

public class LoaderImage extends Loader {
	public static byte[] load(int id) {
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
