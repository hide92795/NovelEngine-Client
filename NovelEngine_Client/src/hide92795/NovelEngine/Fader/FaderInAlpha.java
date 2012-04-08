package hide92795.novelengine.fader;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class FaderInAlpha extends FaderIn {
	private double alpha = 1.0f;
	private Color color;
	private int textureid = 0;
	private Texture texture;
	private int elapsedTime;
	private int totalTime;

	public FaderInAlpha(NovelEngine engine, FaderListener listener, int msec,
			String color) {
		super(engine, listener, color);
		this.color = Color.decode(color);
		this.totalTime = msec;
	}

	@Override
	public void reset() {
		super.reset();
		elapsedTime = 0;
	}

	@Override
	public void update(int delta) {
		elapsedTime += delta;
		double a = (double) elapsedTime / totalTime;
		alpha = 1.0d - a;
		if (alpha < 0.0f) {
			alpha = 0.0f;
			finish();
		}
		if (texture == null && textureid != 0) {
			texture = engine.imageManager.getImage(textureid);
		}
	}

	@Override
	public void render() {
		if (texture != null) {
			Renderer.renderBgImage(texture, alpha);
		} else {
			Renderer.renderColor(color.r, color.g, color.b, alpha);
		}
	}

	public void setTextureId(int textureid) {
		this.textureid = textureid;
		texture = engine.imageManager.getImage(textureid);
	}
}
