package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;

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
	public final boolean isWait() {
		return true;
	}

	@Override
	public final boolean isFinish() {
		return finish;
	}

	@Override
	public void init(PanelStory story) {
		int size = characters.length;
		for (int i = 0; i < size; i++) {
			CharacterChanger cc = characters[i];
			Character c = story.characters.get(cc.placeId);
			if (c == null) {
				cc.beforeCharacterId = 0;
				cc.beforeFaceId = 0;
			} else {
				cc.beforeCharacterId = c.getCharacterId();
				cc.beforeFaceId = c.getCurrentFace();
			}
			cc.init(story);
			story.characters.put(cc.placeId, null);
		}

	}

	private void skip(PanelStory story) {
		int size = characters.length;
		for (int i = 0; i < size; i++) {
			CharacterChanger cc = characters[i];
			cc.finish(story);
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
		int size = characters.length;
		boolean finish = true;
		for (int i = 0; i < size; i++) {
			CharacterChanger cc = characters[i];
			cc.update(story, delta);
			if (!cc.finish) {
				finish = false;
			}
		}
		this.finish = finish;
	}

	@Override
	public void render(NovelEngine engine) {
		int size = characters.length;
		for (int i = 0; i < size; i++) {
			CharacterChanger cc = characters[i];
			cc.render(engine);
		}
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
		private int beforeCharacterId;
		private int beforeFaceId;
		private int elapsedTime;
		private int totalTime = 400;
		private double alpha;
		private Character before_c;
		private Character new_c;
		private int[] pos;
		private boolean finish;

		public CharacterChanger(int characterId, int faceId, int placeId,
				boolean fade) {
			this.characterId = characterId;
			this.faceId = faceId;
			this.placeId = placeId;
			this.fade = fade;
		}

		private void init(PanelStory story) {
			DataCharacter data = story.engine.dataCharacter;
			pos = story.engine.dataGui.getPortraitPosition(placeId);
			before_c = data.getCharacter(beforeCharacterId);
			new_c = data.getCharacter(characterId);
			if (!fade) {
				// フェードなし
				finish(story);
			}
		}

		private void update(PanelStory story, int delta) {
			if (!finish) {
				elapsedTime += delta;
				alpha = (double) elapsedTime / totalTime;
				if (alpha >= 1.0d) {
					finish(story);
				}
			}

		}

		private void render(NovelEngine engine) {
			if (before_c != null) {
				before_c.setCurrentFace(beforeFaceId);
				before_c.render(engine, pos[0], pos[1], 1.0d - alpha);
			}
			if (new_c != null) {
				new_c.setCurrentFace(faceId);
				new_c.render(engine, pos[0], pos[1], alpha);
			}
		}

		private void finish(PanelStory story) {
			new_c.setCurrentFace(faceId);
			story.characters.put(placeId, new_c);
			finish = true;
		}
	}
}
