package hide92795.NovelEngine.fader;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.NovelEngine.client.NovelEngine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Color;

public class FaderInBlock extends FaderIn {

	// １ブロックの幅
	private float w;
	// １ブロックの高さ
	private float h;
	private LinkedList<float[]> renderList;
	private Color color;
	private Random random;

	public FaderInBlock(NovelEngine engine, FaderListener listener, int x,
			int y, String color) {
		super(engine, listener, color);
		this.color = Color.decode(color);
		this.w = (float) engine.width / x;
		this.h = (float) engine.height / y;
		int count = x * y;
		renderList = new LinkedList<float[]>();
		for (int i = 0; i < count; i++) {
			int xpos = i % x;
			int ypos = i / x;
			float renderX = xpos * w;
			float renderY = ypos * h;
			renderList.add(new float[] { renderX, renderY });
		}
		random = new Random();
	}

	@Override
	public void update(int delta) {
		if (renderList.size() != 0) {
			renderList.remove(random.nextInt(renderList.size()));
		} else {
			finish();
		}
	}

	@Override
	public void render() {
		Iterator<float[]> i = renderList.iterator();
		glColor4f(color.r, color.g, color.b, 1.0f);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		while (i.hasNext()) {
			float[] render = i.next();
			glTexCoord2f(0, 0);
			glVertex2f(render[0], render[1]);
			glTexCoord2f(1, 0);
			glVertex2f(render[0] + w, render[1]);
			glTexCoord2f(1, 1);
			glVertex2f(render[0] + w, render[1] + h);
			glTexCoord2f(0, 1);
			glVertex2f(render[0], render[1] + h);
		}
		glEnd();
	}

}
