package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderStory;
import hide92795.novelengine.loader.item.DataStory;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ストーリーデータを管理するマネージャーです。
 *
 * @author hide92795
 */
public class StoryManager {
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
	 * {@link hide92795.novelengine.manager.StoryManager StoryManager} のオブジェクトを生成します。
	 */
	public StoryManager() {
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
				try {
					Logger.info("StoryID: " + file.getName() + " Load start");
					DataStory story = LoaderStory.load(engine, file, id);
					stories.put(id, story);
					Logger.info("StoryID: " + file.getName() + " Load finish");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
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
}
