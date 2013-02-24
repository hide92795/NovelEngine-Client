package hide92795.novelengine.character;

import org.newdawn.slick.opengl.Texture;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

public class EntityCharacter {
	private final Face face;
	private int x;
	private int y;

	public EntityCharacter(NovelEngine engine, int characterId, int faceId, int positionId) {
		this.face = engine.getCharacterManager().getCharacter(characterId).getFace(faceId);
		int[] position = engine.getCharacterManager().getPosition(positionId);
		this.x = position[0];
		this.y = position[1];
	}

	public void update(int delta) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void render(NovelEngine engine, float alpha) {
		Texture texture = engine.getImageManager().getImage(face.getImage(0));
		if (texture != null) {
			Renderer.renderImage(texture, alpha, x, y);
		}
	}

}
