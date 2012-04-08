package hide92795.novelengine.fader;

import java.util.Iterator;
import java.util.LinkedList;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

public class FaderInSlide extends FaderIn {

	private int x;
	private float w;
	private float decrease;
	private LinkedList<Float> renderList;

	public FaderInSlide(NovelEngine engine, FaderListener listener, int x,
			int x1, String color) {
		super(engine, listener, color);
		this.x = x;
		this.w = (float) engine.width / x;
		this.decrease = (float) w / x1;
		renderList = new LinkedList<Float>();
	}

	@Override
	public void reset() {
		super.reset();
		renderList.clear();
	}

	@Override
	public void update(int delta) {
		int i = renderList.size();
		LinkedList<Float> newRenderList = new LinkedList<Float>();
		Iterator<Float> f = renderList.iterator();
		boolean finish = true;
		while (f.hasNext()) {
			float pos = f.next();
			float xpos = pos - decrease;
			if (xpos < 0) {
				xpos = 0;
			} else {
				finish = false;
			}
			newRenderList.add(xpos);
		}

		if (finish && i != 0) {
			finish();
		}

		if (i < x) {
			newRenderList.add(w);
		}
		renderList = newRenderList;
	}

	@Override
	public void render() {
		Iterator<Float> i = renderList.iterator();
		float xpos = 0.0f;
		while (i.hasNext()) {
			float render = i.next();
			xpos += w;
			Renderer.renderColor(color.r, color.g, color.b, 1.0f,
					xpos - render, 0, xpos, engine.height);

		}
		Renderer.renderColor(color.r, color.g, color.b, 1.0f, xpos, 0,
				engine.width, engine.height);
	}

}
