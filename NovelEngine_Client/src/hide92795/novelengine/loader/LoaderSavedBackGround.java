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

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataSavedBackGround;

import java.io.InputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

/**
 * セーブファイルから背景データをロードするためのクラスです。
 * 
 * @author hide92795
 */
public class LoaderSavedBackGround extends Loader {
	/**
	 * セーブデータから背景データをロードします。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param is
	 *            ロード元のストリーム
	 * @return ロードした背景データ
	 * @throws Exception
	 *             何らかのエラーが発生した場合
	 */
	public static DataSavedBackGround load(NovelEngine engine, InputStream is) throws Exception {
		DataSavedBackGround data = new DataSavedBackGround();
		MessagePack msgpack = new MessagePack();
		Unpacker unpacker = msgpack.createUnpacker(is);
		UnpackerIterator iterator = unpacker.iterator();
		while (iterator.hasNext()) {
			byte target = iterator.next().asIntegerValue().byteValue();
			BackGround backGround = BackGround.load(engine, iterator);
			data.addBackGround(target, backGround);
		}
		return data;
	}
}
