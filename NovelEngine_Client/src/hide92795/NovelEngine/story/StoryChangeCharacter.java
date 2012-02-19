package hide92795.NovelEngine.story;

public class StoryChangeCharacter extends Story {

	private final int characterId;
	private final int faceId;
	private final int placeId;

	public StoryChangeCharacter(int charId, int faceId, int placeId) {
		this.characterId = charId;
		this.faceId = faceId;
		this.placeId = placeId;
	}

	public int getCharacterId() {
		return characterId;
	}

	public int getFaceId() {
		return faceId;
	}

	public int getPlaceId() {
		return placeId;
	}

	@Override
	public boolean isWait() {
		return true;
	}
}
