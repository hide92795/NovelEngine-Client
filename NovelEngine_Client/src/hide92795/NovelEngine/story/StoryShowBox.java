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
	private int frequency = -1;
	private float diff;
	private PanelStory panel;

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
		panel = story;
		if (show) {
			now = height;
		} else {
			now = 0.0f;
			story.setShowBox(false);
		}

		frequency = -1;
	}
	
	private void skip(PanelStory panel){
		if(show){
			now = 0;
			panel.setShowBox(true);
			finish = true;
		}else{
			now = height;
			finish = true;
		}
	}
	
	@Override
	public void keyPressed(int eventKey) {
		System.out.println(Keyboard.getKeyName(eventKey));
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(panel);
		}
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(panelStory);
		}
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
	public void render(NovelEngine engine) {
		float alpha = (height - now) / height;

		float x = engine.width - width;
		float y = engine.height - height + now;

		Texture t = engine.imageManager.getImage(123456);

		Renderer.renderImage(t, alpha, x, y, x + t.getTextureWidth(),
				y + t.getTextureHeight());
		// System.out.println("X:" + x + ", Y:" + y);
	}
}
