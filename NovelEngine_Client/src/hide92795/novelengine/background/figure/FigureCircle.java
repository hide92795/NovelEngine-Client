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

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import hide92795.novelengine.client.NovelEngine;

/**
 * 指定した点を中心とし、指定した半径及び分割数による円形を描画範囲とするフィギュアデータです。
 * 
 * @author hide92795
 */
public class FigureCircle extends Figure {
	/**
	 * 円の中心のX座標を表します。
	 */
	private final int ox;
	/**
	 * 円の中心のY座標を表します。
	 */
	private final int oy;
	/**
	 * 頂点の座標を表す配列です。
	 */
	private final float[][] apexes;

	/**
	 * 円形を描画範囲とするフィギュアデータを生成します。
	 * 
	 * @param id
	 *            このフィギュアのID
	 * @param ox
	 *            円の中心のX座標
	 * @param oy
	 *            円の中心のY座標
	 * @param apexes
	 *            描画範囲の円周上の点
	 * @param lines
	 *            描画する線の配列
	 */
	public FigureCircle(int id, int ox, int oy, float[][] apexes, Line[] lines) {
		super(id, lines);
		this.ox = ox;
		this.oy = oy;
		this.apexes = apexes;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		glBegin(GL_TRIANGLE_FAN);
		glVertex2i(ox, oy);
		for (float[] apex : apexes) {
			glVertex2f(apex[0], apex[1]);
		}
		glVertex2f(apexes[0][0], apexes[0][1]);
		glEnd();

	}

	@Override
	public void renderLine(NovelEngine engine) {
		Line[] lines = getLines();
		if (lines != null) {
			for (Line line : lines) {
				line.renderAsConsecutiveLine(apexes);
			}
		}
	}

}
