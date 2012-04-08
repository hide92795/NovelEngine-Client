package hide92795.novelengine.story;

public class StoryShowCG extends Story {
	private final int cgid;
	private final boolean show;

	public StoryShowCG(int cgId, boolean show) {
		this.cgid = cgId;
		this.show = show;
	}

	public final int getCgid() {
		return cgid;
	}

	public final boolean isShow() {
		return show;
	}

	@Override
	public boolean isFinish() {
		return false;
	}
	
	@Override
	public boolean isWait() {
		return false;
	}
}
