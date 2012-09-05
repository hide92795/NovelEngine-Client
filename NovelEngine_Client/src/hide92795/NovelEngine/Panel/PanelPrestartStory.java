package hide92795.novelengine.panel;

import hide92795.novelengine.Properties;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.SettingManager;
import hide92795.novelengine.manager.SettingManager.Setting;

public class PanelPrestartStory extends Panel {
	private final int chapterId;

	public PanelPrestartStory(NovelEngine engine, int chapterid) {
		super(engine);
		this.chapterId = chapterid;
	}

	@Override
	public void update(int delta) {
		DataStory story = engine.storyManager.getStory(chapterId);
		if (story != null) {
			if (story.isDataLoaded()) {
				engine.startStory(chapterId);
			}
		}
	}

	@Override
	public void render(NovelEngine engine) {
		Properties prop = engine.settingManager.getProperties(SettingManager.VARIABLE_SETTING);
		int rgb = prop.getProperty(Setting.SETTING_PRESTART_BACKGROUND_COLOR);
		int r = (rgb & 0x00FF0000) >> 16;
		int g = (rgb & 0x0000FF00) >> 8;
		int b = (rgb & 0x000000FF);
		Renderer.renderColor(r / 255.0f, g / 255.0f, b / 255.0f);
	}

}
