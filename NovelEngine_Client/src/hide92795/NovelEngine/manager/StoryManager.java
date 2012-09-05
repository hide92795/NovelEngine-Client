package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderStory;
import hide92795.novelengine.loader.item.DataStory;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class StoryManager {
	private ConcurrentHashMap<Integer, DataStory> stories;

	public StoryManager() {
		stories = new ConcurrentHashMap<Integer, DataStory>(32, 0.75f, 2);
	}

	public void loadStory(final NovelEngine engine, final File file, final int id) {
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

	public DataStory getStory(int id) {
		return stories.get(id);
	}
}
