//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package hide92795.novelengine.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import hide92795.novelengine.Logger;
import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.Utils;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.loader.LoaderBasic;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.loader.item.DataBasic;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.BackGroundManager;
import hide92795.novelengine.manager.CharacterManager;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.EffectManager;
import hide92795.novelengine.manager.GuiManager;
import hide92795.novelengine.manager.ImageManager;
import hide92795.novelengine.manager.QueueManager;
import hide92795.novelengine.manager.SoundManager;
import hide92795.novelengine.manager.StoryManager;
import hide92795.novelengine.panel.Panel;
import hide92795.novelengine.panel.PanelCrashInfo;
import hide92795.novelengine.panel.PanelPrestartStory;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.story.StoryMoveChapter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * NovelEngineの中枢機能を担うクラスです。
 *
 * @author hide92795
 */
public class NovelEngine {
	/**
	 * NovelEngineをデバッグモードで動かす際にtrueにします。
	 */
	public static final boolean DEBUG = true;
	/**
	 * キューデータを実行するキューマネージャーです。
	 */
	private final QueueManager queueManager;
	/**
	 * 画像の管理を行うイメージマネージャーです。
	 */
	private final ImageManager imageManager;
	/**
	 * 音声の管理を行うサウンドマネージャーです。
	 */
	private final SoundManager soundManager;
	/**
	 * ストーリーデーターの管理を行うストーリーマネージャーです。
	 */
	private final StoryManager storyManager;
	/**
	 * ストーリー上での描画の管理を行うバックグラウンドマネージャーです。
	 */
	private final BackGroundManager backGroundManager;
	/**
	 * 各種エフェクトの管理を行うエフェクトマネージャーです。
	 */
	private final EffectManager effectManager;
	/**
	 * ゲーム上での設定及びフラグを管理するコンフィグマネージャーです。
	 */
	private final ConfigurationManager configurationManager;
	/**
	 * 操作可能な各種GUIを管理するGUIマネージャーです。
	 */
	private final GuiManager guiManager;
	/**
	 * キャラクターデータを管理するキャラクターマネージャーです。
	 */
	private final CharacterManager characterManager;
	/**
	 * 最後にループ処理が行われた時間です。
	 */
	private long lastFrame;
	/**
	 * 前回、fpsを計算した時から経過したフレーム数です。
	 */
	private int fps;
	/**
	 * 最後にfpsを求めた時刻です。
	 */
	private long lastFPS;
	/**
	 * 現在進行中のゲームに関する情報を保管します。
	 */
	private DataBasic dataBasic;
	/**
	 * 現在描画を行なっている{@link hide92795.novelengine.panel.Panel Panel}オブジェクトです。
	 */
	private Panel currentPanel;
	/**
	 * 次の更新の際に描画を開始する{@link hide92795.novelengine.panel.Panel Panel}オブジェクトです。
	 */
	private Panel nextPanel;
	/**
	 * マウスの左ボタンが押下されている時にtrueです。
	 */
	private boolean leftClick = false;
	/**
	 * マウスの右ボタンが押下されている時にtrueです。
	 */
	private boolean rightClick = false;
	/**
	 * ユーザーよりゲームの終了の要求があった時にtrueです。
	 */
	private boolean closeRequested;
	/**
	 * 進行中のゲームが何らかのエラーにより続行不可能になった場合にtrueです。
	 */
	private boolean hasCrash;
	/**
	 * 画面内での有効な描画範囲の左上のX座標です。
	 */
	private int x;
	/**
	 * 画面内での有効な描画範囲の左上のY座標です。
	 */
	private int y;
	/**
	 * 画面内での有効な描画範囲の横幅です。
	 */
	private int width;
	/**
	 * 画面内での有効な描画範囲の縦幅です。
	 */
	private int height;
	/**
	 * 画面の有効な描画範囲がデフォルトの描画範囲よりどれほど拡大縮小されているかを表します。
	 */
	private float magnification = 1.0f;
	/**
	 * 現在実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトです。
	 */
	private static NovelEngine theEngine;

	/**
	 * 各種マネージャの作成及び必要なリソースを読み込みます。
	 */
	public NovelEngine() {
		theEngine = this;
		storyManager = new StoryManager(this);
		configurationManager = new ConfigurationManager();
		effectManager = new EffectManager();
		imageManager = new ImageManager();
		soundManager = new SoundManager(this);
		queueManager = new QueueManager();
		backGroundManager = new BackGroundManager();
		guiManager = new GuiManager(this);
		characterManager = new CharacterManager();
		initResource();
	}

	/**
	 * NovelEngineシステムを起動します。
	 *
	 * @param arg
	 *            起動時に与えられた引数
	 */
	public static void main(String[] arg) {
		Logger.init();
		Logger.info("NovelEngine launched!");
		initNativeLibrary();
		NovelEngine engine = new NovelEngine();
		engine.start();
	}

	/**
	 * 実行に必要なネイティブライブラリをパスに追加します。
	 */
	private static void initNativeLibrary() {
		File path = new File(getCurrentDir(), "lib");
		path = new File(path, "native");
		int platform = LWJGLUtil.getPlatform();
		switch (platform) {
		case LWJGLUtil.PLATFORM_WINDOWS:
			path = new File(path, "windows");
			break;
		case LWJGLUtil.PLATFORM_MACOSX:
			path = new File(path, "macosx");
			break;
		case LWJGLUtil.PLATFORM_LINUX:
			path = new File(path, "linux");
			break;
		default:
			break;
		}
		try {
			System.setProperty("org.lwjgl.librarypath", path.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 起動時に必要なリソース類を読み込みます。
	 */
	private void initResource() {
		try {
			dataBasic = LoaderBasic.load();
			loadStory(StoryManager.CHAPTER_START);
			createBootStory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 起動時に実行するストーリデータのロード及びGuiデータの読み込みを行います。
	 */
	private void createBootStory() {
		DataStory data = new DataStory(StoryManager.CHAPTER_BOOT);
		data.addStory(StoryBlock.BLOCKSTART);
		data.addStory(new StoryMoveChapter(StoryManager.CHAPTER_START));
		data.addStory(StoryBlock.BLOCKEND);
		storyManager.addStory(data);
		LoaderResource loader = new LoaderResource(this, StoryManager.CHAPTER_BOOT);
		guiManager.loadResource(loader);
		loader.start();
	}

	/**
	 * クライアントjarがあるフォルダを返します。
	 *
	 * @return jarファイルのある場所を示すFile。エラーが発生した場合はnull
	 */
	public static File getCurrentDir() {
		URI uri = null;
		try {
			uri = NovelEngine.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new File(uri).getParentFile();
	}

	/**
	 * ディスプレイ表示、メインループなどの処理を行います。
	 */
	private void start() {
		try {
			// ディスプレイ初期化
			Display.setDisplayMode(new DisplayMode(dataBasic.getWidth(), dataBasic.getHeight()));
			Display.setResizable(dataBasic.isAllowResize());
			Display.setVSyncEnabled(true);
			Display.setIcon(dataBasic.getIcons());
			Display.setTitle(dataBasic.getGamename());
			Display.create();
		} catch (LWJGLException e) {
			crash(new NovelEngineException(e, "null"));
		}
		lastFPS = getTime();
		width = getDefaultWidth();
		height = getDefaultHeight();
		initGL();
		setCurrentPanel(new PanelPrestartStory(this, StoryManager.CHAPTER_BOOT));
		closeRequested = false;
		while (!Display.isCloseRequested() && !closeRequested) {
			try {
				int delta = getDelta();
				if (Display.wasResized()) {
					dataBasic.getAspectRatio().adjust(this, Display.getWidth(), Display.getHeight());
				}
				updateFPS();
				if (!hasCrash) {
					pollInput();
					update(delta);
					render();
					queueManager.execute();
				} else {
					update(delta);
					render();
				}
			} catch (Exception e) {
				if (e instanceof NovelEngineException) {
					crash((NovelEngineException) e);
				} else {
					crash(new NovelEngineException(e, "null"));
				}
			} finally {
				panelChange();
				Display.update();
				Display.sync(60);
			}
		}
		Display.destroy();
		soundManager.clean();
		AL.destroy();
		Logger.info("NovelEngine shutdowned!");
		System.exit(0);
	}

	/**
	 * OpenGL系の初期化処理を行います。
	 */
	private void initGL() {
		glClearColor(0f, 0f, 0f, 0f);
		glEnable(GL_BLEND);
		glEnable(GL_STENCIL_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, dataBasic.getWidth(), dataBasic.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	/**
	 * マウス及びキーボードの押下状態を検知します。
	 */
	private void pollInput() {
		// 左クリック
		if (Mouse.isButtonDown(0)) {
			if (!leftClick) {
				leftClick = true;
				if (currentPanel != null) {
					int x = Math.round((Mouse.getX() - this.x) / magnification);
					int y = Math.round((Display.getHeight() - (Mouse.getY() + 1) - this.y) / magnification);
					MouseEvent event = new MouseEvent(this, x, y);
					currentPanel.onLeftClickStart(event);
				}
			}
		} else {
			if (leftClick) {
				leftClick = false;
				if (currentPanel != null) {
					int x = Math.round((Mouse.getX() - this.x) / magnification);
					int y = Math.round((Display.getHeight() - (Mouse.getY() + 1) - this.y) / magnification);
					MouseEvent event = new MouseEvent(this, x, y);
					currentPanel.onLeftClickFinish(event);
				}
			}
		}
		// 右クリック
		if (Mouse.isButtonDown(1)) {
			if (!rightClick) {
				rightClick = true;
				if (currentPanel != null) {
					int x = Math.round((Mouse.getX() - this.x) / magnification);
					int y = Math.round((Display.getHeight() - (Mouse.getY() + 1) - this.y) / magnification);
					MouseEvent event = new MouseEvent(this, x, y);
					currentPanel.onRightClickStart(event);
				}
			}
		} else {
			if (rightClick) {
				rightClick = false;
				if (currentPanel != null) {
					int x = Math.round((Mouse.getX() - this.x) / magnification);
					int y = Math.round((Display.getHeight() - (Mouse.getY() + 1) - this.y) / magnification);
					MouseEvent event = new MouseEvent(this, x, y);
					currentPanel.onRightClickFinish(event);
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("SPACE KEY IS DOWN");
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (currentPanel != null) {
					currentPanel.onKeyPressed(this, Keyboard.getEventKey());
				}
			} else {
				if (currentPanel != null) {
					currentPanel.onKeyReleased(this, Keyboard.getEventKey());
				}
			}
		}
	}

	/**
	 * マネージャー及び画面に対してアップデートを行います。
	 *
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	private void update(int delta) {
		if (currentPanel != null) {
			currentPanel.update(delta);
		}
	}

	/**
	 * 画面の描画を行います。
	 */
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glLoadIdentity();
		if (currentPanel != null) {
			currentPanel.render(this);
		} else {
			glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	/**
	 * {@link NovelEngine#setCurrentPanel(Panel panel) setCurrentPanel}
	 * によってパネルの変更がマークされている場合、パネルの変更及び初期化の通知をパネルに対して送ります。
	 */
	private void panelChange() {
		if (nextPanel != null) {
			currentPanel = nextPanel;
			nextPanel = null;
			if (currentPanel != null) {
				currentPanel.init();
			}
		}
	}

	/**
	 * 正確なシステムの時刻を取得します。
	 *
	 * @return ミリ秒単位でのシステム上の時間
	 */
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * 前回のフレームとの時間差をミリ秒単位で返します。
	 *
	 * @return 前回のフレームとの時間差
	 */
	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	/**
	 * FPSを測定し、デバッグモードの場合はタイトルバーにその結果を表示します。
	 */
	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			if (DEBUG) {
				Display.setTitle(dataBasic.getGamename() + " FPS: " + fps);
			}
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	/**
	 * プログラムの実行中に続行不可の例外が投げられた場合に呼ばれます。<br>
	 * １度目はクラッシュ用の画面の表示を試みます。<br>
	 * それでもまたこのメソッドが呼ばれた場合はエラーログを出力してプログラムを停止させます。
	 *
	 * @param exception
	 *            発生した例外
	 */

	public synchronized void crash(NovelEngineException exception) {
		Utils.printStackTraceToLogger(exception);
		setCurrentPanel(new PanelCrashInfo(this, exception));
		hasCrash = true;
	}

	/**
	 * 指定したチャプターのロードを開始します。<br>
	 * ロードは別スレッドにて行われます。
	 *
	 * @param chapterId
	 *            読み込み先のチャプターID
	 */
	public void loadStory(int chapterId) {
		File current = NovelEngine.getCurrentDir();
		File file;
		if (chapterId == StoryManager.CHAPTER_START) {
			file = new File(current, "start.nes");
		} else if (chapterId == StoryManager.CHAPTER_MENU) {
			file = new File(current, "menu.nes");
		} else {
			file = new File(current, "story");
			file = new File(file, chapterId + ".nes");
		}
		storyManager.loadStory(this, file, chapterId);
	}

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
	 * 画面に表示するパネルを変更します。<br>
	 * 実際に入れ替わるのは{@link NovelEngine#panelChange() panelChange}が呼ばれた時です。
	 *
	 * @see NovelEngine#panelChange()
	 * @param panel
	 *            次に処理を行う{@link hide92795.novelengine.panel.Panel Panel}オブジェクト
	 */
	public void setCurrentPanel(Panel panel) {
		nextPanel = panel;
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
	 * エンジンを終了します。<br>
	 */
	public void exit() {
		closeRequested = true;
	}

	/**
	 * 現在実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクトを返します。
	 *
	 * @return 実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public static final NovelEngine getEngine() {
		return theEngine;
	}

	/**
	 * キューデータを実行するキューマネージャーを返します。
	 *
	 * @return キューマネージャー
	 */
	public final QueueManager getQueueManager() {
		return queueManager;
	}

	/**
	 * 画像の管理を行うイメージマネージャーを返します。
	 *
	 * @return イメージマネージャー
	 */
	public final ImageManager getImageManager() {
		return imageManager;
	}

	/**
	 * 音声の管理を行うサウンドマネージャーを返します。
	 *
	 * @return サウンドマネージャー
	 */
	public final SoundManager getSoundManager() {
		return soundManager;
	}

	/**
	 * ストーリーデーターの管理を行うストーリーマネージャーを返します。
	 *
	 * @return ストーリーマネージャー
	 */
	public final StoryManager getStoryManager() {
		return storyManager;
	}

	/**
	 * ストーリー上での描画の管理を行うバックグラウンドマネージャーを返します。
	 *
	 * @return バックグラウンドマネージャー
	 */
	public final BackGroundManager getBackGroundManager() {
		return backGroundManager;
	}

	/**
	 * 各種エフェクトの管理を行うエフェクトマネージャーを返します。
	 *
	 * @return エフェクトマネージャー
	 */
	public final EffectManager getEffectManager() {
		return effectManager;
	}

	/**
	 * ゲーム上での設定及びフラグを管理するコンフィグマネージャーを返します。
	 *
	 * @return コンフィグマネージャー
	 */
	public final ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	/**
	 * 操作可能な各種GUIを管理するコンフィグマネージャーを返します。
	 *
	 * @return GUIマネージャー
	 */
	public final GuiManager getGuiManager() {
		return guiManager;
	}

	/**
	 * キャラクターデータを管理するキャラクターマネージャーを返します。
	 *
	 * @return キャラクターマネージャー
	 */
	public final CharacterManager getCharacterManager() {
		return characterManager;
	}

	/**
	 * 進行中のゲームで設定されているデフォルトの画面の横幅を取得します。
	 *
	 * @return デフォルトの画面の横幅
	 */
	public final int getDefaultWidth() {
		return dataBasic.getWidth();
	}

	/**
	 * 進行中のゲームで設定されているデフォルトの画面の縦幅を取得します。
	 *
	 * @return デフォルトの画面の縦幅
	 */
	public final int getDefaultHeight() {
		return dataBasic.getHeight();
	}

	/**
	 * 現在のマウスのX座標を返します。この座標はデフォルトの画面の大きさ上での位置を表します。
	 *
	 * @return 現在のマウスのX座標
	 */
	public final int getMouseX() {
		int x = Math.round((Mouse.getX() - this.x) / magnification);
		return x;
	}

	/**
	 * 現在のマウスのY座標を返します。この座標はデフォルトの画面の大きさ上での位置を表します。
	 *
	 * @return 現在のマウスのY 座標
	 */
	public final int getMouseY() {
		int y = Math.round((Display.getHeight() - (Mouse.getY() + 1) - this.y) / magnification);
		return y;
	}

	/**
	 * 画面内での有効な描画範囲の左上のX座標を返します。
	 *
	 * @return 有効な描画範囲の左上のX座標
	 */
	public final int getX() {
		return x;
	}

	/**
	 * 画面内での有効な描画範囲の左上のX座標を設定します。
	 *
	 * @param x
	 *            描画範囲として設定された範囲の左上のX座標
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * 画面内での有効な描画範囲の左上のY座標を返します。
	 *
	 * @return y 有効な描画範囲の左上のY座標
	 */
	public final int getY() {
		return y;
	}

	/**
	 * 画面内での有効な描画範囲の左上のY座標を設定します。
	 *
	 * @param y
	 *            描画範囲として設定された範囲の左上のY座標
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * 画面内の描画範囲の横幅を取得します。
	 *
	 * @return 現在の画面内の描画範囲の横幅
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * 新しく画面内の描画範囲の横幅を設定します。
	 *
	 * @param width
	 *            新しい画面内の描画範囲の横幅
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 画面内の描画範囲の縦幅を取得します。
	 *
	 * @return 現在の画面内の描画範囲の縦幅
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * 新しく画面内の描画範囲の縦幅を設定します。
	 *
	 * @param height
	 *            新しい画面内の描画範囲の縦幅
	 */
	public final void setHeight(int height) {
		this.height = height;
	}

	/**
	 * デフォルト画面大きさに対する現在の画面の大きさの拡大縮小率を取得します。
	 *
	 * @return 画面の大きさの拡大縮小率
	 */
	public final float getMagnification() {
		return magnification;
	}

	/**
	 * 新しくデフォルト画面大きさに対する現在の画面の大きさの拡大縮小率を設定します。
	 *
	 * @param magnification
	 *            新しい画面の大きさの拡大縮小率
	 */
	public final void setMagnification(float magnification) {
		this.magnification = magnification;
	}
}
