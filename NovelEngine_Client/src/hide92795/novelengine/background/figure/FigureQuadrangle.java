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
 * 指定した４つの点を頂点にもつ四角形を描画範囲とするフィギュアデータです。
 * 
 * @author hide92795
 */
public class FigureQuadrangle extends Figure {
	/**
	 * 頂点の座標を表す配列です。
	 */
	private final int[][] apexes;

	/**
	 * 指定した４つの点を頂点にもつ四角形を描画範囲とするフィギュアデータを生成します。
	 * 
	 * @param id
	 *            このフィギュアのID
	 * @param apexes
	 *            描画範囲の頂点
	 * @param lines
	 *            描画する線の配列
	 */
	public FigureQuadrangle(int id, int[][] apexes, Line[] lines) {
		super(id, lines);
		this.apexes = apexes;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
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
		Line[] lines = getLines();
		if (lines != null) {
			for (Line line : lines) {
				line.render(apexes);
			}
		}
	}

}
