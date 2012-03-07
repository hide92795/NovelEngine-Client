package hide92795.novelengine.queue;

import java.awt.image.BufferedImage;

import hide92795.novelengine.client.NovelEngine;

public class QueueTexture extends Queue {
	private int id;
	private byte[] image;
	private BufferedImage buf;

	public QueueTexture(NovelEngine engine, int id, byte[] image) {
		super(engine);
		this.id = id;
		this.image = image;
	}
	
	public QueueTexture(NovelEngine engine, int id, BufferedImage image) {
		super(engine);
		this.id = id;
		this.buf = image;
	}

	@Override
	public final void execute() {
		if(image != null){
			engine.imageManager.putTexture(id, image);
		}else{
			engine.imageManager.putTexture(id, buf);
		}
		
	}

}
