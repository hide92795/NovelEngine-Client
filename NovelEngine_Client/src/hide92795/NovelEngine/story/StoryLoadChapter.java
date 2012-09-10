package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

public class StoryLoadChapter extends Story {
	protected final int loadChapterId;
	
	public StoryLoadChapter(int chapterId) {
		this.loadChapterId = chapterId;
	}
	
	@Override
	public void update(PanelStory story, int delta) {
		//story.engine.loadStory(loadChapterId);
	}

	@Override
	public final boolean isFinish() {
		return true;
	}
}
