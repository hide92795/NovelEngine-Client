package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

public class StoryShowBox extends Story {
	private boolean show;
	protected boolean finish = false;
	private int height = 241;
	private int width = 700;
	private double now;
	private double speed = 0.05d;
	// 経過時間
	private int elapsedTime;
	private int totalTime = 400;
	private double alpha;

	public StoryShowBox(boolean show) {
		this.show = show;
	}

	@Override
	public boolean isWait() {
		return true;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
		elapsedTime = 0;
		if (show) {
			now = height;
		} else {
			now = 0.0d;
			story.setShowBox(false);
		}
	}

	private void skip(PanelStory story) {
		if (show) {
			now = 0.0d;
			story.setShowBox(true);
			finish = true;
		} else {
			now = height;
			finish = true;
		}
	}

	@Override
	public void leftClick(PanelStory story, int x, int y) {
		skip(story);
	}

	@Override
	public void keyPressed(PanelStory story, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(story);
		}
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(panelStory);
		}

		elapsedTime += delta;
		if (show) {
			double d = Math.pow(speed, (double) elapsedTime / totalTime);
			alpha = 1.0d - d;

			now = (double) height * (d - speed);
			if (now <= 0d) {
				now = 0;
				panelStory.setShowBox(true);
				finish = true;
			}
		} else {
			double d = (double) elapsedTime / totalTime;
			alpha = 1.0d - d;
			if (d >= 1.0d) {
				now = height;
				finish = true;
				return;
			}
			now = (double) height * d;
		}

	}

	@Override
	public void render(NovelEngine engine) {
		float x = engine.width - width;
		float y = (float) (engine.height - height + now);

		Texture t = engine.imageManager.getImage(123456);
		Renderer.renderImage(t, alpha, x, y, x + t.getTextureWidth(),
				y + t.getTextureHeight());
	}
}
