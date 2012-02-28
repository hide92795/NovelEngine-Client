package hide92795.novelengine.fader;

import hide92795.novelengine.panel.PanelStory;

public class FaderPairAlpha extends FaderPair {

	private boolean via;

	public FaderPairAlpha(FaderOutAlpha fadeout, FaderInAlpha fadein,
			boolean skippable, boolean af_via) {
		super(fadeout, fadein, skippable);
		this.getFadeout().setListener(this);
		this.getFadein().setListener(this);
		this.via = af_via;
	}

	@Override
	public void init(FaderListener listener, PanelStory story, int nextBgId) {
		setListener(listener);
		getFadein().setListener(this);
		getFadeout().setListener(this);
		if (via) {
			// 背景色を経由
			story.setBgTextureId(nextBgId);
		} else {
			((FaderOutAlpha) getFadeout()).setTextureId(story.getBgTextureId());
			((FaderInAlpha) getFadein()).setTextureId(nextBgId);
			story.setBgTextureId(0);
		}
	}

	@Override
	public void update(int delta) {
		if (via) {
			// 背景色を経由
			super.update(delta);
		} else {
			getFadein().update(delta);
			getFadeout().update(delta);
		}

	}

	@Override
	public void render() {
		if (via) {
			// 背景色を経由
			super.render();
		} else {
			getFadein().render();
			getFadeout().render();
		}
	}

	public void setVia(boolean via) {
		this.via = via;
	}

}
