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
package hide92795.novelengine.manager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;

/**
 * イメージデータを管理するためのマネージャーです。
 *
 * @author hide92795
 */
public class ImageManager {
	/**
	 * イメージIDとそれに対応するイメージを格納するマップです。
	 */
	private HashMap<Integer, Texture> images;

	/**
	 * {@link hide92795.novelengine.manager.ImageManager ImageManager} のオブジェクトを生成します。
	 */
	public ImageManager() {
		images = new HashMap<Integer, Texture>();
	}

	/**
	 * 指定されたIDのイメージを取得します。
	 *
	 * @param id
	 *            イメージID
	 * @return 指定されたIDのイメージ
	 */
	public Texture getImage(int id) {
		Texture t = images.get(id);
		return t;
	}

	/**
	 * イメージを登録します。
	 *
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータが格納された <code>byte</code> 配列
	 */
	public void putTexture(int id, byte[] image) {
		Texture t = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(image);
		try {
			t = TextureLoader.getTexture("PNG", bis);
		} catch (Exception e) {
			System.err.println("イメージをロードできませんでした。");
			e.printStackTrace();
		}
		images.put(id, t);
	}

	/**
	 * イメージを登録します。
	 *
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータが格納された <code>BufferedImage</code>
	 */
	public void putTexture(int id, BufferedImage image) {
		Texture t = null;
		try {
			t = BufferedImageUtil.getTexture(String.valueOf(id), image);
		} catch (IOException e) {
			System.err.println("イメージをロードできませんでした。");
			e.printStackTrace();
		}
		images.put(id, t);
	}

	/**
	 * 指定されたイメージIDのイメージが登録されているかを返します。
	 *
	 * @param id
	 *            検索するイメージID
	 * @return 登録されている場合は <code>true</code>
	 */
	public boolean isLoaded(int id) {
		return images.containsKey(id);
	}
}
