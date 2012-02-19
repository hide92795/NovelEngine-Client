package hide92795.novelengine.fader;

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

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class FaderOutSlide extends FaderOut {
	private int x;
	private float w;
	private LinkedList<Float> renderList;
	private float decrease;
	private int displayHeight;
	private boolean finish;
	private Texture t;

	public FaderOutSlide(NovelEngine engine, FaderListener listener, int x, int x1, String color) {
		super(engine, listener, color);
		this.x = x;
		this.w = (float) engine.width / x;
		this.decrease = (float) w / x1;
		this.displayHeight = engine.height;
		renderList = new LinkedList<Float>();
		makeText();
	}

	private void makeText() {
		BufferedImage bi = new BufferedImage(256, 256,
				BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2d = bi.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(new Font("メイリオ", Font.PLAIN, 24));
		g2d.setColor(java.awt.Color.black);
		g2d.drawString("Now Loading...", 50, 100);
		g2d.drawString("なうろーでぃんぐ...", 50, 130);
		try {
			t = BufferedImageUtil.getTexture("loading", bi);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(int delta) {
		int i = renderList.size();
		@SuppressWarnings("unchecked")
		LinkedList<Float> linkedList = (LinkedList<Float>) renderList.clone();
		Iterator<Float> f = linkedList.iterator();
		renderList.clear();
		while (f.hasNext()) {
			float pos = f.next();
			float xpos = pos + decrease;
			if (xpos > w) {
				xpos = w;
			}
			renderList.add(xpos);
		}

		if (i < x) {
			renderList.add(0.0f);
		} else {
			finish();
		}
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
		if (finish) {
			glEnable(GL_TEXTURE_2D);
			t.bind();
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(1, 0);
				glVertex2f(t.getTextureWidth(), 0);
				glTexCoord2f(1, 1);
				glVertex2f(t.getTextureWidth(), t.getTextureHeight());
				glTexCoord2f(0, 1);
				glVertex2f(0, t.getTextureHeight());
			}
			glEnd();
		}
	}

	@Override
	public void leftClick(int x, int y) {
		super.leftClick(x, y);
	}

}
