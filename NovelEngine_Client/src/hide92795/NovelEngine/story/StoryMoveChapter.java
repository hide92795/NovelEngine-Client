package hide92795.novelengine.story;

public class StoryMoveChapter extends Story {
	private final int chapterId;

	public StoryMoveChapter(int chapterId) {
		this.chapterId = chapterId;
	}

	public final int getChapterId() {
		return chapterId;
	}

	@Override
	public final boolean isFinish() {
		return false;
	}

}
