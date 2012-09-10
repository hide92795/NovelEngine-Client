package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;

import soundly.XSound;

public class StoryPlaySE extends Story {
	private boolean finish;
	private final int seId;
	private final boolean wait;
	private XSound sound;
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
		sound.seek(0.0f);
		sound.rewind();
		sound.setMusic(false);
		sound.setLooping(false);
		sound.setVolume(1.0f);
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		if (!finish) {
			if (!played) {
				sound.queue();
				played = true;
			}
			if (wait) {
				if (sound.isStopped()) {
					sound.stop();
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
		if (canSkip(story)) {
			sound.stop();
		}
	}
}
