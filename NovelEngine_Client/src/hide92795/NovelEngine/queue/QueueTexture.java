package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

public class QueueTexture extends Queue {
	private int id;
	private byte[] image;

	public QueueTexture(NovelEngine engine, int id, byte[] image) {
		super(engine);
		this.id = id;
		this.image = image;
	}

	@Override
	public void execute() {
		engine.imageManager.putTexture(id, image);
	}

}
