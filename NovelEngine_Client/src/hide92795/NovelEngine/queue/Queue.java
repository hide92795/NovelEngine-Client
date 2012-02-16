package hide92795.NovelEngine.queue;

import hide92795.NovelEngine.client.NovelEngine;

public abstract class Queue {
	public NovelEngine engine;
	public Queue(NovelEngine engine) {
		this.engine = engine;
	}
	public abstract void execute();
}
