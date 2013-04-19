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
import hide92795.novelengine.panel.PanelStory;

/**
 * 現在の状態を外部ファイルに保存し、再開できるようにするストーリーデータです。
 * 
 * @author hide92795
 */
public class StorySystemSave extends Story {
	/**
	 * 保存先のファイル番号です。
	 */
	private final int fileId;

	/**
	 * 現在の状態を外部ファイルに保存し、再開できるようにするストーリーデータを生成します。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
	 * @param fileId
	 *            保存先のファイル番号
	 */
	public StorySystemSave(int line, int fileId) {
		super(line);
		this.fileId = fileId;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			try {
				story.save(fileId);
			} catch (Exception e) {
				throw new NovelEngineException("セーブに失敗しました。", e, "-1");
			}
			finish();
		}
	}
}
