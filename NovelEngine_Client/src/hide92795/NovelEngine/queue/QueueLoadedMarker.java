package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;

public class QueueLoadedMarker extends Queue {
	public static final int MAKER_IMAGE = 0;
	public static final int MAKER_WORDS = 1;
	public static final int MAKER_SOUND = 2;
	public static final int MAKER_VOICE = 3;
	private final int chapterId;
	private final int maker;

	public QueueLoadedMarker(NovelEngine engine, int chapterId, int maker) {
		super(engine);
		this.chapterId = chapterId;
		this.maker = maker;
	}

	@Override
	public void execute() {
		DataStory story = engine.storyManager.getStory(chapterId);
		if (story != null) {
			switch (maker) {
			case MAKER_IMAGE:
				story.imageLoaded();
				break;
			case MAKER_WORDS:
				story.wordsLoaded();
				break;
			case MAKER_SOUND:
				story.soundLoaded();
				break;
			case MAKER_VOICE:
				story.voiceLoaded();
				break;
			}
		} else {
			engine.queue.offer(this);
		}
	}

}
