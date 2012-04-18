package hide92795.novelengine.figure;

import static org.lwjgl.opengl.GL11.*;
import hide92795.novelengine.client.NovelEngine;

public class Figure_EntireScreen extends Figure {

	@Override
	public void renderStencil(NovelEngine engine) {
		int width = engine.width;
		int height = engine.height;
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(width, 0);
			glVertex2d(width, height);
			glVertex2d(0, height);
		}
		glEnd();
	}

}
