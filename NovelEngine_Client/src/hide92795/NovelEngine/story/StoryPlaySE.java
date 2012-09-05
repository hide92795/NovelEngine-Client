package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.openal.Audio;

import hide92795.novelengine.panel.PanelStory;

public class StoryPlaySE extends Story {
	private boolean finish;
	private final int seId;
	private final boolean wait;
	private Audio sound;
	private boolean played;

	public StoryPlaySE(int seId, boolean wait) {
		this.seId = seId;
		this.wait = wait;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
		played = false;
		sound = story.engine.soundManager.getSound(seId);
	}

	@Override
	public void update(PanelStory panelStory, int delta) {

		if (!finish) {
			if (!played) {
				sound.playAsSoundEffect(1.0f, 1.0f, false);
				played = true;
			}
			if (wait) {
				if (!sound.isPlaying()) {
					finish = true;
				}
			} else {
				finish = true;
			}
		}
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
			sound.stop();
		}
	}
}
