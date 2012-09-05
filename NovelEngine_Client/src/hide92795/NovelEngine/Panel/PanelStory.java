package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.Audio;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;

public class PanelStory extends Panel {

	private DataStory story;
	private int now = 0;
	private LinkedList<Story> processList;
	private boolean showBox = false;
	private Audio bgm;

	public PanelStory(NovelEngine engine, DataStory story) {
		super(engine);
		this.story = story;
		this.processList = new LinkedList<Story>();
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

		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.update(this, delta);
		}
	}

	@Override
	public void render(NovelEngine engine) {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		engine.backGroundManager.render(engine);
		//engine.characterManager.render();
		renderBox();
		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.render(engine);
		}
	}

	private void renderBox() {
		//		if (showBox) {
		//			//DataGui data = engine.dataGui;
		//			Texture t = engine.imageManager.getImage(data.getBoxImageId());
		//			int x = data.getBoxXpos();
		//			int y = data.getBoxYpos();
		//			Renderer.renderImage(t, x, y, x + t.getTextureWidth(), y + t.getTextureHeight());
		//		}
	}

	public void moveScene(int sceneId) {
		story.moveScene(sceneId);
	}

	@Override
	public void leftClick(int x, int y) {
		int size = processList.size();
		for (int i = 0; i < size; i++) {
			processList.get(i).leftClick(this, x, y);
		}
	}

	@Override
	public void keyPressed(int eventKey) {
		int size = processList.size();
		for (int i = 0; i < size; i++) {
			processList.get(i).keyPressed(this, eventKey);
		}
	}

	public final boolean isShowBox() {
		return showBox;
	}

	public final void setShowBox(boolean showBox) {
		this.showBox = showBox;
	}

	public final int getChapterId() {
		return story.getChapterId();
	}

	public final void playBGM(int id) {
		if (bgm != null) {
			bgm.stop();
		}
		bgm = engine.soundManager.getSound(id);
		if (bgm != null) {
			bgm.playAsMusic(1.0f, 1.0f, true);
		}
	}

	public final void stopBGM() {
		if (bgm != null) {
			bgm.stop();
		}
	}
}
