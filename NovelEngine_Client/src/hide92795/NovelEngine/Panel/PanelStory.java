package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.character.Character;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.data.DataGui;
import hide92795.novelengine.data.DataStory;
import hide92795.novelengine.story.Story;

public class PanelStory extends Panel {

	private DataStory story;
	private int now = 0;
	private LinkedList<Story> processList;
	private Texture nowBgTexture;
	private int nowBgTextureId = 0;
	private boolean showBox = false;
	public Color bgColor;

	public PanelStory(NovelEngine engine, DataStory story) {
		super(engine);
		this.story = story;
		this.bgColor = Color.black;
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
			do {
				s = story.next();
				System.out.println(s.getClass());
				s.init(this);
				processList.add(s);
			} while (!s.isWait());
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
		engine.characterManager.render();
		renderBox();
		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.render(engine);
		}
	}

	private void renderBox() {
		if (showBox) {
			DataGui data = engine.dataGui;
			Texture t = engine.imageManager.getImage(data.getBoxImageId());
			int x = data.getBoxXpos();
			int y = data.getBoxYpos();
			Renderer.renderImage(t, x, y, x + t.getTextureWidth(),
					y + t.getTextureHeight());
		}
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

	public final int getBgTextureId() {
		return nowBgTextureId;
	}

	public final void setBgTextureId(int id) {
		this.nowBgTextureId = id;
		this.nowBgTexture = engine.imageManager.getImage(id);
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
}
