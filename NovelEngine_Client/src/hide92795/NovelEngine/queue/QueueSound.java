package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

/**
 * 音楽データを使用可能にするためのキューデータです。
 *
 * @author hide92795
 */
public class QueueSound extends QueueData {
	/**
	 * 登録する音楽IDです。
	 */
	private int id;
	/**
	 * 音楽データを格納する <code>byte</code> 配列です。
	 */
	private byte[] sound;

	/**
	 * 音楽データの登録を行うキューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param id
	 *            音楽ID
	 * @param sound
	 *            音楽データを格納している <code>byte</code> 配列
	 */
	public QueueSound(final NovelEngine engine, final int id, final byte[] sound) {
		super(engine);
		this.id = id;
		this.sound = sound;

	}

	@Override
	public final void execute() {
		engine().getSoundManager().putSound(id, sound);
	}
}
