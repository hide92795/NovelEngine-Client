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
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.entity.EntityGui;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;

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

	// private boolean showBox = false;
	// private Audio bgm;

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
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(int delta) {
		boolean finishAll = true;
		Iterator<Story> i = processList.iterator();
		// 完了確認
		while (i.hasNext()) {
			Story s = i.next();
			if (!s.isFinish()) {
				finishAll = false;
			}
		}
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

		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
			story.update(this, delta);
		}
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			entityGui.update(this, delta);
		}
	}

	@Override
	public void render(NovelEngine engine) {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		engine.getBackGroundManager().render(engine);
		// renderBox();
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
			story.render(engine);
		}
		Set<Integer> set_g = guiList.keySet();
		for (Integer id : set_g) {
			EntityGui entityGui = guiList.get(id);
			entityGui.render(engine);
		}
	}

	// private void renderBox() {
	// if (showBox) {
	// //DataGui data = engine.dataGui;
	// Texture t = engine.imageManager.getImage(data.getBoxImageId());
	// int x = data.getBoxXpos();
	// int y = data.getBoxYpos();
	// Renderer.renderImage(t, x, y, x + t.getTextureWidth(), y +
	// t.getTextureHeight());
	// }
	// }

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

	// public boolean isShowBox() {
	// return showBox;
	// }
	//
	// public void setShowBox(boolean showBox) {
	// this.showBox = showBox;
	// }
	//
	// public void playBGM(int id) {
	// if (bgm != null) {
	// bgm.stop();
	// }
	// // bgm = engine.soundManager.getSound(id);
	// if (bgm != null) {
	// bgm.playAsMusic(1.0f, 1.0f, true);
	// }
	// }
	//
	// public void stopBGM() {
	// if (bgm != null) {
	// bgm.stop();
	// }
	// }

}
