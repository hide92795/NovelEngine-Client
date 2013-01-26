package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * ボタンを生成するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryButton extends Story {

	/**
	 * ボタンを生成するストーリーデータを生成します。
	 */
	public StoryButton() {
	}

	@Override
	public final void init(final PanelStory story) {
		resetFinish();
	}

	class ButtonData {

	}
}
