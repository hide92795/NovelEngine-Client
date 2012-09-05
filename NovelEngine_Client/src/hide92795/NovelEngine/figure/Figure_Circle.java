package hide92795.novelengine.figure;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;
import hide92795.novelengine.client.NovelEngine;

public class Figure_Circle extends Figure {
	private int d;
	private float r;
	private int ox;
	private int oy;

	public Figure_Circle(int divisionNum, float radius, int ox, int oy) {
		this.d = divisionNum;
		this.r = radius;
		this.ox = ox;
		this.oy = oy;
	}

	@Override
	public void renderStencil(NovelEngine engine) {
		glBegin(GL_TRIANGLE_FAN);
		glVertex2i(ox, oy);
		for (int i = 0; i <= d; i++) {
			// 座標を計算
			double rate = (double) i / d;
			double x = r * Math.cos(2.0 * Math.PI * rate);
			double y = r * Math.sin(2.0 * Math.PI * rate);
			glVertex2d(x + ox, y + oy);
		}
		glEnd();

	}

}
