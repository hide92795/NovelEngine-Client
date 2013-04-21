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

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataBasic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * 基本データを外部ファイルから読み込むクラスです。
 * 
 * @author hide92795
 */
public class LoaderBasic extends Loader {
	/**
	 * 基本データを外部ファイルから読み込みます。
	 * 
	 * @return ロードされた基本データ
	 * @throws IOException
	 *             何らかの入出力エラーが発生した場合
	 */
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
		data.setArrowResize(unpacker.readBoolean());
		data.setAspectRatio(unpacker.readInt(), unpacker.readInt());

		int iconNum = unpacker.readInt();
		ByteBuffer[] icons = new ByteBuffer[iconNum];
		for (int i = 0; i < iconNum; i++) {
			icons[i] = unpacker.readByteBuffer();
		}
		data.setIcons(icons);
		return data;
	}
}
