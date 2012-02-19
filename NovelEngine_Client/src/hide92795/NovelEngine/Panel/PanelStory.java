package hide92795.NovelEngine.panel;

import static org.lwjgl.opengl.GL11.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import hide92795.NovelEngine.Utils;
import hide92795.NovelEngine.client.NovelEngine;
import hide92795.NovelEngine.data.DataStory;
import hide92795.NovelEngine.story.Story;
import hide92795.NovelEngine.story.StoryChangeBg;

public class PanelStory extends Panel {

	private DataStory story;
	private int now;
	private LinkedList<Story> processList;
	public Texture nowBgTexture;
	public Color bgColor;

	public PanelStory(NovelEngine engine, DataStory story) {
		super(engine);
		this.story = story;
		this.now = 0;
		this.nowBgTexture = null;
		this.bgColor = Color.white;
		processList = new LinkedList<Story>();
	}

	@Override
	public void update(int delta) {
		boolean finishAll = true;
		Iterator<Story> i = processList.iterator();
		// 完了確認
		while (i.hasNext()) {
			if (!i.next().isFinish()) {
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
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(nowBgTexture != null){
			Utils.renderBgImage(nowBgTexture);
		}else{
			glColor4f(bgColor.r, bgColor.g, bgColor.b, 1.0f);
			glDisable(GL_TEXTURE_2D);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(1, 0);
				glVertex2f(engine.width, 0);
				glTexCoord2f(1, 1);
				glVertex2f(engine.width, engine.height);
				glTexCoord2f(0, 1);
				glVertex2f(0, engine.height);
			}
			glEnd();
		}
		Iterator<Story> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Story story = iterator.next();
			story.render();
		}
	}

}
