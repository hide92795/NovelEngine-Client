package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

public class StoryPlayBGM extends Story {
	private final int id;
	private boolean finish;

	public StoryPlayBGM(int id) {
		this.id = id;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			story.playBGM(id);
			finish = true;
		}
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
