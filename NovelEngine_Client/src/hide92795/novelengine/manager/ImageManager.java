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
