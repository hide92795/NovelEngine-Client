package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

public class StoryStopBGM extends Story {
	private boolean finish;

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			story.stopBGM();
		}
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
