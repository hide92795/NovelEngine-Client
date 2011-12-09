package hide92795.NovelEngine.EventQueue;

import hide92795.NovelEngine.Client.NovelEngine;

public class QueueSound extends Queue {

	private int id;
	private byte[] sound;

	public QueueSound(NovelEngine engine, int id, byte[] sound) {
		super(engine);
		this.id = id;
		this.sound = sound;
		
	}

	@Override
	public void execute() {
		engine.soundManager.putSound(id, sound);

	}

}
