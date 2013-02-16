package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * 指定時間待機するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryWait extends Story {
	/**
	 * 待機する時間を表します。
	 */
	private final int waitTimeMs;
	/**
	 * このストーリーデータの処理が始まってから経過した時間です。
	 */
	private int elapsedTime;

	/**
	 * 指定時間待機するストーリーデータを生成します。
	 *
	 * @param waitTimeMs
	 *            待機する時間
	 */
	public StoryWait(int waitTimeMs) {
		this.waitTimeMs = waitTimeMs;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		elapsedTime = 0;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			elapsedTime += delta;
			if (elapsedTime >= waitTimeMs) {
				finish();
			}
		}
	}

}
