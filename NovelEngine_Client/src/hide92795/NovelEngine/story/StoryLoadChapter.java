package hide92795.novelengine.story;

public class StoryLoadChapter extends Story {
	private final int loadChapterId;
	
	public StoryLoadChapter(int chapterId) {
		this.loadChapterId = chapterId;
	}

	public final int getLoadChapterId() {
		return loadChapterId;
	}

	@Override
	public final boolean isFinish() {
		return true;
	}

}
