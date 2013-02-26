package hide92795.novelengine.background.figure;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import hide92795.novelengine.background.Figure;
import hide92795.novelengine.client.NovelEngine;

/**
 * 画面全体を描画範囲とする{@link hide92795.novelengine.background.Figure Figure}です
 *
 * @author hide92795
 */
public class Figure_EntireScreen extends Figure {
	@Override
	public void renderStencil(NovelEngine engine) {
		int width = engine.getDefaultWidth();
		int height = engine.getDefaultHeight();
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
