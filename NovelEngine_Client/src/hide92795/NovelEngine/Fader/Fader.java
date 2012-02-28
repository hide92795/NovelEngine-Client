package hide92795.novelengine.fader;

import org.newdawn.slick.Color;

import hide92795.novelengine.client.NovelEngine;

public class Fader {
	public static final int FADER_NONE = 0;
	public static final int FADER_ALPHA = 1;
	public static final int FADER_BLOCK = 2;
	public static final int FADER_SLIDE = 3;
	public NovelEngine engine;
	public FaderListener listener;
	private boolean finish = false;
	public Color color;

	public Fader(NovelEngine engine, FaderListener listener, String color) {
		this.engine = engine;
		this.listener = listener;
		this.color = Color.decode(color);
	}

	public void render() {

	}

	public void update(int delta) {
	}

	public void leftClick(int x, int y) {
	}

	public void setListener(FaderListener listener) {
		this.listener = listener;
	}

	public void finish() {
		if (!finish) {
			finish = true;
			if (listener != null) {
				listener.onFinish(this);
			}
		}
	}
}
