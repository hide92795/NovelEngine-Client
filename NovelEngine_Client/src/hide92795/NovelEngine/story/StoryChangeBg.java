package hide92795.novelengine.story;

import hide92795.novelengine.fader.Fader;
import hide92795.novelengine.fader.FaderIn;
import hide92795.novelengine.fader.FaderListener;
import hide92795.novelengine.fader.FaderOut;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeBg extends Story implements FaderListener {
	private final int nextBgId;
	private final FaderOut fadeout;
	private final FaderIn fadein;
	private Fader fader;
	protected boolean finish = false;

	public StoryChangeBg(int bgId, FaderOut fadeout, FaderIn fadein) {
		this.nextBgId = bgId;
		this.fadeout = fadeout;
		this.fadein = fadein;
	}

	public int getNextBgId() {
		return nextBgId;
	}

	public FaderOut getFadeout() {
		return fadeout;
	}

	public FaderIn getFadein() {
		return fadein;
	}

	@Override
	public boolean isFinish() {
		return false;
	}

	@Override
	public boolean isWait() {
		return true;
	}

	@Override
	public void init(PanelStory story) {
		fadeout.setListener(this);
		fadein.setListener(this);
		if (story.nowBgTexture == null) {
			story.nowBgTexture = story.engine.imageManager
					.getImage(story.engine.dataMainMenu.getBackImageIds()[0]);
			fader = fadein;
		} else {
			fader = fadeout;
		}
		story.bgColor = fader.color;
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
		if (fader instanceof FaderIn) {
			fader = fadeout;
		} else if (fader instanceof FaderOut) {
			finish = true;
		}
	}
}
