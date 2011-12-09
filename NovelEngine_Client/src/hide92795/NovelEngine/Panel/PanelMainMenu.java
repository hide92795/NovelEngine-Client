package hide92795.NovelEngine.Panel;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.NovelEngine.Client.NovelEngine;
import hide92795.NovelEngine.Command.CommandButton;
import hide92795.NovelEngine.Gui.Button;
import hide92795.NovelEngine.SettingData.DataMainMenu;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;

public class PanelMainMenu extends Panel {
	private Texture bgimage;
	private Random random;
	private int nowBgImageId = 0;
	private boolean changeBG;
	private Texture nextImage;
	private float alpha = 1.0f;
	private int[] seq;
	private Audio bgm;

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
				return Display.getWidth();
			} else {
				return 0;
			}
		case 2:
			// ウィンドウ 左下
			if (x) {
				return 0;
			} else {
				return Display.getHeight();
			}
		case 3:
			// ウィンドウ 右下
			if (x) {
				return Display.getWidth();
			} else {
				return Display.getHeight();
			}
		case 4:
			// ウィンドウ 上中央{
			if (x) {
				return Display.getWidth() / 2;
			} else {
				return 0;
			}
		case 5:
			// ウィンドウ 左中央
			if (x) {
				return 0;
			} else {
				return Display.getHeight() / 2;
			}
		case 6:
			// ウィンドウ 下中央
			if (x) {
				return Display.getWidth() / 2;
			} else {
				return Display.getHeight();
			}
		case 7:
			// ウィンドウ 右中央
			if (x) {
				return Display.getWidth();
			} else {
				return Display.getHeight() / 2;
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
		bgimage.bind();
		glBegin(GL_QUADS);
		{
			glColor4f(1.0f, 1.0f, 1.0f, alpha);
			renderBgImage(bgimage);

		}
		glEnd();
		if (changeBG) {
			nextImage.bind();
			glBegin(GL_QUADS);
			{
				glColor4f(1.0f, 1.0f, 1.0f, 1.0f - alpha);
				renderBgImage(nextImage);
			}
			glEnd();
		}
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		renderButtons();
	}

	private void renderButtons() {
		int size = seq.length;
		for (int i = 0; i < size; i++) {
			Button b = buttons.get(seq[i]);
			b.render();
		}
	}

	private void renderBgImage(Texture image) {
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(image.getTextureWidth(), 0);
		glTexCoord2f(1, 1);
		glVertex2f(image.getTextureWidth(), image.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(0, image.getTextureHeight());
	}

	@Override
	public void actionPerformed(Button b, boolean isLeft) {
		if(isLeft){
			CommandButton c = b.getCommand();
			switch (c.getCommand()) {
			case CommandButton.MENU_COMMAND_START:
				System.out.println("Start main story!");
				System.out.println("Start story at "+ c.getId());
				break;
			case CommandButton.MENU_COMMAND_LOAD:
				System.out.println("Go to load window!");
				break;
			case CommandButton.MENU_COMMAND_EXIT:
				engine.exit();
				break;

			default:
				break;
			}
		}
		
	}

	@Override
	public void onMouse(Button b) {
		CommandButton c = b.getCommandOm();
		switch (c.getCommand()) {
		case CommandButton.MENU_COMMAND_START:
			System.out.println("Start main story!");
			System.out.println("Start story at "+ c.getId());
			break;
		case CommandButton.MENU_COMMAND_CHANGEBG:
			if (!changeBG) {
				changeBG = true;
				nextImage = getRandomBGImage(engine.dataMainMenu
						.getBackImageIds());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void update() {
		super.update();
		if(!bgm.isPlaying()){
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
	}
}
