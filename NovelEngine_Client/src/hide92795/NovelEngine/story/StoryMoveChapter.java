package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

public class StoryMoveChapter extends Story {
	private final int chapterId;

	public StoryMoveChapter(int chapterId) {
		this.chapterId = chapterId;
	}

	public final int getChapterId() {
		return chapterId;
	}
	
	@Override
	public void update(PanelStory story, int delta) {
		story.engine.startStory(chapterId);
	}

	@Override
	public final boolean isFinish() {
		return false;
	}
	
	@Override
	public boolean isWait() {
		return true;
	}

}
