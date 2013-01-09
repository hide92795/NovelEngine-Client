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
	 * 一番最初に読み込まれるストーリーデータのIDを示します。
	 */
	public static final int CHAPTER_START = "start".hashCode();
	/**
	 * メニューとして読み込まれるストーリーデータのIDを示します。
	 */
	public static final int CHAPTER_MENU = "menu".hashCode();
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
	public StoryManager(final NovelEngine engine) {
		this.engine = engine;
		stories = new ConcurrentHashMap<Integer, DataStory>(32, 0.75f, 2);
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
	public final void loadStory(final NovelEngine engine, final File file, final int id) {
		Thread t = new Thread("ChapterLoader:" + file.getName()) {
			@Override
			public void run() {
				Logger.info("StoryID: " + file.getName() + " Load start");
				DataStory story = LoaderStory.load(engine, file, id);
				stories.put(id, story);
				Logger.info("StoryID: " + file.getName() + " Load finish");
			}
		};
		t.setUncaughtExceptionHandler(this);
		t.start();
	}

	/**
	 * 指定されたIDのストーリーを取得します。
	 *
	 * @param id
	 *            ストーリーID
	 * @return 指定されたIDのストーリー
	 */
	public final DataStory getStory(final int id) {
		return stories.get(id);
	}

	/**
	 * 指定されたストーリーIDのストーリーのロードが完了し、開始できる状態かどうかを返します。
	 *
	 * @param id
	 *            確認するストーリーID
	 * @return ロードが完了している場合は <code>true</code>
	 */
	public final boolean isLoaded(final int id) {
		DataStory story = getStory(id);
		if (story != null) {
			return story.isDataLoaded();
		}
		return false;
	}

	@Override
	public final void uncaughtException(final Thread t, final Throwable e) {
		if (e instanceof NovelEngineException) {
			engine.crash((NovelEngineException) e);
		} else {
			engine.crash(new NovelEngineException(e, "null"));
		}
	}
}
