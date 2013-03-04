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
package hide92795.novelengine.queue;

import java.awt.image.BufferedImage;

import hide92795.novelengine.client.NovelEngine;

/**
 * イメージデータを使用可能にするためのキューデータです。
 *
 * @author hide92795
 */
public class QueueImage extends QueueData {
	/**
	 * 登録するイメージIDです。
	 */
	private int id;
	/**
	 * イメージデータを格納する <code>byte</code> 配列です。
	 */
	private byte[] image;
	/**
	 * イメージデータを格納する {@link java.awt.image.BufferedImage BufferedImage} です。
	 */
	private BufferedImage buf;

	/**
	 * <code>byte</code> 配列からイメージデータの登録を行うキューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータを格納している <code>byte</code> 配列
	 */
	public QueueImage(NovelEngine engine, int id, byte[] image) {
		super(engine);
		this.id = id;
		this.image = image;
	}

	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} からイメージデータの登録を行うキューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータを格納している {@link java.awt.image.BufferedImage BufferedImage}
	 */
	public QueueImage(NovelEngine engine, int id, BufferedImage image) {
		super(engine);
		this.id = id;
		this.buf = image;
	}

	@Override
	public void execute() {
		if (buf == null) {
			engine().getImageManager().putTexture(id, image);
		} else {
			engine().getImageManager().putTexture(id, buf);
		}

	}

}
