package hide92795.novelengine.background.figure;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;
import hide92795.novelengine.background.Figure;
import hide92795.novelengine.client.NovelEngine;

/**
 * 指定した点を中心とし、指定した半径及び分割数による円形を描画範囲とする {@link hide92795.novelengine.background.Figure Figure} です。
 *
 * @author hide92795
 */
public class Figure_Circle extends Figure {
	/**
	 * 描画する際に円周を何分割して描画するかを表します。
	 */
	private int division;
	/**
	 * 円の半径を表します。
	 */
	private int radius;
	/**
	 * 円の中心のX座標を表します。
	 */
	private int ox;
	/**
	 * 円の中心のY座標を表します。
	 */
	private int oy;

	/**
	 * 指定した点を中心とし、指定した半径及び分割数による円形を描画範囲とする {@link hide92795.novelengine.background.Figure Figure}オブジェクトを生成します。
	 *
	 * @param division
	 *            円の分割数
	 * @param radius
	 *            円の半径
	 * @param ox
	 *            円の中心のX座標
	 * @param oy
	 *            円の中心のY座標
	 */
	public Figure_Circle(int division, int radius, int ox, int oy) {
		this.division = division;
		this.radius = radius;
		this.ox = ox;
		this.oy = oy;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		glBegin(GL_TRIANGLE_FAN);
		glVertex2i(ox, oy);
		for (int i = 0; i <= division; i++) {
			// 座標を計算
			float rate = (float) i / division;
			float x = (float) (radius * Math.cos(2.0 * Math.PI * rate));
			float y = (float) (radius * Math.sin(2.0 * Math.PI * rate));
			glVertex2d(x + ox, y + oy);
		}
		glEnd();

	}

}
