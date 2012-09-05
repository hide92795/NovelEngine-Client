package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.Utils;
import hide92795.novelengine.panel.PanelStory;

public class StoryRandom extends Story {
	private final byte varType;
	private final int varName;
	private final int num;
	private boolean finish;

	public StoryRandom(byte varType, int varName, int num) {
		this.varType = varType;
		this.varName = varName;
		this.num = num;
	}

	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			int randomValue = Utils.getRandom(num);
			Properties p = story.engine.settingManager.getProperties(varType);
			p.setProperty(varName, randomValue);
			finish = true;
		}
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
