package hide92795.novelengine.story;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeBgColor extends Story {
	private boolean finish = false;
	private final byte target;
	private final float r;
	private final float g;
	private final float b;
	private final float a;

	public StoryChangeBgColor(byte target, int r, int g, int b, int a) {
		this.target = target;
		this.r = (float) r / 255;
		this.g = (float) g / 255;
		this.b = (float) b / 255;
		if (target == (byte) 10) {
			this.a = 1.0f;
		} else {
			this.a = a / 255;
		}
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		BackGround background = story.engine.backGroundManager.getBackGround(target);
		background.setR(r);
		background.setG(g);
		background.setB(b);
		background.setAlpha(a);
		finish = true;
	}

}
