package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.queue.QueueImage;
import hide92795.novelengine.queue.QueueLoadedMarker;
import hide92795.novelengine.queue.QueueSound;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 各種リソースの読み込みを非同期で行うために読み込み専用スレッドにキューの登録、実行を行うクラスです。
 *
 * @author hide92795
 */
public class LoaderResource extends Loader implements UncaughtExceptionHandler {
	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトです。
	 */
	private NovelEngine engine;
	/**
	 * リソースの読み込みを行なっているチャプターのIDです。
	 */
	private int chapterId;
	/**
	 * 画像データの読み込みキューデータを格納するキューです。
	 */
	private ConcurrentLinkedQueue<Integer> imageQueue;
	/**
	 * 文章データの読み込みキューデータを格納するキューです。
	 */
	private ConcurrentLinkedQueue<Integer> wordsQueue;
	/**
	 * 音楽データの読み込みキューデータを格納するキューです。
	 */
	private ConcurrentLinkedQueue<Integer> soundQueue;
	/**
	 * 音声データの読み込みキューデータを格納するキューです。
	 */
	private ConcurrentLinkedQueue<Integer> voiceQueue;
	/**
	 * 画像データの読み込みキューを実行するスレッドです。
	 */
	private Thread imageLoadThread;
	/**
	 * 文章データの読み込みキューを実行するスレッドです。
	 */
	private Thread wordsLoadThread;
	/**
	 * 音楽データの読み込みキューを実行するスレッドです。
	 */
	private Thread soundLoadThread;
	/**
	 * 音声データの読み込みキューを実行するスレッドです。
	 */
	private Thread voiceLoadThread;

	/**
	 * 指定されたチャプターのリソース読み込みをするマネージャーの準備、起動をします。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 *
	 * @param chapterId
	 *            読み込みを行うチャプターID
	 */
	public LoaderResource(NovelEngine engine, int chapterId) {
		this.engine = engine;
		this.chapterId = chapterId;
		imageQueue = new ConcurrentLinkedQueue<Integer>();
		wordsQueue = new ConcurrentLinkedQueue<Integer>();
		soundQueue = new ConcurrentLinkedQueue<Integer>();
		voiceQueue = new ConcurrentLinkedQueue<Integer>();
		imageLoadThread = new Thread(new ImageLoader(), "ImageLoader - ChapterID:" + chapterId);
		wordsLoadThread = new Thread(new WordsLoader(), "WordsLoader - ChapterID:" + chapterId);
		soundLoadThread = new Thread(new SoundLoader(), "SoundLoader - ChapterID:" + chapterId);
		voiceLoadThread = new Thread(new VoiceLoader(), "VoiceLoader - ChapterID:" + chapterId);
		imageLoadThread.setUncaughtExceptionHandler(this);
		wordsLoadThread.setUncaughtExceptionHandler(this);
		soundLoadThread.setUncaughtExceptionHandler(this);
		voiceLoadThread.setUncaughtExceptionHandler(this);
		imageLoadThread.start();
		wordsLoadThread.start();
		soundLoadThread.start();
		voiceLoadThread.start();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		loadImage(0);
		loadWords(0, null);
		loadSound(0);
		loadVoice(0);
		if (e instanceof NovelEngineException) {
			engine.crash((NovelEngineException) e);
		} else {
			engine.crash(new NovelEngineException(e, String.valueOf(chapterId)));
		}
	}

	/**
	 * 指定されたIDの画像データが読み込まれていない場合はキューに追加します。
	 *
	 * @param id
	 *            読み込む画像データのID
	 */
	public void loadImage(int id) {
		if (!engine.getImageManager().isLoaded(id)) {
			imageQueue.add(id);
		}
	}

	/**
	 * 指定されたIDの文章データが作成されていない場合はキューに追加します。
	 *
	 * @param id
	 *            作成する文章ID
	 * @param words
	 *            作成する文字列
	 */
	public void loadWords(int id, String words) {
		// TODO
		wordsQueue.add(id);
	}

	/**
	 * 指定されたIDの音楽データが読み込まれていない場合はキューに追加します。
	 *
	 * @param id
	 *            読み込む音楽データのID
	 */
	public void loadSound(int id) {
		if (!engine.getSoundManager().isLoaded(id)) {
			soundQueue.add(id);
		}
	}

	/**
	 * 指定されたIDの音声データが読み込まれていない場合はキューに追加します。
	 *
	 * @param id
	 *            読み込む音声データのID
	 */
	public void loadVoice(int id) {
		voiceQueue.add(id);
	}

	/**
	 * キューに登録されている画像IDのデータを読み込み、システムで使用可能にするために変換用のキューにデータを受け渡すクラスです。<br>
	 * また、読み込みが完了した場合は読み込み完了のフラグを変換用のキューに送ります。
	 *
	 * @author hide92795
	 */
	private class ImageLoader implements Runnable {
		/**
		 * 指定されたチャプターID上で使用される全ての画像データの読み込みが終わった時にtrueです。
		 */
		private boolean finish = false;

		@Override
		public void run() {
			while (!finish) {
				Integer i = imageQueue.poll();
				if (i != null) {
					int id = i.intValue();
					if (id == 0) {
						loadFinish(QueueLoadedMarker.MAKER_IMAGE);
						finish = true;
					} else {
						loadImage(id);
					}
				}
			}

		}

		/**
		 * 外部ファイルから画像データを読み込み、変換用のキューにデータを受け渡します。
		 *
		 * @param id
		 *            読み込む画像の画像ID
		 */
		private void loadImage(int id) {
			byte[] data = LoaderImage.load(id);
			QueueImage q = new QueueImage(engine, id, data);
			engine.getQueueManager().enqueue(q);
		}

	}

	/**
	 * ゲーム上で使用する文章の画像を作成し、システムで使用可能にするために変換用のキューにデータを受け渡すクラスです。<br>
	 * また、読み込みが完了した場合は読み込み完了のフラグを変換用のキューに送ります。
	 *
	 * @author hide92795
	 */
	private class WordsLoader implements Runnable {
		/**
		 * 指定されたチャプターID上で使用される全ての文章データの作成が終わった時にtrueです。
		 */
		private boolean finish = false;

		@Override
		public void run() {
			while (!finish) {
				Integer i = wordsQueue.poll();
				if (i != null) {
					int id = i.intValue();
					if (id == 0) {
						loadFinish(QueueLoadedMarker.MAKER_WORDS);
						finish = true;
					} else {
						loadWords(id);
					}
				}
			}

		}

		/**
		 * 外部ファイルから音楽データを読み込み、変換用のキューにデータを受け渡します。
		 *
		 * @param id
		 *            読み込む音楽の音楽ID
		 */
		private void loadWords(int id) {

		}
	}

	/**
	 * キューに登録されている音楽IDのデータを読み込み、システムで使用可能にするために変換用のキューにデータを受け渡すクラスです。<br>
	 * また、読み込みが完了した場合は読み込み完了のフラグを変換用のキューに送ります。
	 *
	 * @author hide92795
	 */
	private class SoundLoader implements Runnable {
		/**
		 * 指定されたチャプターID上で使用される全ての音楽データの読み込みが終わった時にtrueです。
		 */
		private boolean finish = false;

		@Override
		public void run() {
			while (!finish) {
				Integer i = soundQueue.poll();
				if (i != null) {
					int id = i.intValue();
					if (id == 0) {
						loadFinish(QueueLoadedMarker.MAKER_SOUND);
						finish = true;
					} else {
						loadSound(id);
					}
				}
			}

		}

		/**
		 * 外部ファイルから音楽データを読み込み、変換用のキューにデータを受け渡します。
		 *
		 * @param id
		 *            読み込む音楽の音楽ID
		 */
		private void loadSound(int id) {
			byte[] data = LoaderSound.load(id);
			QueueSound q = new QueueSound(engine, id, data);
			engine.getQueueManager().enqueue(q);
		}
	}

	/**
	 * キューに登録されている音声IDのデータを読み込み、、システムで使用可能にするために変換用のキューにデータを受け渡すクラスです。<br>
	 * また、読み込みが完了した場合は読み込み完了のフラグを変換用のキューに送ります。
	 *
	 * @author hide92795
	 */
	private class VoiceLoader implements Runnable {

		/**
		 * 指定されたチャプターID上で使用される全ての音声データの読み込みが終わった時にtrueです。
		 */
		private boolean finish = false;

		@Override
		public void run() {
			while (!finish) {
				Integer i = voiceQueue.poll();
				if (i != null) {
					int id = i.intValue();
					if (id == 0) {
						loadFinish(QueueLoadedMarker.MAKER_VOICE);
						finish = true;
					} else {
						loadImage(id);
					}
				}
			}

		}

		/**
		 * 外部ファイルから音声データを読み込み、変換用のキューにデータを受け渡します。
		 *
		 * @param id
		 *            読み込む音声の音声ID
		 */
		private void loadImage(int id) {
			byte[] data = LoaderSound.load(id);
			QueueSound q = new QueueSound(engine, id, data);
			engine.getQueueManager().enqueue(q);
		}
	}

	/**
	 * 各種類のリソースがシステム上で使用可能になったことをマークするためのキューデータを送ります。<br>
	 * 送られたキューデータはそのマーカーの種類が示すキューデータ群の最後に付けられ、 このマーカーが実行されるとマーカーの種類のデータが利用可能になったとみなします。
	 *
	 * @param maker
	 *            マーカーの種類
	 */
	public void loadFinish(int maker) {
		QueueLoadedMarker q = new QueueLoadedMarker(engine, chapterId, maker);
		engine.getQueueManager().enqueue(q);
	}
}