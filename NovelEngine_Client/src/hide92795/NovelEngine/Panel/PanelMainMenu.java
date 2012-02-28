package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.command.CommandButton;
import hide92795.novelengine.data.DataMainMenu;
import hide92795.novelengine.fader.Fader;
import hide92795.novelengine.fader.FaderListener;
import hide92795.novelengine.fader.FaderOutBlock;
import hide92795.novelengine.fader.FaderOutAlpha;
import hide92795.novelengine.fader.FaderOutSlide;
import hide92795.novelengine.gui.Button;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;

public class PanelMainMenu extends Panel implements FaderListener {
	private Texture bgimage;
	private Random random;
	private int nowBgImageId = 0;
	private boolean changeBG;
	private Texture nextImage;
	private float alpha = 1.0f;
	private int[] seq;
	private Audio bgm;
	private boolean start = false;
	private Fader f;

	public PanelMainMenu(NovelEngine engine) {
		super(engine);
	}

	@Override
	public void init() {
		random = new Random(Sys.getTime());
		DataMainMenu data = engine.dataMainMenu;
		bgimage = getRandomBGImage(data.getBackImageIds());
		bgm = engine.soundManager.getSound(data.getBackMusic());
		Button[] _buttons = data.getButtons();
		seq = data.getButtonRenderingSeq();
		int size = _buttons.length;
		for (int i = 0; i < size; i++) {
			Button b = _buttons[i];
			b.init();
			buttons.put(b.getName(), b);
		}
		setButtonPosition();
	}

	private void setButtonPosition() {
		int size = seq.length;
		for (int i = 0; i < size; i++) {
			int name = seq[i];
			Button b = buttons.get(name);
			Rectangle rect = getRectangle(b.getPosition());
			b.setRectangle(rect);
		}
	}

	private Rectangle getRectangle(int[] position) {
		int positionX = position[0];
		int positionY = position[1];
		int idX = position[2];
		int idY = position[3];

		int x = getLocationById(idX, true) + positionX;
		int y = getLocationById(idY, false) + positionY;

		return new Rectangle(Math.max(x, 0), Math.max(y, 0), -1, -1);
	}

	private int getLocationById(int id, boolean x) {
		switch (id) {
		case 0:
			// ウィンドウ 左上
			if (x) {
				return 0;
			} else {
				return 0;
			}
		case 1:
			// ウィンドウ 右上
			if (x) {
				return engine.width;
			} else {
				return 0;
			}
		case 2:
			// ウィンドウ 左下
			if (x) {
				return 0;
			} else {
				return engine.height;
			}
		case 3:
			// ウィンドウ 右下
			if (x) {
				return engine.width;
			} else {
				return engine.height;
			}
		case 4:
			// ウィンドウ 上中央{
			if (x) {
				return engine.width / 2;
			} else {
				return 0;
			}
		case 5:
			// ウィンドウ 左中央
			if (x) {
				return 0;
			} else {
				return engine.height / 2;
			}
		case 6:
			// ウィンドウ 下中央
			if (x) {
				return engine.width / 2;
			} else {
				return engine.height;
			}
		case 7:
			// ウィンドウ 右中央
			if (x) {
				return engine.width;
			} else {
				return engine.height / 2;
			}
		default:
			Button b = buttons.get(id);
			if (x) {
				return b.getRectangle().getX();
			} else {
				return b.getRectangle().getY();
			}
		}
	}

	private Texture getRandomBGImage(int[] ids) {
		if (ids.length == 1) {
			return engine.imageManager.getImage(ids[0]);
		}
		int newId;
		do {
			int i = random.nextInt(ids.length);
			newId = ids[i];
		} while (newId == nowBgImageId);
		nowBgImageId = newId;
		return engine.imageManager.getImage(newId);
	}

	@Override
	public void render() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(bgimage != null){
			bgimage.bind();
		}
		Renderer.renderBgImage(bgimage, alpha);
		if (changeBG) {
			Renderer.renderBgImage(nextImage, 1.0f - alpha);
		}
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		renderButtons();
		if (start) {
			f.render();
		}
	}

	private void renderButtons() {
		int size = seq.length;
		for (int i = 0; i < size; i++) {
			Button b = buttons.get(seq[i]);
			if (b != null) {
				b.render();
			}

		}
	}

	@Override
	public void actionPerformed(Button b, boolean isLeft) {
		if (isLeft) {
			commandSwitch(b.getCommand());
		}
	}

	@Override
	public void onMouse(Button b) {
		commandSwitch(b.getCommandOm());
	}

	private void commandSwitch(CommandButton c) {
		switch (c.getCommand()) {
		case CommandButton.MENU_COMMAND_START:
			System.out.println("Start main story!");
			System.out.println("Start story at " + c.getId());
			engine.loadStory(c.getId());
			bgm.stop();
			for (Button b1 : buttons.values()) {
				b1.setClickable(false);
			}
			// f = new FaderOutSlide(engine, 40, 40);
			// f = new FaderOutBlock(engine, this, 12, 9, "#000000");
			f = new FaderOutAlpha(engine, this, 1.7f, "#ffffff");
			start = true;
			break;
		case CommandButton.MENU_COMMAND_LOAD:
			System.out.println("Go to load window!");
			break;
		case CommandButton.MENU_COMMAND_CHANGEBG:
			if (!changeBG) {
				changeBG = true;
				nextImage = getRandomBGImage(engine.dataMainMenu
						.getBackImageIds());
			}
			break;
		case CommandButton.MENU_COMMAND_EXIT:
			engine.exit();
			break;

		default:
			break;
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (bgimage == null) {
			bgimage = getRandomBGImage(engine.dataMainMenu.getBackImageIds());
		}
		if (!bgm.isPlaying() && !start) {
			bgm.playAsMusic(1.0f, 0.5f, true);
		}
		if (changeBG) {
			alpha -= 0.015f;
			if (alpha <= 0.0f) {
				changeBG = false;
				bgimage = nextImage;
				nextImage = null;
				alpha = 1.0f;
			}
		}
		if (start) {
			f.update(delta);
		}

	}

	@Override
	public void onFinish(Fader fader) {
		// TODO 自動生成されたメソッド・スタブ
		engine.startStory(49);
	}
}
