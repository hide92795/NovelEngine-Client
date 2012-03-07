package hide92795.novelengine.story;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import de.matthiasmann.twl.ColumnLayout.Panel;

public class StoryWords extends Story {
	private final int characterId;
	private final int voiceId;
	private final String words;
	private Texture tex;
	private boolean finish = false;
	private int id;
	private int[] wordsImages;

	public StoryWords(int charId, int voiceId, String words, int id) {
		this.characterId = charId;
		this.voiceId = voiceId;
		this.words = words;
		this.id = id;
	}

	public final int getCharacterId() {
		return characterId;
	}

	public final int getVoiceId() {
		return voiceId;
	}

	public final String getWords() {
		return words;
	}

	@Override
	public void init(PanelStory story) {
		wordsImages = story.engine.wordsManager.getWordsTextureIds(
				story.getChapterId(), id);

	}

	@Override
	public void leftClick(int x, int y) {
		finish = true;
	}

	@Override
	public void update(PanelStory panelStory, int delta) {
	}

	@Override
	public void render() {
		int ypos = 0;
		for (int texId : wordsImages) {
			Texture tex = NovelEngine.theEngine.imageManager.getImage(texId);
			if (tex != null) {
				Renderer.renderImage(tex, 148, 445 + ypos,
						148 + tex.getTextureWidth(),
						445 + tex.getTextureHeight() + ypos);
				ypos += tex.getImageHeight() + 5;
			}
		}

		
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public boolean isWait() {
		return true;
	}
}
