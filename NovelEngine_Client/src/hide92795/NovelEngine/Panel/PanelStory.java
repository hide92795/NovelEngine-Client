package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.glClearColor;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.Gui;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;

import java.util.Iterator;
import java.util.LinkedList;

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
	 * 現在画面に描画されている {@link hide92795.novelengine.gui.Gui Gui} オブジェクトのリストです。
	 */
	private LinkedList<Gui> guiList;

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
		this.guiList = new LinkedList<Gui>();
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
		Iterator<Gui> iterator_g = guiList.iterator();
		while (iterator_g.hasNext()) {
			Gui gui = iterator_g.next();
			gui.update(this, delta);
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
		Iterator<Gui> iterator_g = guiList.iterator();
		while (iterator_g.hasNext()) {
			Gui gui = iterator_g.next();
			gui.render(engine);
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
		Iterator<Gui> iterator_g = guiList.iterator();
		while (iterator_g.hasNext()) {
			Gui gui = iterator_g.next();
			if (gui.isClickableAt(event.getX(), event.getY())) {
				gui.onLeftClickStart(event);
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
		Iterator<Gui> iterator_g = guiList.iterator();
		while (iterator_g.hasNext()) {
			Gui gui = iterator_g.next();
			if (gui.isClickableAt(event.getX(), event.getY())) {
				gui.onLeftClickFinish(event);
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
		Iterator<Gui> iterator_g = guiList.iterator();
		while (iterator_g.hasNext()) {
			Gui gui = iterator_g.next();
			gui.render(engine);
		}
		Iterator<Story> iterator_s = processList.iterator();
		while (iterator_s.hasNext()) {
			Story story = iterator_s.next();
			story.onKeyPressed(engine, eventKey);
		}
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
