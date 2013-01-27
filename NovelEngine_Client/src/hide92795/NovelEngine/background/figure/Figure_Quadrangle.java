package hide92795.novelengine.background.figure;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import hide92795.novelengine.background.Figure;
import hide92795.novelengine.client.NovelEngine;

/**
 * 指定した４つの点を頂点にもつ四角形を描画範囲とする {@link hide92795.novelengine.background.Figure Figure} です。
 *
 * @author hide92795
 */
public class Figure_Quadrangle extends Figure {
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
	 * ４つ目の点の座標を表します。
	 */
	private int[] p4;

	/**
	 * 指定した４つの点を頂点にもつ四角形を描画範囲とする{@link hide92795.novelengine.background.Figure Figure}オブジェクトを生成します。
	 *
	 * @param p1
	 *            １つ目の点
	 * @param p2
	 *            ２つ目の点
	 * @param p3
	 *            ３つ目の点
	 * @param p4
	 *            ４つ目の点
	 */
	public Figure_Quadrangle(int[] p1, int[] p2, int[] p3, int[] p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		glBegin(GL_QUADS);
		{
			glVertex2d(p1[0], p1[1]);
			glVertex2d(p2[0], p2[1]);
			glVertex2d(p3[0], p3[1]);
			glVertex2d(p4[0], p4[1]);
		}
		glEnd();
	}

}
