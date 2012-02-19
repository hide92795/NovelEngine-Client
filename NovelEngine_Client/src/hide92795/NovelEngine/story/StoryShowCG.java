package hide92795.novelengine.story;

public class StoryShowCG extends Story {
	private final int cgid;
	private final boolean show;

	public StoryShowCG(int cgId, boolean show) {
		this.cgid = cgId;
		this.show = show;
	}

	public int getCgid() {
		return cgid;
	}

	public boolean isShow() {
		return show;
	}
}
