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
package hide92795.novelengine.manager;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilOp;
import hide92795.novelengine.background.BackGround;
import hide92795.novelengine.client.NovelEngine;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

import org.lwjgl.opengl.GL11;

/**
 * {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトの管理及び描画指示を行うクラスです。
 *
 * @author hide92795
 */
public class BackGroundManager {
	/**
	 * {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトのリストです。
	 */
	private TreeMap<Byte, BackGround> backgrounds;

	/**
	 * {@link hide92795.novelengine.manager.BackGroundManager BackGroundManager} のオブジェクトを生成します。
	 */
	public BackGroundManager() {
		backgrounds = new TreeMap<Byte, BackGround>(new DataComparator());
	}

	/**
	 * 指定された {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトを取得します。
	 *
	 * @param target
	 *            取得する {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトのID
	 * @return 対象の {@link hide92795.novelengine.background.BackGround BackGround} オブジェクト
	 */
	public BackGround getBackGround(byte target) {
		BackGround bg = backgrounds.get(target);
		if (bg == null) {
			bg = new BackGround();
			backgrounds.put(target, bg);
		}
		return bg;
	}

	/**
	 * 各 {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトに描画指示を行います。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public void render(NovelEngine engine) {
		Set<Byte> s = backgrounds.keySet();
		for (byte i : s) {
			resetStencil(engine);
			BackGround bg = backgrounds.get(i);
			bg.createStencil(engine);
			bg.render(engine);
		}
		glStencilFunc(GL_ALWAYS, 0, 0);
	}

	/**
	 * ステンシルバッファに書き込まれたステンシル領域をリセットします。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void resetStencil(NovelEngine engine) {
		glColorMask(false, false, false, false);
		glDepthMask(false);
		glStencilFunc(GL_ALWAYS, 0, ~0);
		glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(engine.getDefaultWidth(), 0);
			GL11.glVertex2f(engine.getDefaultWidth(), engine.getDefaultHeight());
			GL11.glVertex2f(0, engine.getDefaultHeight());
		}
		GL11.glEnd();
	}

	/**
	 * {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトを昇順に整列させるコンパレータです。
	 *
	 * @author hide92795
	 */
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
