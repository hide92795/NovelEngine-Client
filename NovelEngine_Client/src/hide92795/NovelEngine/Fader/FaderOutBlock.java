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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;

public class FaderOutBlock extends Fader {

	// １ブロックの幅
	private float w;
	// １ブロックの高さ
	private float h;
	private Iterator<?> list;
	private int x;
	private LinkedList<float[]> renderList;
	private Color color;

	public FaderOutBlock(NovelEngine engine, int x, int y, String color) {
		super(engine);
		this.color = Color.decode(color);
		this.x = x;
		this.w = (float) engine.width / x;
		this.h = (float) engine.height / y;
		int count = x * y;
		Integer[] arr = new Integer[count];
		for (int i = 0; i < count; i++) {
			arr[i] = i;
		}
		List<Integer> l = Arrays.asList(arr);
		Collections.shuffle(l);
		list = l.iterator();
		renderList = new LinkedList<float[]>();
	}

	@Override
	public void update(int delta) {
		if (list.hasNext()) {
			int i = (Integer) list.next();
			int xpos = i % x;
			int ypos = i / x;
			float renderX = xpos * w;
			float renderY = ypos * h;
			renderList.add(new float[] { renderX, renderY });
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
