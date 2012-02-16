package hide92795.NovelEngine.fader;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.Color;

import hide92795.NovelEngine.client.NovelEngine;

public class FaderOutDisappear extends Fader {
	private float alpha = 0.0f;
	private float fadePerSec;
	private Color color;

	public FaderOutDisappear(NovelEngine engine, float sec, String color) {
		super(engine);
		this.color =Color.decode(color);
		this.fadePerSec = (float) 1.0f / sec;
	}

	@Override
	public void update(int delta) {
		float a = (fadePerSec / 1000f) * delta;
		alpha += a;
		if (alpha > 1.0f) {
			alpha = 1.0f;
		}
	}

	@Override
	public void render() {
		glColor4f(color.r, color.g, color.b, alpha);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(engine.width, 0);
			glTexCoord2f(1, 1);
			glVertex2f(engine.width, engine.height);
			glTexCoord2f(0, 1);
			glVertex2f(0, engine.height);
		}
		glEnd();
	}

}
