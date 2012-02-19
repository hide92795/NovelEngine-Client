package hide92795.novelengine.story;

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

	public int getCharacterId() {
		return characterId;
	}

	public int getVoiceId() {
		return voiceId;
	}

	public AttributedString getWords() {
		return words;
	}
	
	@Override
	public boolean isFinish() {return false;
	}
	
	@Override
	public boolean isWait() {
		return true;
	}
}
