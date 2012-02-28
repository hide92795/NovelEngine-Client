package hide92795.novelengine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.client.NovelEngine;

import org.newdawn.slick.opengl.Texture;

public class Renderer {
	public static void renderBgImage(Texture texture) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, 1.0f, 0, 0,
				texture.getTextureWidth(), texture.getTextureHeight());
	}
	
	public static void renderBgImage(Texture texture, float alpha) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, alpha, 0, 0,
				texture.getTextureWidth(), texture.getTextureHeight());
	}

	public static void renderImage(Texture texture, float x, float y, float x1,
			float y1) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, 1.0f, x, y, x1, y1);
	}

	public static void renderImage(Texture texture, float alpha, float x,
			float y, float x1, float y1) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, alpha, x, y, x1, y1);
	}

	public static void renderImage(Texture texture, float red, float green,
			float blue, float a, float x, float y, float x1, float y1) {
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4f(red, green, blue, a);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x1, y);
			glTexCoord2f(1, 1);
			glVertex2f(x1, y1);
			glTexCoord2f(0, 1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	public static void renderColor(float red, float green, float blue) {
		renderColor(red, green, blue, 1.0f, 0, 0, NovelEngine.theEngine.width,
				NovelEngine.theEngine.height);
	}

	public static void renderColor(float red, float green, float blue,
			float alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.theEngine.width,
				NovelEngine.theEngine.height);
	}

	public static void renderColor(float red, float green, float blue,
			float alpha, float x, float y, float x1, float y1) {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4f(red, green, blue, alpha);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x1, y);
			glTexCoord2f(1, 1);
			glVertex2f(x1, y1);
			glTexCoord2f(0, 1);
			glVertex2f(x, y1);
		}
		glEnd();
	}
}
