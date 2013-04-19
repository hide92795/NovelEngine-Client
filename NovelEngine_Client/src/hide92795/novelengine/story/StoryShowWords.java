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

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.words.EntityWords;
import hide92795.novelengine.words.WordsProcessListener;

import org.lwjgl.input.Keyboard;

/**
 * 文章の表示を行うストーリーデータです。
 * 
 * @author hide92795
 */
public class StoryShowWords extends Story implements WordsProcessListener {
	/**
	 * 表示を行う文章データです。
	 */
	private EntityWords words;
	/**
	 * 文章データ側の文字送りが終わったかどうかを表します。
	 */
	private boolean showFinish;

	/**
	 * 文章の表示を行うストーリーデータを作成します。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
	 * @param characterId
	 *            名前部分に表示するキャラクターのID
	 * @param words
	 *            表示を行う文章データ
	 */
	public StoryShowWords(int line, int characterId, EntityWords words) {
		super(line);
		this.words = words;
	}

	@Override
	public void init(PanelStory story) {
		showFinish = false;
		words.setListener(this);
		story.setWords(words);
		story.setInternalData(PanelStory.INTERNAL_DATA_WORDS_LINE, getLine());
	}

	/**
	 * メッセージボックスの開閉をスキップします。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine)) {
			if (showFinish) {
				finish();
			} else {
				words.forceFinish();
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
		// 表示終了
		showFinish = true;
	}

}
