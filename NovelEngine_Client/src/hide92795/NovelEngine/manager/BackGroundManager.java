package hide92795.novelengine.manager;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.client.NovelEngine;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class BackGroundManager {
	private TreeMap<Byte, BackGround> backgrounds;

	public BackGroundManager() {
		backgrounds = new TreeMap<Byte, BackGround>(new DataComparator());
	}

	public BackGround getBackGround(byte target) {
		BackGround bg = backgrounds.get(target);
		if (bg == null) {
			bg = new BackGround();
			backgrounds.put(target, bg);
		}
		return bg;
	}

	public void render(NovelEngine engine) {
		Set<Byte> s = backgrounds.keySet();
		for (byte i : s) {
			BackGround bg = backgrounds.get(i);
			bg.createStencil(engine, i);
			bg.render(engine, i);
		}
		glStencilFunc(GL_ALWAYS, 0, 0);
	}

	public class DataComparator implements Comparator<Byte> {
		@Override
		public int compare(Byte o1, Byte o2) {
			if (o1.byteValue() > o1.byteValue()) {
				return -1;
			} else if (o1.byteValue() == o1.byteValue()) {
				return 0;
			} else if (o1.byteValue() < o1.byteValue()) {
				return 1;
			}
			return 0;
		}
	}
}
