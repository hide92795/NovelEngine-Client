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
	 * 開始ブロックを表すストーリーデータです。
	 */
	public static final StoryBlock BLOCKSTART;
	/**
	 * 終了ブロックを表すストーリーデータです。
	 */
	public static final StoryBlock BLOCKEND;

	static {
		BLOCKSTART = new StoryBlock(true);
		BLOCKEND = new StoryBlock(false);
	}

	/**
	 * このブロックが開始ブロックかどうかを表します。
	 */
	private final boolean startBlock;

	/**
	 * ブロックを表すストーリーデータを生成します。<br>
	 * このストーリーデータはマーカーとしての機能を行うだけなので必要以上に生成されるべきではありません。
	 *
	 * @param start
	 *            このブロックが開始ブロックかどうか
	 *
	 * @see StoryBlock#BLOCKSTART
	 * @see StoryBlock#BLOCKEND
	 */
	private StoryBlock(boolean start) {
		this.startBlock = start;
	}

	@Override
	public void init(PanelStory story) {
	}

	/**
	 * このブロックが開始ブロックかどうかを返します。
	 *
	 * @return このブロックが開始ブロックの場合は <code>true</code>
	 */
	public boolean isStartBlock() {
		return startBlock;
	}
}
