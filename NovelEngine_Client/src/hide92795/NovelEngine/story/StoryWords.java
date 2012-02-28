package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

import java.text.AttributedString;

public class StoryWords extends Story {
	private final int characterId;
	private final int voiceId;
	private final AttributedString words;

	public StoryWords(int charId, int voiceId, AttributedString words) {
		this.characterId = charId;
		this.voiceId = voiceId;
		this.words = words;
	}

	public final int getCharacterId() {
		return characterId;
	}

	public final int getVoiceId() {
		return voiceId;
	}

	public final AttributedString getWords() {
		return words;
	}
	
	@Override
	public void init(PanelStory story) {
		story.engine.addPanel(null);
	}
	
	@Override
	public void update(PanelStory panelStory, int delta) {
	}
	
	@Override
	public void render() {

	}
	
	@Override
	public boolean isFinish() {return false;
	}
	
	@Override
	public boolean isWait() {
		return true;
	}
}
