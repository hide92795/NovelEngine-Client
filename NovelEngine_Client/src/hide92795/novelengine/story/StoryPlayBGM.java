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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;

/**
 * BGMの再生を行うストーリーデータです。
 *
 * @author hide92795
 */
public class StoryPlayBGM extends Story {
	/**
	 * 再生するサウンドのURLです。
	 */
	private URL url;

	/**
	 * BGMの再生を行うストーリーデータを生成します。
	 *
	 * @param id
	 *            再生を行う音楽のID
	 */
	public StoryPlayBGM(int id) {
		String filename = id + ".nea";
		File path = new File(NovelEngine.getCurrentDir(), "sound");
		File file = new File(path, filename);
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new NovelEngineException(e, "StoryPlayBGM#CONSTRUCTOR");
		}
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			story.engine().getSoundManager().playAsBGM(url, ".nea");
			finish();
		}
	}
}
