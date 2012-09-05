package hide92795.novelengine.background;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryEffect;

public abstract class Effect {
	public final byte target;
	public Effect(byte target) {
		this.target = target;
	}
	public abstract void init(PanelStory story, byte target);
	public abstract void update(StoryEffect story, int delta);
	public abstract void render(NovelEngine engine);
	public abstract void skip();
}
