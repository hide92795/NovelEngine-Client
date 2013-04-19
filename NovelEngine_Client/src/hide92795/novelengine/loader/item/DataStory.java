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
	private int position;
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
	 * 音声データのリソースデータが使用可能になったかどうかを表します。
	 */
	private boolean voiceLoaded;

	/**
	 * 指定されたチャプターIDのストーリーを作成します。
	 * 
	 * @param chapterId
	 *            チャプターID
	 */
	public DataStory(int chapterId) {
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
	public int getChapterId() {
		return chapterId;
	}

	/**
	 * このストーリーにストーリーデータを追加します。<br>
	 * ストーリーは追加した順に保存されます。
	 * 
	 * @param story
	 *            追加するストーリーデータ
	 */
	public void addStory(Story story) {
		if (story instanceof StoryScene) {
			scenes.put(((StoryScene) story).getSceneId(), commandLine.size());
		}
		commandLine.add(story);
	}

	/**
	 * ストーリーの現在の位置を最初に戻します。
	 */
	public void reset() {
		position = 0;
	}

	/**
	 * 次のストーリーデータを読み込みます。
	 * 
	 * @return 次のストーリーデータ
	 */
	public Story next() {
		Story s = commandLine.get(position);
		position++;
		return s;
	}

	/**
	 * 現在のストーリーの位置を返します。
	 * 
	 * @return 現在のストーリーの位置
	 */
	public final int getCurrentLine() {
		return position - 1;
	}

	/**
	 * 現在のストーリーの位置を設定します
	 * 
	 * @param line
	 *            ストーリーの位置
	 */
	public final void setCurrentLine(int line) {
		this.position = line;
	}

	/**
	 * 指定位置のストーリーデータを取得します。
	 * 
	 * @param line
	 *            取得するストーリーデータの位置
	 * @return ストーリーデータ
	 */
	public final Story getStory(int line) {
		return commandLine.get(line);
	}

	/**
	 * ストーリー内の指定されたシーンIDの位置に移動します。
	 * 
	 * @param sceneId
	 *            移動先のシーンID
	 */
	public void moveScene(int sceneId) {
		Integer integer = scenes.get(sceneId);
		this.position = integer.intValue();
	}

	/**
	 * 画像・文字・BGM,SE・ボイスデータ全てのロードが終わっており、ストーリーの開始に問題がない場合はtrueを返します。
	 * 
	 * @return 読み込みが終わっているか
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
		if (imageLoaded && wordsLoaded && voiceLoaded) {
			dataLoaded.getAndSet(true);
		}
	}

	/**
	 * ストーリー内に保存されたストーリーデータの一覧を出力します。
	 */
	public void trace() {
		for (Story story : commandLine) {
			System.out.println(story.getClass());
		}
	}
}
