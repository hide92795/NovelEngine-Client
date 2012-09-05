package hide92795.novelengine.client;

import hide92795.novelengine.Logger;
import hide92795.novelengine.QueueHandler;
import hide92795.novelengine.loader.LoaderBasic;
import hide92795.novelengine.loader.item.DataBasic;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.BackGroundManager;
import hide92795.novelengine.manager.EffectManager;
import hide92795.novelengine.manager.ImageManager;
import hide92795.novelengine.manager.SettingManager;
import hide92795.novelengine.manager.SoundManager;
import hide92795.novelengine.manager.StoryManager;
import hide92795.novelengine.panel.Panel;
import hide92795.novelengine.panel.PanelPrestartStory;
import hide92795.novelengine.panel.PanelStory;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 *
 * @author hide92795
 */
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
	public static NovelEngine theEngine;
	public ImageManager imageManager;
	public SoundManager soundManager;
	public QueueHandler queue;
	private boolean closeRequested;
	public int nowfps;
	private NovelEngineFrame frame;
	public int width;
	public int height;
	private Panel nextPanel;
	private boolean changePanel;
	private boolean hasCrash;
	public StoryManager storyManager;
	public BackGroundManager backGroundManager;
	public EffectManager effectManager;
	public SettingManager settingManager;

	public NovelEngine() {
		theEngine = this;
		settingManager = new SettingManager();
		effectManager = new EffectManager();
		imageManager = new ImageManager();
		soundManager = new SoundManager();
		queue = new QueueHandler();
		storyManager = new StoryManager();
		backGroundManager = new BackGroundManager();
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

			frame.setVisible(true);
			initGL();
			setCurrentPanel(new PanelPrestartStory(theEngine, "start".hashCode()));
			closeRequested = false;
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Dimension newDim;
		while (!Display.isCloseRequested() && !closeRequested) {
			try {
				newDim = frame.newCanvasSize.getAndSet(null);
				if (newDim != null) {
					width = newDim.width;
					height = newDim.height;
					GL11.glViewport(0, 0, width, height);
				}
				updateFPS();
				if (!hasCrash) {
					pollInput();
					update(getDelta());
					render();
					queue.execute();
				} else {
					setCurrentPanel(null);
				}
				panelChange();
				Display.update();
				Display.sync(60);
			} catch (Exception e) {
				e.printStackTrace();
				hasCrash = true;
			}
		}
		Display.destroy();
		AL.destroy();
		Logger.info("NovelEngine shutdowned!");
		System.exit(0);
	}

	private void initGL() throws LWJGLException {
		Display.create();
		glClearColor(0f, 0f, 0f, 1f);
		glEnable(GL_BLEND);
		glEnable(GL_STENCIL_TEST);
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

	private void update(int delta) {
		if (currentPanel != null) {
			currentPanel.update(delta);
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glLoadIdentity();
		if (currentPanel != null) {
			currentPanel.render(this);
		} else {
			glClearColor(1.0f, 1.0f, 1.0f, 0.5f);
		}
	}

	public void setCurrentPanel(Panel panel) {
		nextPanel = panel;
		changePanel = true;
	}

	private void panelChange() {
		if (changePanel == true) {
			changePanel = false;
			this.currentPanel = nextPanel;
			if (currentPanel != null) {
				currentPanel.init();
			}
		}

	}

	private void initResource() {
		try {
			dataBasic = LoaderBasic.load();
			loadStory("start".hashCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void pollInput() {
		// 左クリック
		// System.out.println("X:"+Mouse.getX() + " Y:" + Mouse.getY());
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
				if (currentPanel != null) {
					currentPanel.keyPressed(Keyboard.getEventKey());
				}
			} else {
				if (currentPanel != null) {
					currentPanel.keyReleased(Keyboard.getEventKey());
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

	public static void main(String[] argv) throws IOException {
		Logger.init();
		Logger.info("NovelEngine launched!");
		NovelEngine engine = new NovelEngine();
		engine.start();
	}

	public static File getCurrentDir() {
		URI uri = null;
		try {
			uri = NovelEngine.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new File(uri).getParentFile();
	}

	//	public void actionPerformed(Button button, boolean isLeft) {
	//		if (currentPanel != null) {
	//			currentPanel.actionPerformed(button, isLeft);
	//		}
	//	}
	//
	//	public void onMouse(Button button) {
	//		if (currentPanel != null) {
	//			currentPanel.onMouse(button);
	//		}
	//	}

	/**
	 * 指定されたチャプターIDのデータのロードを待ってから開始します。<br>
	 * 画面は{@link PanelPrestartStory}により提供されます。
	 *
	 * @param id
	 *            スタート元のチャプターID
	 */
	public void prestartStory(int id) {
		setCurrentPanel(new PanelPrestartStory(this, id));
	}

	/**
	 * 指定されたチャプターIDからストーリーを開始します。<br>
	 * すべてのロードが終わっていない場合、表示に問題が発生する可能性があります。<br>
	 * このメソッドは{@link PanelPrestartStory}より呼び出されるのが適切です。<br>
	 *
	 * @param id
	 *            スタート元のチャプターID
	 */
	public void startStory(int id) {
		DataStory story = storyManager.getStory(id);
		story.reset();
		setCurrentPanel(new PanelStory(this, story));
	}

	/**
	 * 指定したチャプターのロードを開始します。<br>
	 * ロードは別スレッドにて行われます。
	 *
	 * @param chapterId
	 */
	public void loadStory(int chapterId) {
		File current = NovelEngine.getCurrentDir();
		File file;
		if (chapterId == "start".hashCode()) {
			file = new File(current, "start.nes");
		} else if (chapterId == "menu".hashCode()) {
			file = new File(current, "menu.nes");
		} else {
			file = new File(current, "story");
			file = new File(file, chapterId + ".nes");
		}
		storyManager.loadStory(this, file, chapterId);
	}

}
