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
