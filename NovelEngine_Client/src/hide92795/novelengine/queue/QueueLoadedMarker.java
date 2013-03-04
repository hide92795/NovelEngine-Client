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
