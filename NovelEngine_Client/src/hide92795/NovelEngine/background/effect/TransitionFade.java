package hide92795.novelengine.background.effect;

import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.background.Effect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryEffect;

public class TransitionFade extends Effect {
	public static final int ID = "fade".hashCode();
	public static final Class<?>[] CONSTRUCTOR = { byte.class, int.class, int.class };

	private int firstAlpha;
	private int endAlpha;
	private final int totalTime;
	private int elapsedTime;
	private BackGround background;
	private boolean finish;
	private int changeAlpha;

	public TransitionFade(byte target, int endAlpha, int elapsedTime) {
		super(target);
		this.endAlpha = endAlpha;
		this.totalTime = elapsedTime;
	}

	@Override
	public void init(PanelStory story, byte target) {
		finish = false;
		elapsedTime = 0;
		background = story.engine.backGroundManager.getBackGround(target);
		float now = background.getAlpha();
		firstAlpha = (int) (now * 255);
		changeAlpha = endAlpha - firstAlpha;
	}

	@Override
	public void update(StoryEffect story, int delta) {
		if (finish) {
			story.effectFinish();
		} else {
			float per = (float) elapsedTime / totalTime;
			if (elapsedTime >= totalTime) {
				finish = true;
				per = 1.0f;
			}
			int fluctuation = (int) (changeAlpha * per);
			int alpha_255 = firstAlpha + fluctuation;
			background.setAlpha((float) alpha_255 / 255);
			elapsedTime += delta;
		}

	}

	@Override
	public void render(NovelEngine engine) {
	}

	@Override
	public void skip() {
		background.setAlpha(endAlpha / 255);
		finish = true;
	}

}
