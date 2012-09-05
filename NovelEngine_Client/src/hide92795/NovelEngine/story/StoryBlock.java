package hide92795.novelengine.story;

public class StoryBlock extends Story {
	private final boolean startBlock;
	public StoryBlock(boolean start) {
		this.startBlock = start;
	}


	@Override
	public final boolean isFinish() {
		return true;
	}


	public final boolean isStartBlock() {
		return startBlock;
	}
}
