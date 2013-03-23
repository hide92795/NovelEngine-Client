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

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.input.Keyboard;

/**
 * SEを再生するストーリーデータです。
 * 
 * @author hide92795
 */
public class StoryPlaySE extends Story {
	/**
	 * 再生するサウンドのURLです。
	 */
	private URL url;
	/**
	 * このサウンドを管理するための名前です。
	 */
	private String sourcename;

	/**
	 * SEを再生するストーリーデータを生成します。
	 * 
	 * @param id
	 *            再生を行うSEのID
	 */
	public StoryPlaySE(int id) {
		String filename = id + ".nea";
		sourcename = "se_" + id;
		File path = new File(NovelEngine.getCurrentDir(), "sound");
		File file = new File(path, filename);
		try {
			this.url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new NovelEngineException(e, "StoryPlaySE#CONSTRUCTOR");
		}
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			story.engine().getSoundManager().playAsSE(url, ".nea", sourcename);
			finish();
		}
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		skip(event.engine());
	}

	/**
	 * スキップ可能な場合にスキップを行います。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine) && sourcename != null) {
			engine.getSoundManager().stop(sourcename);
		}
	}
}
