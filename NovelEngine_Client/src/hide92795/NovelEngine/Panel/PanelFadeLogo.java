package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.glClearColor;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

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
	public void render(NovelEngine engine) {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		int x1 = engine.width / 2 - texture.getImageWidth() / 2;
		int y1 = engine.height / 2 - texture.getImageHeight() / 2;
		Renderer.renderImage(texture, alpha, x1, y1,
				x1 + texture.getTextureWidth(), y1 + texture.getTextureHeight());
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
