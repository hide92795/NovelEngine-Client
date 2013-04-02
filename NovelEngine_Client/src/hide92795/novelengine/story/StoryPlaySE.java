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
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
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
	 * このサウンドの識別子
	 */
	private String identifier;
	/**
	 * このサウンドが再生中かどうかを表します。<br>
	 * 設定によりサウンドの再生を待機しない場合は常に false です。
	 */
	private boolean playing;

	/**
	 * SEを再生するストーリーデータを生成します。
	 * 
	 * @param id
	 *            再生を行うSEのID
	 */
	public StoryPlaySE(int id) {
		String filename = id + ".nea";
		identifier = filename;
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
		playing = false;
		sourcename = null;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (playing) {
			if (!story.engine().getSoundManager().isPlaying(sourcename)) {
				playing = false;
				finish();
			}
		} else if (!isFinish()) {
			sourcename = story.engine().getSoundManager().playAsSE(url, identifier);
			if (story.engine().getConfigurationManager()
					.getValue(ConfigurationManager.VARIABLE_RENDER, Setting.RENDER_WAIT_SE_FINISHED) == 1) {
				playing = true;
			} else {
				finish();
			}
		}
	}

	@Override
	public void dispose(PanelStory story) {
		if (playing
				&& story.engine().getConfigurationManager()
						.getValue(ConfigurationManager.VARIABLE_RENDER, Setting.RENDER_STOP_SE_SKIPPED) == 1) {
			story.engine().getSoundManager().stop(sourcename);
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
