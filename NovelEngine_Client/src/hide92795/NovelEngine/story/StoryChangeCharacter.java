package hide92795.novelengine.story;

public class StoryChangeCharacter extends Story {

	private final int characterId;
	private final int faceId;
	private final int placeId;

	public StoryChangeCharacter(int charId, int faceId, int placeId) {
		this.characterId = charId;
		this.faceId = faceId;
		this.placeId = placeId;
	}

	public final int getCharacterId() {
		return characterId;
	}

	public final int getFaceId() {
		return faceId;
	}

	public final int getPlaceId() {
		return placeId;
	}

	@Override
	public boolean isWait() {
		return true;
	}

	@Override
	public final boolean isFinish() {
		return false;
	}
}
