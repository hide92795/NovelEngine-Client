package hide92795.novelengine.story;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.fader.Fader;
import hide92795.novelengine.fader.FaderIn;
import hide92795.novelengine.fader.FaderListener;
import hide92795.novelengine.fader.FaderOut;
import hide92795.novelengine.fader.FaderPair;
import hide92795.novelengine.fader.FaderPairAlpha;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeBg extends Story implements FaderListener {
	private final int nextBgId;
	private final FaderPair fader;
	protected boolean finish = false;

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
			if(fader instanceof FaderPairAlpha){
				((FaderPairAlpha) fader).setVia(true); 
			}
		} else {
			fader.setCurrentFader(fader.getFadeout());
		}
		fader.init(this, story, nextBgId);
		story.bgColor = fader.getCurrentFader().color;
	}

	@Override
	public void update(PanelStory p_story, int delta) {
		if (fader != null) {
			fader.update(delta);
		}
	}

	@Override
	public void render() {
		if (fader != null) {
			fader.render();
		}
	}

	@Override
	public void onFinish(Fader fader) {
		finish = true;
	}
}
