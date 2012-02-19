package hide92795.novelengine;

import hide92795.novelengine.queue.Queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueHandler {
	private ConcurrentLinkedQueue<Queue> queue;

	public QueueHandler() {
		queue = new ConcurrentLinkedQueue<Queue>();
	}

	public void offer(Queue q) {
		queue.offer(q);
	}

	public void execute() {
		if (!queue.isEmpty()) {
			Queue q = queue.poll();
			if (q == null)
				return;
			q.execute();
			System.out.println("Queue was done!");
		}
	}

}
