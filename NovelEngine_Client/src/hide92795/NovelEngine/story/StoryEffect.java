package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;

import hide92795.novelengine.background.Effect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

public class StoryEffect extends Story {
	private boolean finish = false;
	private final byte target;
	private final int delay;
	private final Effect effect;
	private int elapsedTime;
	private boolean timeElapsed;

	public StoryEffect(byte target, int delay, Effect effect) {
		this.target = target;
		this.delay = delay;
		this.effect = effect;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		effect.init(story, target);
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			if (timeElapsed) {
				effect.update(this, delta);
			} else {
				if (elapsedTime > delay) {
					timeElapsed = true;
				} else {
					elapsedTime += delta;
				}
			}
		}
	}

	@Override
	public void render(NovelEngine engine) {
		if (timeElapsed) {
			effect.render(engine);
		}
	}

	public void effectFinish() {
		this.finish = true;
	}
	
	@Override
	public void keyPressed(PanelStory story, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(story);
		}
	}

	@Override
	public void leftClick(PanelStory story, int x, int y) {
		skip(story);
	}

	private void skip(PanelStory story) {
		if(canSkip(story)){
			effect.skip();
		}
	}

}
