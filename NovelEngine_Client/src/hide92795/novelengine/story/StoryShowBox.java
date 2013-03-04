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
package hide92795.novelengine.story;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

/**
 * メッセージボックスの開閉を行うストーリーデータです。<br>
 * 今後大幅な修正が加わります。
 *
 * @author hide92795
 */
@Deprecated
public class StoryShowBox extends Story {
	private boolean show;
	private int height = 241;
	private int width = 700;
	private float now;
	private float speed = 0.05f;
	// 経過時間
	private int elapsedTime;
	private int totalTime = 400;
	private float alpha;

	public StoryShowBox(boolean show) {
		this.show = show;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		elapsedTime = 0;
		if (show) {
			now = height;
		} else {
			now = 0.0f;
			// story.setShowBox(false);
		}
	}

	private void skip(PanelStory story) {
		if (show) {
			now = 0.0f;
			// story.setShowBox(true);
			finish();
		} else {
			now = height;
			finish();
		}
	}

	// @Override
	// public void leftClick(PanelStory story, int x, int y) {
	// skip(story);
	// }
	//
	// @Override
	// public void keyPressed(PanelStory story, int eventKey) {
	// if (eventKey == Keyboard.KEY_RETURN) {
	// skip(story);
	// }
	// }

	@Override
	public void update(PanelStory panelStory, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(panelStory);
		}

		elapsedTime += delta;
		if (show) {
			float f = (float) Math.pow(speed, (float) elapsedTime / totalTime);
			alpha = 1.0f - f;

			now = (float) height * (f - speed);
			if (now <= 0f) {
				now = 0;
				// panelStory.setShowBox(true);
				finish();
			}
		} else {
			float f = (float) elapsedTime / totalTime;
			alpha = 1.0f - f;
			if (f >= 1.0f) {
				now = height;
				finish();
				return;
			}
			now = (float) height * f;
		}

	}

	@Override
	public void render(NovelEngine engine) {
		float x = engine.getDefaultWidth() - width;
		float y = (float) (engine.getDefaultHeight() - height + now);

		Texture t = engine.getImageManager().getImage(123456);
		Renderer.renderImage(t, alpha, x, y, x + t.getTextureWidth(), y + t.getTextureHeight());
	}
}
