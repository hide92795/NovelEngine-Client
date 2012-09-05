package hide92795.novelengine.story;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeBg extends Story {
	private final int nextBgId;
	private final byte target;
	private final int xPos;
	private final int yPos;
	private final int magnification;
	private final int delay;
	private boolean finish = false;
	private int elapsedTime;

	public StoryChangeBg(int bgId, byte target, int xPos, int yPos,
			int magnification, int delay) {
		this.nextBgId = bgId;
		this.target = target;
		this.xPos = xPos;
		this.yPos = yPos;
		this.magnification = magnification;
		this.delay = delay;
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
		if (elapsedTime >= delay && !finish) {
			// 閭梧勹螟画峩
			BackGround background = story.engine.backGroundManager
					.getBackGround(target);
			background.setImageId(nextBgId);
			background.setxPos(xPos);
			background.setyPos(yPos);
			background.setMagnificartion(magnification);

			finish = true;
		}
		elapsedTime += delta;
	}
}
