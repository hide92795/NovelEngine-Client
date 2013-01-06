package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * ブロックを表すストーリーデータです。<br>
 * このストーリーデータの終了確認が行われることはありません。
 *
 * @author hide92795
 */
public class StoryBlock extends Story {
	/**
	 * このブロックが開始ブロックかどうかを表します。
	 */
	private final boolean startBlock;

	/**
	 * ブロックを表すストーリーデータを生成します。
	 *
	 * @param start
	 *            このブロックが開始ブロックかどうか
	 */
	public StoryBlock(final boolean start) {
		this.startBlock = start;
	}

	@Override
	public final void init(final PanelStory story) {
	}

	/**
	 * このブロックが開始ブロックかどうかを返します。
	 *
	 * @return このブロックが開始ブロックの場合は <code>true</code>
	 */
	public final boolean isStartBlock() {
		return startBlock;
	}
}
