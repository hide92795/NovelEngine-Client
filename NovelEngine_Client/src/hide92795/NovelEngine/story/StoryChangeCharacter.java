package hide92795.novelengine.story;

import hide92795.novelengine.Character;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeCharacter extends Story {

	private final int characterId;
	private final int faceId;
	private final int placeId;
	private boolean finish;

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
		return true;
	}

	@Override
	public void init(PanelStory story) {
		Character c = story.engine.dataCharacter.getCharacter(characterId);
		if (c != null) {
			c.setCurrentFace(faceId);
		}
		story.characters.put(placeId, c);
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
		// TODO 自動生成されたメソッド・スタブ
		super.update(panelStory, delta);
	}

	@Override
	public void render(NovelEngine engine) {
		// TODO 自動生成されたメソッド・スタブ
		super.render(engine);
	}
}
