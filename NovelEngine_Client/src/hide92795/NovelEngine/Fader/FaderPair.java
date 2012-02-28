package hide92795.novelengine.fader;

import hide92795.novelengine.panel.PanelStory;


public class FaderPair implements FaderListener {
	private final FaderOut fadeout;
	private final FaderIn fadein;
	private Fader currentFader;
	private FaderListener listener;
	private boolean skippable;

	public FaderPair(FaderOut fadeout, FaderIn fadein, boolean skippable) {
		this.fadeout = fadeout;
		this.fadein = fadein;
		this.skippable = skippable;
		currentFader = fadeout;
	}

	public void init(FaderListener listener, PanelStory story, int nextBgId) {
		this.listener = listener;
		this.fadein.setListener(this);
		this.fadeout.setListener(this);
		story.setBgTextureId(nextBgId);
	}

	public void update(int delta) {
		currentFader.update(delta);
	}

	public void render() {
		currentFader.render();
	}

	public Fader getCurrentFader() {
		return currentFader;
	}

	public void setCurrentFader(Fader currentFader) {
		this.currentFader = currentFader;
	}

	public FaderOut getFadeout() {
		return fadeout;
	}

	public FaderIn getFadein() {
		return fadein;
	}

	@Override
	public void onFinish(Fader fader) {
		if (fader instanceof FaderOut) {
			currentFader = fadein;
		} else {
			if (listener != null) {
				listener.onFinish(fader);
			}
		}
	}

	public FaderListener getListener() {
		return listener;
	}

	public void setListener(FaderListener listener) {
		this.listener = listener;
	}

}
