package hide92795.novelengine.loader;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.queue.QueueImage;
import hide92795.novelengine.queue.QueueLoadedMarker;
import hide92795.novelengine.queue.QueueSound;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoaderResource extends Loader {
	private NovelEngine engine;
	private int chapterId;
	private ConcurrentLinkedQueue<Integer> imageQueue;
	private ConcurrentLinkedQueue<Integer> wordsQueue;
	private ConcurrentLinkedQueue<Integer> soundQueue;
	private ConcurrentLinkedQueue<Integer> voiceQueue;
	private Thread imageLoadThread;
	private Thread wordsLoadThread;
	private Thread soundLoadThread;
	private Thread voiceLoadThread;

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
		imageLoadThread.start();
		wordsLoadThread.start();
		soundLoadThread.start();
		voiceLoadThread.start();
	}

	public void loadImage(int id) {
		if (!engine.imageManager.isLoaded(id)) {
			imageQueue.add(id);
		}
	}

	public void loadWords(int id, String words) {

	}

	public void loadSound(int id) {
		if (!engine.soundManager.isLoaded(id)) {
			soundQueue.add(id);
		}
	}

	public void loadVoice(int id) {
		voiceQueue.add(id);
	}

	private class ImageLoader implements Runnable {
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

		private void loadImage(int id) {
			byte[] data = LoaderImage.load(id);
			QueueImage q = new QueueImage(NovelEngine.theEngine, id, data);
			engine.queue.offer(q);
		}

	}

	private class WordsLoader implements Runnable {

		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			loadFinish(QueueLoadedMarker.MAKER_WORDS);
		}

	}

	private class SoundLoader implements Runnable {

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
						loadImage(id);
					}
				}
			}

		}

		private void loadImage(int id) {
			byte[] data = LoaderSound.load(id);
			QueueSound q = new QueueSound(NovelEngine.theEngine, id, data);
			engine.queue.offer(q);
		}

	}

	private class VoiceLoader implements Runnable {

		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			loadFinish(QueueLoadedMarker.MAKER_VOICE);
		}

	}

	public void loadFinish(int maker) {
		QueueLoadedMarker q = new QueueLoadedMarker(engine, chapterId, maker);
		engine.queue.offer(q);
	}
}