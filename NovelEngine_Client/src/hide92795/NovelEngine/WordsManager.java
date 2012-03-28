package hide92795.novelengine;

import hide92795.novelengine.queue.QueueWords;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WordsManager implements Runnable {
	private Thread looper;
	private boolean running;

	private ConcurrentLinkedQueue<QueueWords> queue;
	private HashMap<Integer, Words> chapters;

	public WordsManager() {
		// 同期化
		chapters = new HashMap<Integer, Words>();
		queue = new ConcurrentLinkedQueue<QueueWords>();
		looper = new Thread(this, "WordsMaker");
	}

	public void offer(QueueWords q) {
		queue.offer(q);
	}

	public void loop(boolean loop) {
		if (loop) {
			looper.start();
		} else {
			this.running = false;
		}
	}

	protected final void execute() {
		if (!queue.isEmpty()) {
			QueueWords q = queue.poll();
			if (q == null)
				return;
			// 画像作成
			q.execute();

			int chapterId = q.getChapterId();
			synchronized (chapters) {
				Words w = chapters.get(chapterId);
				if (w == null) {
					w = new Words(chapterId);
				}
				w.addWordsTextureIds(q.getId(), q.getIds());
				chapters.put(chapterId, w);
			}

			System.out.println("Make words!");
		}
	}

	private Words getWords(int chapterId) {
		synchronized (chapters) {
			Words w = chapters.get(chapterId);
			if (w == null) {
				w = new Words(chapterId);
				chapters.put(chapterId, w);
			}
			return w;
		}
	}

	public int[] getWordsTextureIds(int chapterId, int id) {
		return getWords(chapterId).getWordsTextureIds(id);
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			execute();
		}
	}

	class Words {
		private int chapterId;
		private LinkedHashMap<Integer, int[]> words;

		public Words(int chapterId) {
			this.chapterId = chapterId;
			words = new LinkedHashMap<Integer, int[]>(100);
		}

		public int[] getWordsTextureIds(int id) {
			return words.get(id);
		}

		public void addWordsTextureIds(int id, int[] texIds) {
			words.put(id, texIds);
		}

		@Override
		public int hashCode() {
			return chapterId;
		}
	}
}
