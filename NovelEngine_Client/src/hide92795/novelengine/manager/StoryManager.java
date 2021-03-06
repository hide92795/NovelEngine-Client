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
package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderStory;
import hide92795.novelengine.loader.item.DataStory;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ストーリーデータを管理するマネージャーです。
 *
 * @author hide92795
 */
public class StoryManager implements UncaughtExceptionHandler {
	/**
	 * 起動時に各種リソースを読み込むためのストーリーデータのIDを示します。
	 */
	public static final int CHAPTER_BOOT = -1;
	/**
	 * 一番最初に読み込まれるストーリーデータのIDを示します。
	 */
	public static final int CHAPTER_START = -2;
	/**
	 * メニューとして読み込まれるストーリーデータのIDを示します。
	 */
	public static final int CHAPTER_MENU = -3;
	/**
	 * ストーリーデータを格納するためのマップです。
	 */
	private ConcurrentHashMap<Integer, DataStory> stories;

	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトです。
	 */
	private NovelEngine engine;

	/**
	 * {@link hide92795.novelengine.manager.StoryManager StoryManager} のオブジェクトを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public StoryManager(NovelEngine engine) {
		stories = new ConcurrentHashMap<Integer, DataStory>(32, 0.75f, 2);
		this.engine = engine;
	}

	/**
	 * 指定されたストーリーを読み込みます。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param file
	 *            ストーリーデータが格納されたファイル
	 * @param id
	 *            ストーリーID
	 */
	public void loadStory(final NovelEngine engine, final File file, final int id) {
		Thread t = new Thread("ChapterLoader:" + file.getName()) {
			@Override
			public void run() {
				Logger.info("StoryID: " + file.getName() + " Load start");
				DataStory story = LoaderStory.load(engine, file, id);
				addStory(story);
				Logger.info("StoryID: " + file.getName() + " Load finish");
			}
		};
		t.setUncaughtExceptionHandler(this);
		t.start();
	}

	/**
	 * ストーリーを登録します。
	 *
	 * @param story
	 *            ストーリー
	 */
	public void addStory(DataStory story) {
		stories.put(story.getChapterId(), story);
	}

	/**
	 * 指定されたIDのストーリーを取得します。
	 *
	 * @param id
	 *            ストーリーID
	 * @return 指定されたIDのストーリー
	 */
	public DataStory getStory(int id) {
		return stories.get(id);
	}

	/**
	 * 指定されたストーリーIDのストーリーのロードが完了し、開始できる状態かどうかを返します。
	 *
	 * @param id
	 *            確認するストーリーID
	 * @return ロードが完了している場合は <code>true</code>
	 */
	public boolean isLoaded(int id) {
		DataStory story = getStory(id);
		if (story != null) {
			return story.isDataLoaded();
		}
		return false;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (e instanceof NovelEngineException) {
			engine.crash((NovelEngineException) e);
		} else {
			engine.crash(new NovelEngineException(e, "null"));
		}
	}
}
