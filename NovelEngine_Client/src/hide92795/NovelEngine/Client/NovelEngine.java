package hide92795.NovelEngine.client;

import hide92795.NovelEngine.DataLoader;
import hide92795.NovelEngine.QueueHandler;
import hide92795.NovelEngine.ImageManager;
import hide92795.NovelEngine.SoundManager;
import hide92795.NovelEngine.data.DataBasic;
import hide92795.NovelEngine.data.DataGui;
import hide92795.NovelEngine.data.DataMainMenu;
import hide92795.NovelEngine.data.DataStory;
import hide92795.NovelEngine.gui.Button;
import hide92795.NovelEngine.panel.Panel;
import hide92795.NovelEngine.panel.PanelFadeLogo;
import hide92795.NovelEngine.panel.PanelStory;

import java.awt.Dimension;
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
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class NovelEngine {
	/** time at last frame */
	private long lastFrame;
	/** frames per second */
	private int fps;
	/** last fps time */
	private long lastFPS;
	public DataBasic dataBasic;
	private Panel currentPanel;
	private boolean leftClick = false;
	private boolean rightClick = false;
	public DataMainMenu dataMainMenu;
	public static NovelEngine theEngine;
	public ImageManager imageManager;
	public SoundManager soundManager;
	public QueueHandler queue;
	private boolean closeRequested;
	public int nowfps;
	private NovelEngineFrame frame;
	public int width;
	public int height;
	protected DataStory s;
	private Panel nextPanel;
	private boolean changePanel;

	public NovelEngine() {
		theEngine = this;
		imageManager = new ImageManager();
		soundManager = new SoundManager();
		queue = new QueueHandler();
		initResource();
		width = dataBasic.getWidth();
		height = dataBasic.getHeight();
		frame = new NovelEngineFrame(this);
		
	}

	public void start() {
		try {
			Display.setParent(frame.getCanvas());
			Display.setVSyncEnabled(true);
			lastFPS = getTime();
			loadMenuData();

			frame.setVisible(true);
			initGL();
			setCurrentPanel(new PanelFadeLogo(this));
			closeRequested = false;
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Dimension newDim;
		while (!Display.isCloseRequested() && !closeRequested) {
			newDim = frame.newCanvasSize.getAndSet(null);
			if (newDim != null) {
				width = newDim.width;
				height = newDim.height;
				GL11.glViewport(0, 0, width, height);
			}
			updateFPS();
			pollInput();
			update(getDelta());
			render();
			queue.execute();
			panelChange();
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
		AL.destroy();
		System.exit(0);
	}

	public void initGL() throws LWJGLException {
		Display.create();
		// enable alpha blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, dataBasic.getWidth(), dataBasic.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public void exit() {
		closeRequested = true;
	}

	private void loadMenuData() {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					dataMainMenu = (DataMainMenu) DataLoader.loadData(null,
							"menu.dat", DataMainMenu.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void update(int delta) {
		if (currentPanel != null) {
			currentPanel.update(delta);
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
		nextPanel = panel;
		changePanel = true;
	}
	
	private void panelChange() {
		if(changePanel == true){
			changePanel  =false;
			this.currentPanel = nextPanel;
			if (currentPanel != null) {
				currentPanel.init();
			}
		}
			
	}

	private void initResource() {
		Exception ex = null;
		try {
			dataBasic = (DataBasic) DataLoader.loadData(null, "gameinfo.dat",
					DataBasic.class);
			DataLoader.parseGui(null);
			//DataLoader.loadData(null, "gui.dat", DataGui.class);
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
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("A Key Released");
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
			frame.setTitle("FPS: " + fps);
			nowfps = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void main(String[] argv) {
		NovelEngine engine = new NovelEngine();
		engine.start();
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

	/**
	 * 指定されたチャプターIDからストーリーを開始します。
	 * 
	 * @param id
	 *            スタート元のチャプターID
	 */
	public void startStory(int id) {
		setCurrentPanel(new PanelStory(this, s));
	}

	public void loadStory(final int id) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					s = (DataStory) DataLoader.loadData(null, id + ".dat", DataStory.class);
					System.out
							.println("NovelEngine.loadStory(...).new Thread() {...}.run()");
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				super.run();
			}
		};
		t.start();
	}
}
