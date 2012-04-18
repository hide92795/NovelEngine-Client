package hide92795.novelengine.manager;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import hide92795.novelengine.BackGround;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.figure.Figure_Circle;
import hide92795.novelengine.figure.Figure_EntireScreen;
import hide92795.novelengine.figure.Figure_Quadrangle;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class BackGroundManager {
	private TreeMap<Integer, BackGround> backgrounds;
	public BackGroundManager() {
		backgrounds = new TreeMap<Integer, BackGround>();
		Figure_EntireScreen fq = new Figure_EntireScreen();
		BackGround bg = new BackGround(fq);
		backgrounds.put(0, bg);

		Figure_Circle fc = new Figure_Circle(120, 200, 400, 300);
		BackGround bg1 = new BackGround(fc);
		bg1.primaryBgID = -1631768098;
		backgrounds.put(1, bg1);

		Figure_Quadrangle ft = new Figure_Quadrangle(new double[] { 0, 0 },
				new double[] { 250, 0 }, new double[] { 150, 600 },
				new double[] { 0, 600 });
		BackGround bg2 = new BackGround(ft);
		bg2.primaryBgID = -1631768097;
		backgrounds.put(2, bg2);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private BackGround getBackGround(int priority) {
		BackGround bg = backgrounds.get(priority);
		if (bg == null) {
			// bg = new BackGround();
			// backgrounds.put(priority, bg);
		}
		return bg;
	}

	public void changePriority(int target, int destination) {
		if (target == 0 || destination == 0) {
			return;
		}
		BackGround bg = backgrounds.get(target);
		backgrounds.remove(target);
		backgrounds.put(destination, bg);
	}

	public void render(NovelEngine engine) {
		Set<Integer> s = backgrounds.keySet();
		for (int i : s) {
			BackGround bg = backgrounds.get(i);
			bg.createStencil(engine, i);
			bg.render(engine, i);
		}
		glStencilFunc(GL_ALWAYS, 0, 0);
		
	}

	public void changePrimaryBgID(int priority, int bgId) {
		BackGround bg = backgrounds.get(priority);
		if (bg != null) {
			bg.primaryBgID = bgId;
			backgrounds.put(priority, bg);
		}
	}
}
