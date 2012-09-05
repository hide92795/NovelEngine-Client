package hide92795.novelengine.background.effect;

import hide92795.novelengine.background.Effect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryEffect;

public class TransitionSlide extends Effect {
	public static final int ID = "slide".hashCode();
	public static final Class<?>[] CONSTRUCTOR = { byte.class, int.class, int.class };
	
	
	public TransitionSlide(byte target) {
		super(target);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void init(PanelStory story, byte target) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(StoryEffect story, int delta) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void render(NovelEngine engine) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void skip() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
