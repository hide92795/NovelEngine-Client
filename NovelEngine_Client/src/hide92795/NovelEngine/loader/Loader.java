package hide92795.novelengine.loader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Loader {

	public static final byte[] KEY = "0123456789ABCDEF".getBytes();

	public static final int DATA_BASIC = 0;
	public static final int DATA_MAINMENU = 1;
	public static final int DATA_STORY = 2;
	public static final int DATA_GUI = 3;
	public static final int DATA_CHARACTER = 4;
	public static final int DATA_BGIMAGE = 5;

	public static Key getKey() {
		return new SecretKeySpec(KEY, "AES");
	}

	public static byte[] readAll(InputStream inputStream) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		while (true) {
			int len = inputStream.read(buffer);
			if (len < 0) {
				break;
			}
			bos.write(buffer, 0, len);
		}
		return bos.toByteArray();
	}

	public static CipherInputStream createCipherInputStream(File file) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Key key = Loader.getKey();
		FileInputStream fis = new FileInputStream(file);

		byte[] iv = new byte[16];

		fis.read(iv);

		Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");

		IvParameterSpec ivspec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

		CipherInputStream cis = new CipherInputStream(new BufferedInputStream(fis), cipher);

		return cis;
	}
}
