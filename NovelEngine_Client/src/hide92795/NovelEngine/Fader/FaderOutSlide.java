package hide92795.novelengine.fader;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.client.NovelEngine;

import java.util.Iterator;
import java.util.LinkedList;

public class FaderOutSlide extends FaderOut {
	private int x;
	private float w;
	private LinkedList<Float> renderList;
	private float decrease;
	private int displayHeight;

	public FaderOutSlide(NovelEngine engine, FaderListener listener, int x,
			int x1, String color) {
		super(engine, listener, color);
		this.x = x;
		this.w = (float) engine.width / x;
		this.decrease = (float) w / x1;
		this.displayHeight = engine.height;
		renderList = new LinkedList<Float>();
	}

	@Override
	public void update(int delta) {
		int i = renderList.size();
		LinkedList<Float> newRenderList = new LinkedList<Float>();
		Iterator<Float> f = renderList.iterator();
		boolean finish = true;
		while (f.hasNext()) {
			float pos = f.next();
			float xpos = pos + decrease;
			if (xpos > w) {
				xpos = w;
			} else {
				finish = false;
			}
			newRenderList.add(xpos);
		}

		if (finish && i != 0) {
			finish();
		}

		if (i < x) {
			newRenderList.add(0.0f);
		}
		renderList = newRenderList;
	}

	@Override
	public void render() {
		Iterator<Float> i = renderList.iterator();
		glColor4f(color.r, color.g, color.b, 1.0f);
		glDisable(GL_TEXTURE_2D);
		float xpos = 0.0f;
		glBegin(GL_QUADS);
		while (i.hasNext()) {
			float render = i.next();
			glTexCoord2f(0, 0);
			glVertex2f(xpos, displayHeight);
			glTexCoord2f(1, 0);
			glVertex2f(xpos + render, displayHeight);
			glTexCoord2f(1, 1);
			glVertex2f(xpos + render, 0);
			glTexCoord2f(0, 1);
			glVertex2f(xpos, 0);
			xpos += w;
		}
		glEnd();
	}

	@Override
	public void leftClick(int x, int y) {
		super.leftClick(x, y);
	}

}
