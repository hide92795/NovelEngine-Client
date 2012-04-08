package hide92795.novelengine.character;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

public class Character {
	private final int characterId;
	private final String name;

	/**
	 * Key:表情ID Val:画像ID
	 */
	private HashMap<Integer, Integer> faces;

	public Character(int characterId, String name) {
		this.characterId = characterId;
		this.name = name;
		this.faces = new HashMap<Integer, Integer>();
	}

	public void addFace(int faceId, int imageId) {
		faces.put(faceId, imageId);
	}

	public int getCharacterId() {
		return characterId;
	}

	public String getName() {
		return name;
	}

	public void render(NovelEngine engine, int faceId, double x, double y) {
		render(engine, faceId, x, y, 1.0d);
	}

	public void render(NovelEngine engine, int faceId, double x, double y,
			double alpha) {
		int imgId = faces.get(faceId);
		Texture tex = engine.imageManager.getImage(imgId);
		Renderer.renderImage(tex, alpha, x, y,
				x + tex.getTextureWidth(), y + tex.getTextureHeight());
	}
}
