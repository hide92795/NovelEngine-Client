package hide92795.novelengine.figure;

import static org.lwjgl.opengl.GL11.*;
import hide92795.novelengine.client.NovelEngine;

public class Figure_Triangle extends Figure {
	private double[] p1;
	private double[] p2;
	private double[] p3;

	public Figure_Triangle(double[] p1, double[] p2, double[] p3) {
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
