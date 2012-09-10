package hide92795.novelengine.background;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.*;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.figure.Figure;
import hide92795.novelengine.figure.Figure_EntireScreen;

import org.newdawn.slick.opengl.Texture;

public class BackGround {
	private int imageId;
	private int xPos;
	private int yPos;
	//拡大率
	private float magnificartion;

	//private LinkedList<EntityCharacter> characters;
	private Figure figure;

	private float r;
	private float g;
	private float b;
	private float alpha = 1.0f;

	public BackGround(Figure figure) {
		this.figure = figure;
		//characters = new LinkedList<EntityCharacter>();
	}

	public BackGround() {
		this(new Figure_EntireScreen());
	}

	public void createStencil(NovelEngine engine, int target) {
		glStencilFunc(GL_ALWAYS, 1, ~0);
		glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
		glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
		figure.renderStencil(engine);
		glColorMask(true, true, true, true);
		glDepthMask(true);
	}

	public void render(NovelEngine engine, int target) {
		glPushMatrix();
		glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
		glStencilFunc(GL_EQUAL, 1, ~0);
		// 背景
		Renderer.renderColor(r, g, b, alpha);
		Texture texture = engine.imageManager.getImage(imageId);
		if (texture != null) {
			Renderer.renderImage(texture, xPos, yPos, alpha, magnificartion);
		}
		// キャラクター
		//		for (EntityCharacter character : characters) {
		//			character.render();
		//		}
		glPopMatrix();
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public void setMagnificartion(int magnificartion) {
		this.magnificartion = (float) magnificartion / 100;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
}
