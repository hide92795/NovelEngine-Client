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
import hide92795.novelengine.UniqueNumberProvider;
import hide92795.novelengine.box.Box;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.entity.EntityGui;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.words.EntityWords;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * ゲーム上でストーリーデータを読み込んで表示を行うクラスです。
 * 
 * @author hide92795
 */
public class PanelStory extends Panel {
	/**
	 * 表示を行うストーリーデータです。
	 */
	private DataStory story;
	/**
	 * 現在処理を行なっている {@link hide92795.novelengine.story.Story Story} データのリストです。
	 */
	private LinkedList<Story> processList;
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
	 * ブロック内のストーリーデータの処理が終わったかどうかを表します。
	 */
	private boolean finishAll;

	/**
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param story
	 *            画面の描画に使用するストーリーデータ
	 */
	public PanelStory(NovelEngine engine, DataStory story) {
		super(engine);
		this.story = story;
		this.processList = new LinkedList<Story>();
		this.guiList = new HashMap<Integer, EntityGui>();
		this.guiIdProvider = new UniqueNumberProvider();
	}

	@Override
	public void init() {
	}

	@Override
	public void update(int delta) {
		if (finishAll) {
			// すべてが完了
			// 処理リストをクリア
			processList.clear();
			Story s;
			boolean b = false;
			do {
				s = story.next();
				if (s instanceof StoryBlock) {
					b = ((StoryBlock) s).isStartBlock();
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

		finishAll = true;

		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
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

	@Override
	public void render(NovelEngine engine) {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		engine.getBackGroundManager().render(engine);
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
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
		box.update(delta);
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
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
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
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
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
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
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
		words.init(this);
		this.words = words;
	}
}
