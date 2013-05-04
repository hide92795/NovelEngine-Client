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
package hide92795.novelengine.background.figure;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;
import hide92795.novelengine.client.NovelEngine;

/**
 * 画面全体を描画範囲とするフィギュアデータです。
 * 
 * @author hide92795
 */
public class FigureEntireScreen extends Figure {
	/**
	 * 頂点の座標を表す配列です。
	 */
	private int[][] apexes;

	/**
	 * 画面全体を描画範囲とするフィギュアデータを作成します。
	 * 
	 * @param id
	 *            このフィギュアのID
	 * @param lines
	 *            描画する線を表す配列
	 */
	public FigureEntireScreen(int id, Line[] lines) {
		super(id, lines);
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		if (apexes == null) {
			createApexData(engine);
		}
		glBegin(GL_QUADS);
		{
			glVertex2i(apexes[0][0], apexes[0][1]);
			glVertex2i(apexes[1][0], apexes[1][1]);
			glVertex2i(apexes[2][0], apexes[2][1]);
			glVertex2i(apexes[3][0], apexes[3][1]);
		}
		glEnd();
	}

	@Override
	public void renderLine(NovelEngine engine) {
		if (apexes == null) {
			createApexData(engine);
		}
		Line[] lines = getLines();
		if (lines != null) {
			for (Line line : lines) {
				line.render(apexes);
			}
		}
	}

	/**
	 * 頂点データを作成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void createApexData(NovelEngine engine) {
		int width = engine.getDefaultWidth();
		int height = engine.getDefaultHeight();
		apexes = new int[4][2];
		apexes[0][0] = 0;
		apexes[0][1] = 0;
		apexes[1][0] = width;
		apexes[1][1] = 0;
		apexes[2][0] = width;
		apexes[2][1] = height;
		apexes[3][0] = 0;
		apexes[3][1] = height;
	}
}
