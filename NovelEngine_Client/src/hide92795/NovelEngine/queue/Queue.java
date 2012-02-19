package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

public abstract class Queue {
	public NovelEngine engine;
	public Queue(NovelEngine engine) {
		this.engine = engine;
	}
	public abstract void execute();
}
