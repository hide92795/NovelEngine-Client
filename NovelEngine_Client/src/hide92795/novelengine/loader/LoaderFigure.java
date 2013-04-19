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

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.background.figure.Figure;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.figure.FigureLoader;
import hide92795.novelengine.loader.figure.FigureLoaderCircle;
import hide92795.novelengine.loader.figure.FigureLoaderPolygon;
import hide92795.novelengine.loader.figure.FigureLoaderQuadrangle;
import hide92795.novelengine.loader.figure.FigureLoaderTriangle;
import hide92795.novelengine.manager.FigureManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * フィギュアデータを外部ファイルから読み込むためのクラスです。
 * 
 * @author hide92795
 */
public class LoaderFigure extends Loader {
	/**
	 * 各フィギュアデータのローダーを保存するマップです。
	 */
	private static HashMap<Byte, FigureLoader> loaders;

	static {
		loaders = new HashMap<Byte, FigureLoader>();
		loaders.put(Figure.FIGURE_TRIANGLE, new FigureLoaderTriangle());
		loaders.put(Figure.FIGURE_QUADANGLE, new FigureLoaderQuadrangle());
		loaders.put(Figure.FIGURE_POLYGON, new FigureLoaderPolygon());
		loaders.put(Figure.FIGURE_CIRCLE, new FigureLoaderCircle());
	}

	/**
	 * フィギュアデータを読み込み、登録します。
	 * 
	 * @param figureManager
	 *            フィギュアデータを管理するフィギュアマネージャー
	 */
	public static void load(FigureManager figureManager) {
		File path = new File(NovelEngine.getCurrentDir(), "object");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "figure.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			int num = unpacker.readInt();

			for (int i = 0; i < num; i++) {
				int id = unpacker.readInt();
				byte type = unpacker.readByte();
				FigureLoader loader = loaders.get(type);
				Figure figure = loader.load(id, unpacker);
				figureManager.addFigure(id, figure);
			}
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
	}

}
