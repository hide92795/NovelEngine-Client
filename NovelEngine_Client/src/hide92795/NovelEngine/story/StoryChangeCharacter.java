package hide92795.novelengine.story;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;

import hide92795.novelengine.CharacterManager;
import hide92795.novelengine.character.EntryCharacter;
import hide92795.novelengine.character.effect.CharacterEffect;
import hide92795.novelengine.data.DataCharacter;
import hide92795.novelengine.panel.PanelStory;

public class StoryChangeCharacter extends Story {

	private boolean finish;

	private LinkedList<CharacterChanger> changeList;

	public StoryChangeCharacter() {
		this.changeList = new LinkedList<CharacterChanger>();
	}

	@Override
	public final boolean isWait() {
		return true;
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
		CharacterManager cm = story.engine.characterManager;
		DataCharacter data = story.engine.dataCharacter;
		for (CharacterChanger cc : changeList) {
			EntryCharacter ec = cm.get(cc.placeId);
			ec.setSecondaryCharacter(data.getCharacter(cc.characterId));
			ec.setSecondaryFaceID(cc.faceId);
			cc.effect.setEntry(ec);
		}
	}

	private void skip(PanelStory story) {
		CharacterManager cm = story.engine.characterManager;
		for (CharacterChanger cc : changeList) {
			EntryCharacter ec = cm.get(cc.placeId);
			ec.finish();
		}
	}

	@Override
	public void keyPressed(PanelStory story, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(story);
		}
	}

	@Override
	public void leftClick(PanelStory story, int x, int y) {
		skip(story);
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(story);
		}
		boolean finish = true;
		for (CharacterChanger cc : changeList) {
			cc.effect.update(delta);
			if (!cc.effect.isFinish()) {
				finish = false;
			}
		}
		this.finish = finish;
	}

	public void addChange(int num, int characterId, int faceId, int placeId,
			CharacterEffect effect) {
		CharacterChanger cc = new CharacterChanger(characterId, faceId,
				placeId, effect);
		changeList.add(cc);
	}

	class CharacterChanger {
		private final int placeId;
		private final int characterId;
		private final int faceId;
		private final CharacterEffect effect;

		public CharacterChanger(int characterId, int faceId, int placeId,
				CharacterEffect effect) {
			this.characterId = characterId;
			this.faceId = faceId;
			this.placeId = placeId;
			this.effect = effect;
		}
	}
}
