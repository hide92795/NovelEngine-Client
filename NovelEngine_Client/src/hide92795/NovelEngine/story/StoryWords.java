package hide92795.novelengine.story;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.data.DataGui;
import hide92795.novelengine.manager.ImageManager;
import hide92795.novelengine.manager.WordsManager;
import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

/**
 * セリフ及びメッセージの表示を行います。
 * 
 * @author hide92795
 */
public class StoryWords extends Story {
	/**
	 * セリフの話し主のキャラクターID
	 */
	private final int characterId;

	/**
	 * セリフに音声がある場合、音声ID<br>
	 * 音声がない場合は0
	 */
	private final int voiceId;

	/**
	 * 表示させる文字列
	 */
	private final String words;

	/**
	 * すべての処理が終わっているかどうか
	 * 
	 * @see #isFinish()
	 */
	private boolean finish;

	/**
	 * ストーリー内でのID
	 */
	private int id;

	/**
	 * 文字列の画像IDの配列
	 */
	private int[] wordsImages;

	/**
	 * 現在流して表示している文字列の行
	 */
	private int line;

	private int progression;
	private Texture wordsTexture;
	private float texturePerMax;
	private boolean showed = false;
	private float perTexWid;
	private int c;

	private Texture nameTexture;

	public StoryWords(int characterId, int voiceId, String words, int id) {
		this.characterId = characterId;
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
		finish = false;
		showed = false;
		line = 0;
		WordsManager wordsManager = story.engine.wordsManager;
		ImageManager imageManager = story.engine.imageManager;

		// セリフイメージ取得
		wordsImages = wordsManager.getWordsTextureIds(story.getChapterId(), id);
		wordsTexture = imageManager.getImage(wordsImages[0]);

		// 表示名イメージ取得
		int[] ids = wordsManager.getWordsTextureIds(0, characterId);
		if (ids != null) {
			nameTexture = imageManager.getImage(ids[0]);
		}

		progression = 0;
		line = 0;
		texturePerMax = (float) wordsTexture.getImageWidth()
				/ wordsTexture.getTextureWidth();
	}

	public void skip() {
		if (!showed) {
			showed = true;
		} else {
			finish = true;
		}
	}

	@Override
	public void leftClick(PanelStory story, int x, int y) {
		skip();
	}

	@Override
	public void keyPressed(PanelStory story, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip();
		}
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip();
		}
		if (!showed) {
			progression += 10;
			perTexWid = (float) progression / wordsTexture.getTextureWidth();
			if (perTexWid >= texturePerMax) {
				if (line >= wordsImages.length - 1) {
					showed = true;
					return;
				}
				line++;
				wordsTexture = story.engine.imageManager
						.getImage(wordsImages[line]);
				progression = 0;
				texturePerMax = (float) wordsTexture.getImageWidth()
						/ wordsTexture.getTextureWidth();
				perTexWid = 0.0f;
			}
		}

	}

	@Override
	public void render(NovelEngine engine) {
		int ypos = 0;
		DataGui data = engine.dataGui;
		// 名前表示
		if (nameTexture != null) {
			int x = data.getBoxXpos() + data.getBoxNameXpos();
			int y = data.getBoxYpos() + data.getBoxNameYpos();
			int x1 = x + nameTexture.getTextureWidth();
			int y1 = y + nameTexture.getTextureHeight();
			Renderer.renderImage(nameTexture, x, y, x1, y1);
		}

		if (showed) {
			int cursorYpos = 0;
			int cursorXpos = 0;
			// すべてのセリフ＆カーソル表示
			for (int wordsId : wordsImages) {
				Texture t = engine.imageManager.getImage(wordsId);
				if (t != null) {
					int x = data.getBoxXpos() + data.getBoxWordsXpos();
					int y = data.getBoxYpos() + data.getBoxWordsYpos() + ypos;
					int x1 = x + t.getTextureWidth();
					int y1 = y + t.getTextureHeight();
					Renderer.renderImage(t, x, y, x1, y1);
					cursorXpos = t.getImageWidth();
					cursorYpos = ypos + t.getImageHeight();
					ypos += t.getImageHeight() + 5;
				}
			}

			// カーソル
			engine.imageManager.getImage(data.getWaitCursorImageId()).bind();
			float[] a = data.getWaitCursorList()[c];
			int size = data.getWaitCursorSize();
			cursorYpos -= size;
			int x = data.getBoxXpos() + data.getBoxWordsXpos() + cursorXpos;
			int y = data.getBoxYpos() + data.getBoxWordsYpos() + cursorYpos;
			glEnable(GL_TEXTURE_2D);
			glBegin(GL_QUADS);
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			{
				glTexCoord2f(a[0], a[1]);
				glVertex2f(x, y);

				glTexCoord2f(a[2], a[1]);
				glVertex2f(x + size, y);

				glTexCoord2f(a[2], a[3]);
				glVertex2f(x + size, y + size);

				glTexCoord2f(a[0], a[3]);
				glVertex2f(x, y + size);
			}
			glEnd();
			c++;
			if (c == data.getWaitCursorCount()) {
				c = 0;
			}
		} else {
			// 途中のセリフまで表示
			for (int i = 0; i <= line; i++) {
				Texture t = engine.imageManager.getImage(wordsImages[i]);
				if (t != null) {
					int x = data.getBoxXpos() + data.getBoxWordsXpos();
					int y = data.getBoxYpos() + data.getBoxWordsYpos() + ypos;
					int y1 = y + t.getTextureHeight();
					if (i == line) {
						// 表示途中
						float x1 = x + t.getTextureWidth() * perTexWid;
						t.bind();
						glEnable(GL_TEXTURE_2D);
						glBegin(GL_QUADS);
						glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						{
							glTexCoord2f(0, 0);
							glVertex2f(x, y);
							glTexCoord2f(perTexWid, 0);
							glVertex2f(x1, y);
							glTexCoord2f(perTexWid, 1);
							glVertex2f(x1, y1);
							glTexCoord2f(0, 1);
							glVertex2f(x, y1);
						}
						glEnd();
					} else {
						int x1 = x + t.getTextureWidth();
						Renderer.renderImage(t, x, y, x1, y1);
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
