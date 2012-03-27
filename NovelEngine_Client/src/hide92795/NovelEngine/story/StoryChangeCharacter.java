package hide92795.novelengine.story;

import hide92795.novelengine.Character;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.data.DataCharacter;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeCharacter extends Story {
	private final CharacterChanger[] characters;

	private boolean finish;

	public StoryChangeCharacter(int number) {
		this.characters = new CharacterChanger[number];
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
		int size = characters.length;
		DataCharacter data = story.engine.dataCharacter;
		for (int i = 0; i < size; i++) {
			CharacterChanger cc = characters[i];
			Character c = data.getCharacter(cc.characterId);
			if (c != null) {
				c.setCurrentFace(cc.faceId);
			}
			story.characters.put(cc.placeId, c);
		}

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

	public void addChange(int num, int characterId, int faceId, int placeId,
			boolean fade) {
		CharacterChanger cc = new CharacterChanger(characterId, faceId,
				placeId, fade);
		characters[num] = cc;
	}

	class CharacterChanger {
		private final int characterId;
		private final int faceId;
		private final int placeId;
		private final boolean fade;

		public CharacterChanger(int characterId, int faceId, int placeId,
				boolean fade) {
			this.characterId = characterId;
			this.faceId = faceId;
			this.placeId = placeId;
			this.fade = fade;
		}
	}
}
