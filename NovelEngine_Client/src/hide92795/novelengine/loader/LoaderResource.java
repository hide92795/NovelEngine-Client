//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.FontManager.DataFont;
import hide92795.novelengine.queue.QueueImage;
import hide92795.novelengine.queue.QueueLoadedMarker;
import hide92795.novelengine.words.EntityWords;

import java.awt.image.BufferedImage;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.Map;

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
	private HashSet<Integer> imageQueue;
	/**
	 * 文章データの読み込みキューデータを格納するキューです。
	 */
	private HashSet<Object[]> wordsQueue;
	/**
	 * 音声データの読み込みキューデータを格納するキューです。
	 */
	private HashSet<Integer> voiceQueue;
	/**
	 * 画像データの読み込みキューを実行するスレッドです。
	 */
	private Thread imageLoadThread;
	/**
	 * 文章データの読み込みキューを実行するスレッドです。
	 */
	private Thread wordsLoadThread;
	/**
	 * 音声データの読み込みキューを実行するスレッドです。
	 */
	private Thread voiceLoadThread;

	/**
	 * 指定されたチャプターのリソース読み込みをするマネージャーの準備をします。
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
		imageQueue = new HashSet<Integer>();
		wordsQueue = new HashSet<Object[]>();
		voiceQueue = new HashSet<Integer>();
		imageLoadThread = new Thread(new ImageLoader(), "ImageLoader - ChapterID:" + chapterId);
		wordsLoadThread = new Thread(new WordsLoader(), "WordsLoader - ChapterID:" + chapterId);
		voiceLoadThread = new Thread(new VoiceLoader(), "VoiceLoader - ChapterID:" + chapterId);
		imageLoadThread.setUncaughtExceptionHandler(this);
		wordsLoadThread.setUncaughtExceptionHandler(this);
		voiceLoadThread.setUncaughtExceptionHandler(this);
	}

	/**
	 * 読み込みスレッドを起動し、ロードを行います。
	 */
	public void start() {
		imageLoadThread.start();
		wordsLoadThread.start();
		voiceLoadThread.start();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
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
	 * 配列に格納されているIDの画像データが読み込まれていない場合はキューに追加します。
	 * 
	 * @param ids
	 *            読み込む画像データのIDの配列
	 */
	public void loadImages(int[] ids) {
		for (int id : ids) {
			loadImage(id);
		}
	}

	/**
	 * 文章データの作成要求をキューに追加します。
	 * 
	 * @param words
	 *            作成したデータを保管する EntityWords
	 * @param words_str
	 *            文章の文字列
	 * @param initVariable
	 *            初期変数
	 */
	public void loadWords(EntityWords words, String words_str, Map<String, Integer> initVariable) {
		Object[] data = { words, words_str, initVariable };
		wordsQueue.add(data);
	}

	/**
	 * 名前用の画像データの作成要求をキューに追加します。
	 * 
	 * @param name
	 *            作成する名前
	 * @param imageId
	 *            作成した画像に割り当てる画像ID
	 * @param font
	 *            使用するフォント
	 */
	public void loadWords(String name, int imageId, DataFont font) {
		Object[] data = { name, imageId, font };
		wordsQueue.add(data);
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

		@Override
		public void run() {
			for (int id : imageQueue) {
				loadImage(id);
			}
			loadFinish(QueueLoadedMarker.MAKER_IMAGE);
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
		@Override
		public void run() {
			for (Object[] data : wordsQueue) {
				loadWords(data);
			}
			loadFinish(QueueLoadedMarker.MAKER_WORDS);
		}

		/**
		 * 文章データから画像を作成し、変換用のキューにデータを受け渡します。
		 * 
		 * @param data
		 *            読み込む音楽の音楽ID
		 */

		private void loadWords(Object[] data) {
			Object o = data[0];
			if (o instanceof EntityWords) {
				// 文章
				EntityWords words = (EntityWords) data[0];
				String words_str = (String) data[1];
				@SuppressWarnings("unchecked")
				Map<String, Integer> initVariable = (Map<String, Integer>) data[2];
				engine.getWordsManager().createWords(engine, words, words_str, initVariable);
			} else if (o instanceof String) {
				// 名前
				String name = (String) data[0];
				int imageId = (Integer) data[1];
				DataFont font = (DataFont) data[2];
				BufferedImage image = engine.getWordsManager().drawText(name, engine.getDefaultWidth(), font)[0];
				QueueImage q = new QueueImage(engine, imageId, image);
				engine.getQueueManager().enqueue(q);
			}
		}
	}

	/**
	 * キューに登録されている音声IDのデータを読み込み、、システムで使用可能にするために変換用のキューにデータを受け渡すクラスです。<br>
	 * また、読み込みが完了した場合は読み込み完了のフラグを変換用のキューに送ります。
	 * 
	 * @author hide92795
	 */
	private class VoiceLoader implements Runnable {
		@Override
		public void run() {
			for (int id : voiceQueue) {
				loadVoice(id);
			}
			loadFinish(QueueLoadedMarker.MAKER_VOICE);
		}

		/**
		 * 外部ファイルから音声データを読み込み、変換用のキューにデータを受け渡します。
		 * 
		 * @param id
		 *            読み込む音声の音声ID
		 */
		private void loadVoice(int id) {
			// byte[] data = LoaderSound.load(id);
			// QueueSound q = new QueueSound(engine, id, data);
			// engine.getQueueManager().enqueue(q);
		}
	}

	/**
	 * 各種類のリソースがシステム上で使用可能になったことをマークするためのキューデータを送ります。<br>
	 * 送られたキューデータはそのマーカーの種類が示すキューデータ群の最後に付けられ、 このマーカーが実行されるとマーカーの種類のデータが利用可能になったとみなします。
	 * 
	 * @param maker
	 *            マーカーの種類
	 */
	private void loadFinish(int maker) {
		QueueLoadedMarker q = new QueueLoadedMarker(engine, chapterId, maker);
		engine.getQueueManager().enqueue(q);
	}
}