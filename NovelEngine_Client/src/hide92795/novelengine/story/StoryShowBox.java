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

import hide92795.novelengine.box.Box;
import hide92795.novelengine.box.BoxProcessListener;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

/**
 * メッセージボックスの開閉を行うストーリーデータです。<br>
 *
 * @author hide92795
 */
public class StoryShowBox extends Story implements BoxProcessListener {
	/**
	 * このストーリーデータがメッセージボックスの表示を行うかどうか。
	 */
	private boolean show;

	/**
	 * メッセージボックスの開閉を行うストーリーデータを生成します。
	 *
	 * @param show
	 *            メッセージボックスを表示する場合はtrue
	 */
	public StoryShowBox(boolean show) {
		this.show = show;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		int mode;
		if (show) {
			mode = Box.SHOWING;
		} else {
			mode = Box.HIDING;
		}
		Box currentBox = story.engine().getBoxManager().getCurrentBox();
		currentBox.setMode(mode);
		currentBox.setListener(this);
	}

	/**
	 * メッセージボックスの開閉をスキップします。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine)) {
			if (show) {
				engine.getBoxManager().getCurrentBox().setMode(Box.SHOWED);
				finish();
			} else {
				engine.getBoxManager().getCurrentBox().setMode(Box.HIDED);
				finish();
			}
		}
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
		skip(event.engine());
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			skip(story.engine());
		}
	}

	@Override
	public void onProcessFinish() {
		finish();
	}
}
