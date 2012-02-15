package hide92795.NovelEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ImageManager {
	private LinkedHashMap<Integer, Texture> images;

	public ImageManager() {
		images = new LinkedHashMap<Integer, Texture>();
	}

	public Texture getImage(int id, byte[] image) {
		Texture t = null;
		if (images.containsKey(id)) {
			t = images.get(id);
		} else {
			ByteArrayInputStream bis = new ByteArrayInputStream(image);
			try {
				t = TextureLoader.getTexture("PNG", bis);
			} catch (IOException e) {
				System.err.println("イメージをロードできませんでした。");
				e.printStackTrace();
			}
			images.put(id, t);
		}
		return t;
	}

	public Texture getImage(int id) {
		Texture t = images.get(id);
		return t;
	}

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

}
