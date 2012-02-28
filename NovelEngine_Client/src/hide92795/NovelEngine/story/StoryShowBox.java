package hide92795.novelengine.story;

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
	private int frequency = -1;
	private float diff;

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
		if (show) {
			now = height;
		} else {
			now = 0.0f;
			story.setShowBox(false);
		}

		frequency = -1;
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		setFrequency(400 / delta);
		if (show) {
			now -= diff;
			if (now <= height / 2) {
				diff *= 0.95f;
			}
			if (now <= 0) {
				now = 0;
				panelStory.setShowBox(true);
				finish = true;
			}
		} else {
			now += diff;
			if (now >= height) {
				now = height;
				finish = true;
			}
		}

	}

	private void setFrequency(int i) {
		if (frequency == -1) {
			frequency = i;
			diff = (float) height / i;
		}
	}

	@Override
	public void render() {
		float alpha = (height - now) / height;

		float x = NovelEngine.theEngine.width - width;
		float y = NovelEngine.theEngine.height - height + now;

		Texture t = NovelEngine.theEngine.imageManager.getImage(123456);

		Renderer.renderImage(t, alpha, x, y, x + t.getTextureWidth(),
				y + t.getTextureHeight());
		// System.out.println("X:" + x + ", Y:" + y);
	}
}
