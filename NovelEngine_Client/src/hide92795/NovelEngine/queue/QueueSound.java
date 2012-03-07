package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;

public class QueueSound extends Queue {

	private int id;
	private byte[] sound;

	public QueueSound(NovelEngine engine, int id, byte[] sound) {
		super(engine);
		this.id = id;
		this.sound = sound;
		
	}

	@Override
	public final void execute() {
		engine.soundManager.putSound(id, sound);

	}

}
