//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
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
	public void enqueue(QueueData q) {
		queue.offer(q);
	}

	/**
	 * キューからキューデータを1つ取り出し、処理します。
	 */
	public void execute() {
		if (!queue.isEmpty()) {
			long l1 = System.nanoTime();
			QueueData q = queue.poll();
			if (q == null) {
				return;
			}
			q.execute();
			long l2 = System.nanoTime();
			Logger.debug("Queue was done! : " + q.getClass() + " at " + (l2 - l1));
		}
	}

}
