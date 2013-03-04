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

/**
 * 外部ファイルからデータを読み取るための便利なメソッドが含まれます。
 *
 * @author hide92795
 */
public abstract class Loader {
	// キーは今後ここへの直接指定のほか、外部ファイル指定もサポートする予定。
	/**
	 * 外部ファイルの復号化に使用するキーです。
	 */
	private static final byte[] KEY = "0123456789ABCDEF".getBytes();

	/**
	 * 復号化のために使用するキーを取得します。
	 *
	 * @return 復号化に使用するキー
	 */
	public static Key getKey() {
		return new SecretKeySpec(KEY, "AES");
	}

	/**
	 * 指定された入力ストリームのデータをすべてbyte配列に読み込みます。
	 *
	 * @param inputStream
	 *            読み込み元の入力ストリーム
	 * @return 読み込んだデータが格納されたbyte配列
	 * @throws IOException
	 *             何らかの入出力エラーが発生した場合
	 */
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

	/**
	 * 指定されたファイルを復号化しながら読み込むためのストリームを取得します。
	 *
	 * @param file
	 *            ストリームを開くファイル
	 * @return 復号化しながら読み込む準備の出来たストリーム
	 * @throws IOException
	 *             何らかの入出力エラーが発生した場合
	 * @throws NoSuchAlgorithmException
	 *             暗号化に使用されているアルゴリズムがサポートされない場合
	 * @throws NoSuchPaddingException
	 *             指定されたパディングがサポートされない場合
	 * @throws InvalidKeyException
	 *             不正な鍵が使用された場合
	 * @throws InvalidAlgorithmParameterException
	 *             不適切なアルゴリズムパラメーターが渡された場合
	 */
	public static CipherInputStream createCipherInputStream(File file) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Key key = Loader.getKey();
		FileInputStream fis = new FileInputStream(file);

		byte[] iv = new byte[16];

		fis.read(iv);

		Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");

		IvParameterSpec ivspec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

		CipherInputStream cis = new CipherInputStream(fis, cipher);

		return cis;
	}
}
