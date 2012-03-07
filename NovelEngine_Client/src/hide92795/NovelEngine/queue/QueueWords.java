package hide92795.novelengine.queue;

import java.awt.Color;
import java.awt.image.BufferedImage;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

public class QueueWords extends Queue {
	private int id;
	private String words;
	private int chapterId;

	private int[] ids;

	public QueueWords(NovelEngine engine, int chapterId, int id, String words) {
		super(engine);
		this.chapterId = chapterId;
		this.id = id;
		this.words = words;
	}

	@Override
	public final void execute() {
		BufferedImage[] bi = Renderer.drawEdgedText(words, Color.white,
				Color.black);
		ids = new int[bi.length];
		for (int i = 0; i < bi.length; i++) {
			BufferedImage image = bi[i];
			StringBuilder sb = new StringBuilder();
			sb.append("_Words_");
			sb.append(chapterId);
			sb.append("_");
			sb.append(id);
			sb.append("_");
			sb.append(i);
			int imageId = sb.toString().hashCode();
			ids[i] = imageId;
			QueueTexture q = new QueueTexture(engine, imageId, image);
			engine.queue.offer(q);
		}
	}

	public final int getId() {
		return id;
	}

	public final int getChapterId() {
		return chapterId;
	}

	public final int[] getIds() {
		return ids;
	}

}
