package hide92795.novelengine.story;

public class StoryScene extends Story {
	private final int sceneId;

	public StoryScene(int sceneId) {
		this.sceneId = sceneId;
	}

	public final int getSceneId() {
		return sceneId;
	}

	@Override
	public final boolean isFinish() {
		return true;
	}

	@Override
	public boolean isWait() {
		return false;
	}

}
