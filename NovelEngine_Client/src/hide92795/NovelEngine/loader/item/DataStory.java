package hide92795.novelengine.loader.item;

import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryScene;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * １チャプター分のストーリーデータを格納及び管理を行うクラスです。<br>
 * ストーリーを開始するためには全てのリソースが使用可能である必要があります。
 *
 * @author hide92795
 */
public class DataStory {
	/**
	 * このストーリーのチャプターIDです。
	 */
	private final int chapterId;
	/**
	 * ストーリー内のシーンの位置を表すマップです<br>
	 * キーにシーンID、値にシーンの行番号が格納されます。
	 */
	private HashMap<Integer, Integer> scenes;
	/**
	 * このチャプター内のストーリーデータのリストです。
	 */
	private LinkedList<Story> commandLine;
	/**
	 * 次に {@link #next() }を呼び出した際に返されるストーリーデータの番号です。
	 */
	private int pos;
	/**
	 * 全てのリソースデータが使用可能になったかどうかを表します。
	 */
	private AtomicBoolean dataLoaded;
	/**
	 * 画像データのリソースデータが使用可能になったかどうかを表します。
	 */
	private boolean imageLoaded;
	/**
	 * 文章データのリソースデータが使用可能になったかどうかを表します。
	 */
	private boolean wordsLoaded;
	/**
	 * 音楽データのリソースデータが使用可能になったかどうかを表します。
	 */
	private boolean soundLoaded;
	/**
	 * 音声データのリソースデータが使用可能になったかどうかを表します。
	 */
	private boolean voiceLoaded;

	/**
	 * 指定されたチャプターIDのストーリーを作成します。
	 *
	 * @param chapterId
	 *            チャプターID
	 */
	public DataStory(final int chapterId) {
		this.chapterId = chapterId;
		scenes = new HashMap<Integer, Integer>();
		commandLine = new LinkedList<Story>();
		dataLoaded = new AtomicBoolean(false);
	}

	/**
	 * このストーリーのチャプターIDを取得します。
	 *
	 * @return チャプターID
	 */
	public final int getChapterId() {
		return chapterId;
	}

	/**
	 * このストーリーにストーリーデータを追加します。<br>
	 * ストーリーは追加した順に保存されます。
	 *
	 * @param story
	 *            追加するストーリーデータ
	 */
	public final void addStory(final Story story) {
		if (story instanceof StoryScene) {
			scenes.put(((StoryScene) story).getSceneId(), commandLine.size());
		}
		commandLine.add(story);
	}

	/**
	 * ストーリーの現在の位置を最初に戻します。
	 */
	public final void reset() {
		pos = 0;
	}

	/**
	 * 次のストーリーデータを読み込みます。
	 *
	 * @return 次のストーリーデータ
	 */
	public final Story next() {
		Story s = commandLine.get(pos);
		pos++;
		return s;
	}

	/**
	 * ストーリー内の指定されたシーンIDの位置に移動します。
	 *
	 * @param sceneId
	 *            移動先のシーンID
	 */
	public final void moveScene(final int sceneId) {
		Integer integer = scenes.get(sceneId);
		this.pos = integer.intValue();
	}

	/**
	 * 画像・文字・BGM,SE・ボイスデータ全てのロードが終わっており、ストーリーの開始に問題がない場合はtrueを返します。
	 *
	 * @return 読み込みが終わっているか
	 */
	public final boolean isDataLoaded() {
		return dataLoaded.get();
	}

	/**
	 * 画像データのテクスチャ登録が完了したことをマークします。
	 */
	public final void imageLoaded() {
		this.imageLoaded = true;
		checkLoaded();
	}

	/**
	 * 文字データのテクスチャ登録が完了したことをマークします。
	 */
	public final void wordsLoaded() {
		this.wordsLoaded = true;
		checkLoaded();
	}

	/**
	 * BGM・SEのサウンド登録が完了したことをマークします。
	 */
	public final void soundLoaded() {
		this.soundLoaded = true;
		checkLoaded();
	}

	/**
	 * ボイスデータのサウンド登録が完了したことをマークします。
	 */
	public final void voiceLoaded() {
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

	/**
	 * ストーリー内に保存されたストーリーデータの一覧を出力します。
	 */
	public final void trace() {
		for (Story story : commandLine) {
			System.out.println(story.getClass());
		}
	}

}
