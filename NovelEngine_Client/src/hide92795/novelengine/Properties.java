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
package hide92795.novelengine;

import hide92795.novelengine.loader.Loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

/**
 * 設定・フラグなどの情報を管理します。
 *
 * @author hide92795
 */
public class Properties extends HashMap<String, Integer> {
	/**
	 * このクラスを直列化することは推奨されていません。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 指定されたキーに登録されている値を変更します。
	 *
	 * @param key
	 *            値を設定するキー
	 * @param value
	 *            キーに対して設定する値
	 */
	public void setProperty(String key, int value) {
		put(key, value);
	}

	/**
	 * 指定されたキーに登録されている値を取得します。キーに対して値が登録されていない場合は0が返されます。
	 *
	 * @param key
	 *            取得する値のキー
	 * @return キーに対して登録されている値。キーに対して値が登録されていない場合は0
	 */
	public int getProperty(String key) {
		Integer value = get(key);
		if (value == null) {
			return 0;
		} else {
			return value.intValue();
		}
	}

	/**
	 * 指定した{@link java.io.File File}オブジェクトに対してこのプロパティが保持しているキー及び値の組み合わせを保存します。
	 *
	 * @param file
	 *            保存先のファイル
	 * @throws Exception
	 *             保存の最中に何らかのエラーが発生した場合
	 */
	public void store(File file) throws Exception {
		Key key = Loader.getKey();
		Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		FileOutputStream fos = new FileOutputStream(file);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		fos.write(cipher.getIV());
		MessagePack msgpack = new MessagePack();
		Packer p = msgpack.createPacker(cos);
		for (String i : this.keySet()) {
			p.write(i);
			p.write(getProperty(i));
		}
		p.flush();
		p.close();
	}

	/**
	 * 指定した{@link java.io.File File}オブジェクトからキー及び値の組み合わせを読み込み、プロパティに登録します。
	 *
	 * @param file
	 *            読み込み元のファイル
	 * @throws Exception
	 *             読み込みの最中に何らかのエラーが発生した場合
	 */
	public void load(File file) throws Exception {
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(file);
			MessagePack msgpack = new MessagePack();
			Unpacker p = msgpack.createUnpacker(cis);
			load(p);
		} catch (Exception e) {
			throw e;
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	/**
	 * 指定された{@link org.msgpack.unpacker.Unpacker Unpacker} オブジェクトからキー及び値を読み込み、プロパティに登録します。
	 *
	 * @param p
	 *            キー及び値が保持されている{@link org.msgpack.unpacker.Unpacker Unpacker} オブジェクト
	 */
	private void load(Unpacker p) {
		for (UnpackerIterator iterator = p.iterator(); iterator.hasNext();) {
			Value v = (Value) iterator.next();
			String key = v.asRawValue().getString();
			v = (Value) iterator.next();
			int value = v.asIntegerValue().intValue();
			setProperty(key, value);
		}
	}

}
