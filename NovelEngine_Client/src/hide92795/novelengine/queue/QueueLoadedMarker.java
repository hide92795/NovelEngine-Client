package hide92795.novelengine.queue;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;

/**
 * 各種リソースが使用可能になったことを通知するためのキューデータです。
 *
 * @author hide92795
 */
public class QueueLoadedMarker extends QueueData {
	/**
	 * イメージデータを表す定数です。
	 */
	public static final int MAKER_IMAGE = 0;
	/**
	 * 文章データを表す定数です。
	 */
	public static final int MAKER_WORDS = 1;
	/**
	 * 音声データを表す定数です。
	 */
	public static final int MAKER_VOICE = 3;
	/**
	 * このキューデータによってロードされているチャプターのIDです。
	 */
	private final int chapterId;
	/**
	 * このキューデータがどのリソースの完了通知を行うかを保存します。
	 */
	private final int maker;

	/**
	 * 指定されたチャプター及びリソースが使用可能になったことを通知するキューデータを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param chapterId
	 *            このキューデータによってロードされているチャプターのID
	 * @param maker
	 *            このキューデータが完了通知を行うリソースの種類
	 */
	public QueueLoadedMarker(NovelEngine engine, int chapterId, int maker) {
		super(engine);
		this.chapterId = chapterId;
		this.maker = maker;
	}

	@Override
	public void execute() {
		DataStory story = engine().getStoryManager().getStory(chapterId);
		if (story != null) {
			switch (maker) {
			case MAKER_IMAGE:
				story.imageLoaded();
				break;
			case MAKER_WORDS:
				story.wordsLoaded();
				break;
			case MAKER_VOICE:
				story.voiceLoaded();
				break;
			default:
				break;
			}
		} else {
			engine().getQueueManager().enqueue(this);
		}
	}

}
