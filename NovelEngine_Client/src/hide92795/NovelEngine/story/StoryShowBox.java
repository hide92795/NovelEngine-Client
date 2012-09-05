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
	private float now;
	private float speed = 0.05f;
	// 邨碁℃譎る俣
	private int elapsedTime;
	private int totalTime = 400;
	private float alpha;

	public StoryShowBox(boolean show) {
		this.show = show;
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
			now = 0.0f;
			story.setShowBox(false);
		}
	}

	private void skip(PanelStory story) {
		if (show) {
			now = 0.0f;
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
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(panelStory);
		}

		elapsedTime += delta;
		if (show) {
			float f = (float) Math.pow(speed, (float) elapsedTime / totalTime);
			alpha = 1.0f - f;

			now = (float) height * (f - speed);
			if (now <= 0f) {
				now = 0;
				panelStory.setShowBox(true);
				finish = true;
			}
		} else {
			float f = (float) elapsedTime / totalTime;
			alpha = 1.0f - f;
			if (f >= 1.0f) {
				now = height;
				finish = true;
				return;
			}
			now = (float) height * f;
		}

	}

	@Override
	public void render(NovelEngine engine) {
		float x = engine.width - width;
		float y = (float) (engine.height - height + now);

		Texture t = engine.imageManager.getImage(123456);
		Renderer.renderImage(t, alpha, x, y, x + t.getTextureWidth(), y + t.getTextureHeight());
	}
}
