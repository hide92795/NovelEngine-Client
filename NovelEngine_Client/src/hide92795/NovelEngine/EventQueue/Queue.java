package hide92795.NovelEngine.EventQueue;

import hide92795.NovelEngine.Client.NovelEngine;

public abstract class Queue {
	public NovelEngine engine;
	public Queue(NovelEngine engine) {
		this.engine = engine;
	}
	public abstract void execute();
}
