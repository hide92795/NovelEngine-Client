package hide92795.NovelEngine.Client;

import hide92795.NovelEngine.DataLoader;
import hide92795.NovelEngine.QueueHandler;
import hide92795.NovelEngine.ImageManager;
import hide92795.NovelEngine.SoundManager;
import hide92795.NovelEngine.Gui.Button;
import hide92795.NovelEngine.Panel.Panel;
import hide92795.NovelEngine.Panel.PanelFadeLogo;
import hide92795.NovelEngine.SettingData.DataBasic;
import hide92795.NovelEngine.SettingData.DataMainMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class NovelEngine {

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	public DataBasic dataBasic;
	private Panel currentPanel;

	private boolean leftClick = false;

	private boolean rightClick = false;

	public DataMainMenu dataMainMenu;

	public static NovelEngine theEngine;

	public ImageManager imageManager;
	
	public SoundManager soundManager;

	public QueueHandler queue;

	private boolean running;

	public NovelEngine() {

		imageManager = new ImageManager();
		soundManager = new SoundManager();
		queue = new QueueHandler();
		theEngine = this;
		initResource();
	}

	public void start() {
		initGL(dataBasic.getWidth(), dataBasic.getHeight());
		lastFPS = getTime();
		loadMenuData();
		setCurrentPanel(new PanelFadeLogo(this));
		running = true;
		while (!Display.isCloseRequested() && running) {
			updateFPS();
			pollInput();
			update();
			render();
			queue.execute();
			
			Display.update();
			Display.sync(60); // cap fps to 60fps
		}

		Display.destroy();
		AL.destroy();
	}
	
	public void exit(){
		running = false;
	}

	private void loadMenuData() {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					dataMainMenu = (DataMainMenu) DataLoader.loadData(null,
							"menu.dat", DataMainMenu.class);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void update() {
		if (currentPanel != null) {
			currentPanel.update();
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (currentPanel != null) {
			currentPanel.render();
		} else {
			glClearColor(1.0f, 1.0f, 1.0f, 0.5f);
		}
	}

	public void setCurrentPanel(Panel panel) {

		this.currentPanel = panel;
		if (currentPanel != null) {
			currentPanel.init();
		}

	}

	private void initResource() {
		Exception ex = null;
		try {
			dataBasic = (DataBasic) DataLoader.loadData(null, "gameinfo.dat",
					DataBasic.class);
		} catch (Exception e) {
			e.printStackTrace();
			ex = e;
		}

	}

	public void pollInput() {
		// 左クリック
		if (Mouse.isButtonDown(0)) {
			if (!leftClick) {
				int x = Mouse.getX();
				int y = Mouse.getY();
				leftClick = true;
				if (currentPanel != null) {
					currentPanel.leftClick(x, y);
				}
			}
		} else {
			leftClick = false;
		}

		// 右クリック
		if (Mouse.isButtonDown(1)) {
			if (!rightClick) {
				int x = Mouse.getX();
				int y = Mouse.getY();
				rightClick = true;
				if (currentPanel != null) {
					currentPanel.rightClick(x, y);
				}
			}
		} else {
			rightClick = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("SPACE KEY IS DOWN");
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Pressed");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Pressed");
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("S Key Released");
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Released");
				}
			}
		}
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		glEnable(GL_TEXTURE_2D);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glViewport(0, 0, width, height);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void main(String[] argv) {
		NovelEngine timerExample = new NovelEngine();
		timerExample.start();
	}

	public static File getCurrentDir() {
		URI uri = null;
		try {
			uri = NovelEngine.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new File(uri).getParentFile();
	}

	public void actionPerformed(Button button, boolean isLeft) {
		if (currentPanel != null) {
			currentPanel.actionPerformed(button, isLeft);
		}
	}

	public void onMouse(Button button) {
		if (currentPanel != null) {
			currentPanel.onMouse(button);
		}
	}
}
