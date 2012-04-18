package hide92795.novelengine;

import static org.lwjgl.opengl.GL11.*;
import hide92795.novelengine.character.EntryCharacter;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.figure.Figure;

import java.util.LinkedList;

import org.newdawn.slick.opengl.Texture;

public class BackGround {
	public int primaryBgID;
	public int secondaryBgID;

	private int imageX;
	private int imageY;
	private double imageMagnification;
	private double movementX;
	private double movementY;

	private LinkedList<EntryCharacter> characters;
	private Figure figure;

	public BackGround(Figure figure) {
		this.figure = figure;
	}

	public void createStencil(NovelEngine engine, int priority) {
		glStencilFunc(GL_ALWAYS, priority, ~0);
		glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
		glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
		figure.renderStencil(engine);
		glColorMask(true, true, true, true);
		glDepthMask(true);
	}

	public void render(NovelEngine engine, int priority) {
		glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
		glStencilFunc(GL_EQUAL, priority, ~0);
		Texture texture = engine.imageManager.getImage(primaryBgID);
		if (texture != null) {
			Renderer.renderBgImage(texture);
		}
	}

}
