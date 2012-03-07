package hide92795.novelengine.panel;

import static org.lwjgl.opengl.GL11.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.Utils;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.data.DataStory;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryChangeBg;

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
		this.bgColor = Color.white;
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
				System.out.println(s.isWait());
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
	public void render() {
		renderBackGround();
		renderBox();

		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.render();
		}
	}

	private void renderBox() {
		if (showBox) {
			float x = NovelEngine.theEngine.width - 700;
			float y = NovelEngine.theEngine.height - 241;
			Texture t = NovelEngine.theEngine.imageManager.getImage(123456);
			Renderer.renderImage(t, x, y, x + t.getTextureWidth(),
					y + t.getTextureHeight());
		}
	}

	@Override
	public void leftClick(int x, int y) {
		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.leftClick(x, y);
		}
	}

	private void renderBackGround() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (nowBgTexture != null) {
			Renderer.renderBgImage(nowBgTexture);
		} else {
			Renderer.renderColor(bgColor.r, bgColor.g, bgColor.b);
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

	public int getChapterId() {
		return story.getChapterId();
	}

}
