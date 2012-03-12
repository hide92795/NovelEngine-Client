package hide92795.novelengine.story;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.*;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class StoryWords extends Story {
	private final int characterId;
	private final int voiceId;
	private final String words;
	private boolean finish = false;
	private int id;
	private int[] wordsImages;

	private int page;
	private int progression;
	private Texture wordsTexture;
	private float texturePerMax;
	private boolean showed = false;
	private float perTexWid;

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
		wordsTexture = story.engine.imageManager.getImage(wordsImages[0]);
		progression = 0;
		page = 0;
		texturePerMax = (float) wordsTexture.getImageWidth()
				/ wordsTexture.getTextureWidth();
	}

	@Override
	public void leftClick(int x, int y) {
		if (!showed) {
			showed = true;
		} else {
			finish = true;
		}
	}
	
	@Override
	public void keyPressed(int eventKey) {
		if(eventKey == Keyboard.KEY_RETURN){
			if (!showed) {
				showed = true;
			} else {
				finish = true;
			}
		}
	}

	@Override
	public void update(PanelStory story, int delta) {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_GRAVE) {
					System.out.println(Keyboard.getEventKey());
					System.out.println(Keyboard.getEventCharacter());
					System.out.println("A Key Pressed");
				}
			}
		}
		while (Keyboard.next()) {
			System.out.println("StoryWords.update()");
		}
		if (!showed) {
			progression += 10;
			perTexWid = (float) progression / wordsTexture.getTextureWidth();
			if (perTexWid >= texturePerMax) {
				if (page >= wordsImages.length - 1) {
					showed = true;
					return;
				}
				page++;
				wordsTexture = story.engine.imageManager
						.getImage(wordsImages[page]);
				progression = 0;
				texturePerMax = (float) wordsTexture.getImageWidth()
						/ wordsTexture.getTextureWidth();
				perTexWid = 0.0f;
			}
		}

	}

	@Override
	public void render() {
		int ypos = 0;
		if (showed) {
			// すべてのセリフ＆カーソル表示
			for (int wordsId : wordsImages) {
				Texture t = NovelEngine.theEngine.imageManager
						.getImage(wordsId);
				if (t != null) {
					Renderer.renderImage(t, 148, 445 + ypos,
							148 + t.getTextureWidth(),
							445 + t.getTextureHeight() + ypos);
					ypos += t.getImageHeight() + 5;
				}

			}
		} else {
			// 途中のセリフまで表示
			for (int i = 0; i <= page; i++) {
				Texture t = NovelEngine.theEngine.imageManager
						.getImage(wordsImages[i]);
				if (t != null) {
					if (i == page) {
						// 表示途中
						float x1 = 148 + t.getTextureWidth() * perTexWid;
						int y1 = 445 + t.getTextureHeight() + ypos;
						t.bind();
						glEnable(GL_TEXTURE_2D);
						glBegin(GL_QUADS);
						glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						{
							glTexCoord2f(0, 0);
							glVertex2f(148, 445 + ypos);
							glTexCoord2f(perTexWid, 0);
							glVertex2f(x1, 445 + ypos);
							glTexCoord2f(perTexWid, 1);
							glVertex2f(x1, y1);
							glTexCoord2f(0, 1);
							glVertex2f(148, y1);
						}
						glEnd();
					} else {
						Renderer.renderImage(t, 148, 445 + ypos,
								148 + t.getTextureWidth(),
								445 + t.getTextureHeight() + ypos);
					}
					ypos += t.getImageHeight() + 5;
				}
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
