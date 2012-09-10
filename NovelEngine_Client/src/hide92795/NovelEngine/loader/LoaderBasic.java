package hide92795.novelengine.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataBasic;

/**
 *
 * @since b1.0
 * @author hide92795
 */
public class LoaderBasic extends Loader {
	public static DataBasic load() throws IOException {
		File file = NovelEngine.getCurrentDir();
		file = new File(file, "basic.neb");

		FileInputStream fis = new FileInputStream(file);

		MessagePack msgpack = new MessagePack();
		Unpacker unpacker = msgpack.createUnpacker(new BufferedInputStream(fis));

		DataBasic data = new DataBasic();

		data.setGamename(unpacker.readString());
		data.setVersion(unpacker.readString());
		data.setHeight(unpacker.readInt());
		data.setWidth(unpacker.readInt());
		data.setMinHeight(unpacker.readInt());
		data.setMinWidth(unpacker.readInt());
		data.setArrowResize(unpacker.readBoolean());
		return data;
	}
}
