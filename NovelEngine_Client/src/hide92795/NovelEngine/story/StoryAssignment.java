package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.panel.PanelStory;

public class StoryAssignment extends Story {
	private final byte varType;
	private final int varName;
	private final int value;
	private boolean finish;

	public StoryAssignment(byte varType, int varName, int value) {
		this.varType = varType;
		this.varName = varName;
		this.value = value;
	}
	
	@Override
	public void init(PanelStory story) {
		finish = false;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!finish) {
			Properties p = story.engine.settingManager.getProperties(varType);
			p.setProperty(varName, value);
			finish = true;
		}
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

}
