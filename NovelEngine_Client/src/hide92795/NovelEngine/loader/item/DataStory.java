package hide92795.novelengine.loader.item;

import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryScene;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataStory extends Data {
	private int chapterId;
	// Key:シーンID Val:行番号
	public HashMap<Integer, Integer> scenes;
	private LinkedList<Story> commandLine;
	private int pos;
	private AtomicBoolean dataLoaded;
	private boolean imageLoaded;
	private boolean wordsLoaded = true;
	private boolean soundLoaded;
	private boolean voiceLoaded = true;

	public DataStory() {
		scenes = new HashMap<Integer, Integer>();
		commandLine = new LinkedList<Story>();
		dataLoaded = new AtomicBoolean(false);
	}

	public final int getChapterId() {
		return chapterId;
	}

	public final void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public final void addStory(Story story) {
		if (story instanceof StoryScene) {
			scenes.put(((StoryScene) story).getSceneId(), commandLine.size());
		}
		commandLine.add(story);
	}

	public final void reset() {
		pos = 0;
	}

	public final Story next() {
		Story s = commandLine.get(pos);
		pos++;
		return s;
	}

	public final void moveScene(int sceneId) {
		Integer integer = scenes.get(sceneId);
		this.pos = integer.intValue();
		System.out.println("DataStory.moveScene()");
		System.out.println(pos);
	}

	/**
	 * 画像・文字・BGM,SE・ボイスデータ全てのロードが終わっており、ストーリーの開始に問題がない場合はtrueを返します。
	 */
	public boolean isDataLoaded() {
		return dataLoaded.get();
	}

	/**
	 * 画像データのテクスチャ登録が完了したことをマークします。
	 */
	public void imageLoaded() {
		this.imageLoaded = true;
		checkLoaded();
	}

	/**
	 * 文字データのテクスチャ登録が完了したことをマークします。
	 */
	public void wordsLoaded() {
		this.wordsLoaded = true;
		checkLoaded();
	}

	/**
	 * BGM・SEのサウンド登録が完了したことをマークします。
	 */
	public void soundLoaded() {
		this.soundLoaded = true;
		checkLoaded();
	}

	/**
	 * ボイスデータのサウンド登録が完了したことをマークします。
	 */
	public void voiceLoaded() {
		this.voiceLoaded = true;
		checkLoaded();
	}

	/**
	 * 画像・文字・BGM,SE・ボイスデータ全てのロードが終わったか確認します。
	 */
	private void checkLoaded() {
		if (imageLoaded && wordsLoaded && soundLoaded && voiceLoaded) {
			dataLoaded.getAndSet(true);
		}
	}

	public void trace() {
		for (Story story : commandLine) {
			System.out.println(story.getClass());
		}
	}

}
