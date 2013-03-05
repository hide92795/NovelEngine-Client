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

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import hide92795.novelengine.background.Figure;
import hide92795.novelengine.client.NovelEngine;

/**
 * 指定した３つの点を頂点にもつ三角形を描画範囲とする {@link hide92795.novelengine.background.Figure Figure} です。
 *
 * @author hide92795
 */
public class Figure_Triangle extends Figure {
	/**
	 * １つ目の点の座標を表します。
	 */
	private int[] p1;
	/**
	 * ２つ目の点の座標を表します。
	 */
	private int[] p2;
	/**
	 * ３つ目の点の座標を表します。
	 */
	private int[] p3;

	/**
	 * 指定した３つの点を頂点にもつ三角形を描画範囲とする{@link hide92795.novelengine.background.Figure Figure}オブジェクトを生成します。
	 *
	 * @param p1
	 *            １つ目の点
	 * @param p2
	 *            ２つ目の点
	 * @param p3
	 *            ３つ目の点
	 */
	public Figure_Triangle(int[] p1, int[] p2, int[] p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		glBegin(GL_TRIANGLES);
		{
			glVertex2d(p1[0], p1[1]);
			glVertex2d(p2[0], p2[1]);
			glVertex2d(p3[0], p3[1]);
		}
		glEnd();
	}

}
