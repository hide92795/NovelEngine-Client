package hide92795.novelengine.panel;

import hide92795.novelengine.client.NovelEngine;

public abstract class Panel {

	public NovelEngine engine;

	public Panel(NovelEngine engine) {
		this.engine = engine;
	}

	public abstract void render(NovelEngine engine);

	public abstract void update(int delta);

	public void init() {

	}

	public void leftClick(int x, int y) {
	}

	public void rightClick(int x, int y) {
	}

	public void keyPressed(int eventKey) {
	}

	public void keyReleased(int eventKey) {
	}
}
