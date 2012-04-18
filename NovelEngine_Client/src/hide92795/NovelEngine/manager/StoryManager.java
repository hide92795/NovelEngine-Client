package hide92795.novelengine.manager;

import hide92795.novelengine.DataLoader;
import hide92795.novelengine.Logger;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.data.DataStory;

import java.util.HashMap;

public class StoryManager {
	protected HashMap<Integer, DataStory> stories;

	public StoryManager() {
		stories = new HashMap<Integer, DataStory>();
	}

	public void loadStory(final NovelEngine engine, final int id) {
		Thread t = new Thread("ChapterLoader:" + id) {
			@Override
			public void run() {
				try {
					Logger.info("StoryID: " + id + " Load start");
					String name = id + ".dat";
					DataStory story = (DataStory) DataLoader.loadData(engine,
							"st", name, DataLoader.DATA_STORY);
					synchronized (stories) {
						stories.put(id, story);
					}
					Logger.info("StoryID: " + id + " Load finish");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public DataStory getStory(int id) {
		synchronized (stories) {
			return stories.get(id);
		}
	}
}
