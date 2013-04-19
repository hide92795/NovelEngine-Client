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
package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.msgpack.template.Templates.TInteger;
import static org.msgpack.template.Templates.TString;
import static org.msgpack.template.Templates.tMap;
import hide92795.novelengine.UniqueNumberProvider;
import hide92795.novelengine.Utils;
import hide92795.novelengine.box.Box;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.entity.EntityGui;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.loader.item.DataSavedBackGround;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.story.StoryPlayBGM;
import hide92795.novelengine.story.StoryShowWords;
import hide92795.novelengine.words.EntityWords;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.CipherOutputStream;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;

/**
 * ゲーム上でストーリーデータを読み込んで表示を行うクラスです。
 * 
 * @author hide92795
 */
public class PanelStory extends Panel {
	static {
		INTERNAL_DATA_TEMPLATE = tMap(TString, TInteger);
	}
	/**
	 * ロード時に必要な物を保存するマップのデシリアライズに必要なテンプレートです。
	 */
	public static final Template<Map<String, Integer>> INTERNAL_DATA_TEMPLATE;
	/**
	 * 現在表示中の文章データの行番号を管理するためのキーを表します。
	 */
	public static final String INTERNAL_DATA_WORDS_LINE = "wordsline";
	/**
	 * 現在再生中のBGMのIDを管理するためのキーを表します。
	 */
	public static final String INTERNAL_DATA_BGM = "bgm";
	/**
	 * 現在のメッセージボックスの表示状態を管理するためのキーを表します。
	 */
	public static final String INTERNAL_DATA_BOX = "box";
	/**
	 * 文章データがこのパネルに設定されていないことを表します。
	 */
	public static final int WORDS_UNREGISTED = 0;
	/**
	 * 現在設定されている文章データが表示中であることを表します。
	 */
	public static final int WORDS_SHOWING = 1;
	/**
	 * 現在設定されている文章データが完全に表示されていることを表します。
	 */
	public static final int WORDS_SHOWED = 2;
	/**
	 * 表示を行うストーリーデータです。
	 */
	private DataStory story;
	/**
	 * 現在処理を行なっている {@link hide92795.novelengine.story.Story Story} データのリストです。
	 */
	private LinkedHashSet<Story> processList;
	/**
	 * 現在画面に描画されている {@link hide92795.novelengine.gui.entity.EntityGui EntityGui} オブジェクトを管理するマップです。
	 */
	private HashMap<Integer, EntityGui> guiList;
	/**
	 * GUIのIDを提供するプロバイダーです。
	 */
	private UniqueNumberProvider guiIdProvider;
	/**
	 * 現在表示している文章データです。
	 */
	private EntityWords words;
	/**
	 * 現在の文章データの状況を表します。
	 */
	private int wordsState;
	/**
	 * ブロック内のストーリーデータの処理が終わったかどうかを表します。
	 */
	private boolean finishAll;
	/**
	 * 現在のブロックの先頭ブロックの位置です。
	 */
	private int currentStartBlockLine;
	/**
	 * ロードする際に必要なデータです。
	 */
	private Map<String, Integer> internalData;
	/**
	 * 開始時に展開する背景データです。
	 */
	private DataSavedBackGround savedBackGround;

	/**
	 * 指定行からストーリーデータを再開するパネルを生成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param line
	 *            再開する行番号
	 * @param story
	 *            画面の描画に使用するストーリーデータ
	 * @param internalData
	 *            ロードする際に必要なデータ
	 * @param savedBackGround
	 *            開始時に展開する背景データ
	 */
	public PanelStory(NovelEngine engine, DataStory story, int line, Map<String, Integer> internalData,
			DataSavedBackGround savedBackGround) {
		super(engine);
		this.story = story;
		this.processList = new LinkedHashSet<Story>();
		this.guiList = new HashMap<Integer, EntityGui>();
		this.guiIdProvider = new UniqueNumberProvider();
		this.currentStartBlockLine = line;
		if (internalData == null) {
			this.internalData = new HashMap<String, Integer>();
		} else {
			this.internalData = internalData;
		}
		this.savedBackGround = savedBackGround;
		story.setCurrentLine(currentStartBlockLine);
	}

	@Override
	public void init() {
		engine().getBackGroundManager().clear();
		if (savedBackGround != null) {
			engine().getBackGroundManager().restore(savedBackGround);
		}
		nextBlock();
		if (internalData.containsKey(INTERNAL_DATA_WORDS_LINE)) {
			int wordsLine = internalData.get(INTERNAL_DATA_WORDS_LINE);
			Story s_wl = story.getStory(wordsLine);
			if (s_wl instanceof StoryShowWords) {
				s_wl.init(this);
				if (words != null) {
					words.forceFinish();
				}
			}
		}
		if (internalData.containsKey(INTERNAL_DATA_BGM)) {
			int bgm = internalData.get(INTERNAL_DATA_BGM);
			Story s_bgm = new StoryPlayBGM(-1, bgm);
			s_bgm.update(this, 0);
		}
		if (internalData.containsKey(INTERNAL_DATA_BOX)) {
			int boxState = internalData.get(INTERNAL_DATA_BOX);
			engine().getBoxManager().getCurrentBox().setMode(boxState);
		}
	}

	@Override
	public void update(int delta) {
		if (finishAll) {
			// すべてが完了
			// 終了通知
			for (Story story : processList) {
				story.dispose(this);
			}
			nextBlock();
		}

		finishAll = true;

		for (Story story : processList) {
			story.update(this, delta);
			if (!story.isFinish()) {
				finishAll = false;
			}
		}

		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			entityGui.update(this, delta);
		}
		updateBox(delta);
		updateWords(delta);
	}

	/**
	 * 次のブロックを作業用リストに追加します。
	 */
	private void nextBlock() {
		// 処理リストをクリア
		processList.clear();
		Story s;
		boolean b = false;
		do {
			s = story.next();
			if (s instanceof StoryBlock) {
				b = ((StoryBlock) s).isStartBlock();
				currentStartBlockLine = story.getCurrentLine();
			}
		} while (!b);
		s = story.next();
		while (b) {
			System.out.println(s.getClass());
			if (s instanceof StoryBlock) {
				b = ((StoryBlock) s).isStartBlock();
			} else {
				s.init(this);
				processList.add(s);
				s = story.next();
			}
		}
	}

	@Override
	public void render(NovelEngine engine) {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		engine.getBackGroundManager().render(engine);
		for (Story story : processList) {
			story.render(engine);
		}
		renderBox(engine);
		renderWords(engine);
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			entityGui.render(engine);
		}
	}

	/**
	 * メッセージボックスを更新します。
	 * 
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	private void updateBox(int delta) {
		Box box = engine().getBoxManager().getCurrentBox();
		box.update(this, delta);
	}

	/**
	 * 文章データを更新します。
	 * 
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	private void updateWords(int delta) {
		if (words != null) {
			words.update(this, delta);
			if (words.isFinish()) {
				wordsState = WORDS_SHOWED;
			} else {
				wordsState = WORDS_SHOWING;
			}
		} else {
			wordsState = WORDS_UNREGISTED;
		}
	}

	/**
	 * メッセージボックスを描画します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void renderBox(NovelEngine engine) {
		Box box = engine.getBoxManager().getCurrentBox();
		box.render(engine);
	}

	/**
	 * メッセージボックスに文章を描画します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void renderWords(NovelEngine engine) {
		if (words != null) {
			words.render(engine);
		}
	}

	/**
	 * 同チャプター内の別のシーンへ移動します。
	 * 
	 * @param sceneId
	 *            移動先のシーンID
	 */
	public void moveScene(int sceneId) {
		story.moveScene(sceneId);
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			if (entityGui.isClickableAt(event.getX(), event.getY())) {
				entityGui.onLeftClickStart(event);
			}
			if (event.isConsumed()) {
				return;
			}
		}
		for (Story story : processList) {
			story.onLeftClickStart(event);
			if (event.isConsumed()) {
				return;
			}
		}
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			if (entityGui.isClickableAt(event.getX(), event.getY())) {
				entityGui.onLeftClickFinish(event);
			}
			if (event.isConsumed()) {
				return;
			}
		}
		for (Story story : processList) {
			story.onLeftClickFinish(event);
			if (event.isConsumed()) {
				return;
			}
		}
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			entityGui.render(engine);
		}
		for (Story story : processList) {
			story.onKeyPressed(engine, eventKey);
		}
	}

	/**
	 * パネルにGUIを登録します。
	 * 
	 * @param gui
	 *            登録するGUI
	 * @return 削除用に使用する番号
	 */
	public int addGui(EntityGui gui) {
		int id = guiIdProvider.get();
		guiList.put(id, gui);
		return 0;
	}

	/**
	 * パネルからGUIを削除します。<br>
	 * 削除に使用するIDは登録した際の戻り値です。
	 * 
	 * @param id
	 *            削除するGUIのID
	 */
	public void removeGui(int id) {
		guiList.remove(id);
		guiIdProvider.release(id);
	}

	/**
	 * 文章データの表示を開始します。
	 * 
	 * @param words
	 *            表示する文章データ
	 */
	public void setWords(EntityWords words) {
		if (words == null) {
			this.words = null;
		} else {
			words.init(this);
			this.words = words;
		}
	}

	/**
	 * 現在の文章データの状況を取得します。
	 * 
	 * @return 現在の文章データの状況
	 */
	public int getWordsState() {
		return wordsState;
	}

	/**
	 * 現在のストーリーを保存します。
	 * 
	 * @param saveId
	 *            セーブ番号
	 * @throws Exception
	 *             何らかのエラーが発生した場合
	 */
	public void save(int saveId) throws Exception {
		File dir = new File(NovelEngine.getCurrentDir(), "save");
		File file = new File(dir, "save." + saveId + ".neb");
		CipherOutputStream cos = Utils.createCipherOutputStream(file);
		MessagePack msgpack = new MessagePack();
		Packer p = msgpack.createPacker(cos);
		p.write(story.getChapterId());
		p.write(currentStartBlockLine);
		updateInternalData();
		p.write(internalData);
		p.flush();
		ZipOutputStream zos = new ZipOutputStream(cos);
		ZipEntry ze_local = new ZipEntry("private.neb");
		zos.putNextEntry(ze_local);
		engine().getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_PRIVATE).store(zos);
		ZipEntry ze_render = new ZipEntry("render.neb");
		zos.putNextEntry(ze_render);
		engine().getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER).store(zos);
		ZipEntry ze_background = new ZipEntry("background.neb");
		zos.putNextEntry(ze_background);
		engine().getBackGroundManager().save(zos);
		zos.flush();
		zos.close();
	}

	/**
	 * 内部データを更新します。
	 */
	private void updateInternalData() {
		int boxCurrentState = engine().getBoxManager().getCurrentBox().getMode();
		switch (boxCurrentState) {
		case Box.SHOWING:
		case Box.SHOWED:
			internalData.put(INTERNAL_DATA_BOX, Box.SHOWED);
			break;
		case Box.HIDING:
		case Box.HIDED:
			internalData.put(INTERNAL_DATA_BOX, Box.HIDED);
			break;
		default:
			break;
		}
	}

	/**
	 * 内部データを設定します。
	 * 
	 * @param key
	 *            データのキー
	 * @param value
	 *            値
	 */
	public void setInternalData(String key, int value) {
		internalData.put(key, value);
	}
}
