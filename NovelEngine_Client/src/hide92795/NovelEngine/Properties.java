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

public class Properties extends HashMap<Integer, Integer> {
	private static final long serialVersionUID = 785300521248095499L;

	public void setProperty(int key, int value) {
		put(key, value);
	}

	public int getProperty(int key) {
		Integer value = get(key);
		return value != null ? value.intValue() : 0;
	}

	public void store(File file) throws Exception {
		Key key = Loader.getKey();
		Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		FileOutputStream fos = new FileOutputStream(file);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		fos.write(cipher.getIV());
		MessagePack msgpack = new MessagePack();
		Packer p = msgpack.createPacker(cos);
		for (Integer i : this.keySet()) {
			p.write(i.intValue());
			p.write(getProperty(i));
		}
		p.flush();
		p.close();
	}

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

	private void load(Unpacker p) {
		for (UnpackerIterator iterator = p.iterator(); iterator.hasNext();) {
			Value v = (Value) iterator.next();
			int key = v.asIntegerValue().intValue();
			v = (Value) iterator.next();
			int value = v.asIntegerValue().intValue();
			setProperty(key, value);
		}
	}

}
