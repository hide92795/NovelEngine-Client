package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.fader.Fader;
import hide92795.novelengine.fader.FaderListener;
import hide92795.novelengine.fader.FaderPair;
import hide92795.novelengine.fader.FaderPairAlpha;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeBg extends Story implements FaderListener {
	private final int nextBgId;
	private final FaderPair fader;
	private boolean finish = false;

	public StoryChangeBg(int bgId, FaderPair fader) {
		this.nextBgId = NovelEngine.theEngine.dataMainMenu.getBackImageIds()[0];
		this.fader = fader;
	}

	public int getNextBgId() {
		return nextBgId;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public boolean isWait() {
		return true;
	}

	@Override
	public void init(PanelStory story) {
		if (story.getBgTextureId() == 0) {
			fader.setCurrentFader(fader.getFadein());
			if (fader instanceof FaderPairAlpha) {
				((FaderPairAlpha) fader).setVia(true);
			}
		} else {
			fader.setCurrentFader(fader.getFadeout());
		}
		fader.init(this, story, nextBgId);
		story.bgColor = fader.getCurrentFader().color;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip();
		}
		if (fader != null) {
			fader.update(delta);
		}
	}

	private void skip() {
		fader.skip();
	}

	@Override
	public void leftClick(PanelStory story, int x, int y) {
		skip();
	}

	@Override
	public void keyPressed(PanelStory story, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip();
		}
	}

	@Override
	public void render(NovelEngine engine) {
		if (fader != null) {
			fader.render();
		}
	}

	@Override
	public void onFinish(Fader fader) {
		finish = true;
	}
}
