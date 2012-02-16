package hide92795.NovelEngine.panel;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.NovelEngine.client.NovelEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;

public class PanelFadeLogo extends Panel {
	private Texture texture;
	private float alpha = 0.0f;
	private boolean show = true;
	private Audio player;

	public PanelFadeLogo(NovelEngine engine) {
		super(engine);
	}

	@Override
	public void init() {
		try {
			byte[] image = engine.dataBasic.getLogo();
			texture = engine.imageManager.getImage("_Logo".hashCode(), image);
			byte[] sound = engine.dataBasic.getLogoSound()[0];
			ByteArrayInputStream bis2 = new ByteArrayInputStream(sound);
			player = AudioLoader.getAudio("WAV", bis2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		texture.bind();
		glBegin(GL_QUADS);
		{
			glColor4f(1.0f, 1.0f, 1.0f, alpha);
			int x1, y1;
			x1 = engine.width / 2 - texture.getImageWidth() / 2;
			y1 = engine.height / 2 - texture.getImageHeight() / 2;
			glTexCoord2f(0, 0);
			glVertex2f(x1, y1);
			glTexCoord2f(1, 0);
			glVertex2f(x1 + texture.getTextureWidth(), y1);
			glTexCoord2f(1, 1);
			glVertex2f(x1 + texture.getTextureWidth(),
					y1 + texture.getTextureHeight());
			glTexCoord2f(0, 1);
			glVertex2f(x1, y1 + texture.getTextureHeight());
		}
		glEnd();
	}
	
	@Override
	public void update(int delta) {
		if (!player.isPlaying()) {
			if (show) {
				alpha += 0.015f;
			} else {
				alpha -= 0.015f;
			}
			if (alpha >= 1.0f) {
				show = false;
				player.playAsSoundEffect(1.0f, 1.0f, false);
			}
			if (!show && alpha <= 0.0f) {
				engine.setCurrentPanel(new PanelMainMenu(engine));
			}
		}
	}

	@Override
	public void leftClick(int x, int y) {
		if (player.isPlaying()) {
			player.stop();
			return;
		}
		if (show) {
			alpha = 0.99f;
		} else {
			alpha = 0.01f;
		}
	}
}
