package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.queue.QueueData;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 各種リソースをシステムで使用可能な形式に変換を行うクラスです。
 *
 * @author hide92795
 */
public class QueueManager {
	/**
	 * キューデータを保持するためのキューです。
	 */
	private ConcurrentLinkedQueue<QueueData> queue;

	/**
	 * {@link hide92795.novelengine.manager.QueueManager QueueManager}のオブジェクトを生成します。
	 */
	public QueueManager() {
		queue = new ConcurrentLinkedQueue<QueueData>();
	}

	/**
	 * キューデータをキューの最後尾に追加します。
	 *
	 * @param q
	 *            追加するキューデータ
	 */
	public final void enqueue(final QueueData q) {
		queue.offer(q);
	}

	/**
	 * キューからキューデータを1つ取り出し、処理します。
	 */
	public final void execute() {
		if (!queue.isEmpty()) {
			QueueData q = queue.poll();
			if (q == null) {
				return;
			}
			q.execute();
			Logger.debug("Queue was done!");
		}
	}

}
