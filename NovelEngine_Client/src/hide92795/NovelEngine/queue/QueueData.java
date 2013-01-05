package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

/**
 * キューデータを表す抽象クラスです。
 *
 * @author hide92795
 */
public abstract class QueueData {
	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトを表します。
	 */
	private NovelEngine engine;

	/**
	 * キューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public QueueData(final NovelEngine engine) {
		this.engine = engine;
	}

	/**
	 * キューを実行します。
	 */
	public abstract void execute();

	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトを返します。
	 *
	 * @return engine 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public final NovelEngine engine() {
		return engine;
	}
}
