package hide92795.novelengine.queue;

import java.awt.image.BufferedImage;

import hide92795.novelengine.client.NovelEngine;

/**
 * イメージデータを使用可能にするためのキューデータです。
 *
 * @author hide92795
 */
public class QueueImage extends QueueData {
	/**
	 * 登録するイメージIDです。
	 */
	private int id;
	/**
	 * イメージデータを格納する <code>byte</code> 配列です。
	 */
	private byte[] image;
	/**
	 * イメージデータを格納する {@link java.awt.image.BufferedImage BufferedImage} です。
	 */
	private BufferedImage buf;

	/**
	 * <code>byte</code> 配列からイメージデータの登録を行うキューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータを格納している <code>byte</code> 配列
	 */
	public QueueImage(final NovelEngine engine, final int id, final byte[] image) {
		super(engine);
		this.id = id;
		this.image = image;
	}

	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} からイメージデータの登録を行うキューデータを作成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param id
	 *            イメージID
	 * @param image
	 *            イメージデータを格納している {@link java.awt.image.BufferedImage BufferedImage}
	 */
	public QueueImage(final NovelEngine engine, final int id, final BufferedImage image) {
		super(engine);
		this.id = id;
		this.buf = image;
	}

	@Override
	public final void execute() {
		if (buf == null) {
			engine().getImageManager().putTexture(id, image);
		} else {
			engine().getImageManager().putTexture(id, buf);
		}

	}

}
