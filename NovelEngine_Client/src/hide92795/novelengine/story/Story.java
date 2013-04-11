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
package hide92795.novelengine.story;

import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.gui.listener.KeybordListener;
import hide92795.novelengine.gui.listener.MouseListener;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
import hide92795.novelengine.panel.PanelStory;

/**
 * ストーリを構成するパーツとして機能します。
 * 
 * 
 * @author hide92795
 */
public abstract class Story implements MouseListener, KeybordListener {

	/**
	 * ブロックの開始地点を表します。
	 * 
	 * @see StoryBlock
	 */
	public static final byte COMMAND_BLOCK_START = 0;
	/**
	 * ブロックの終了地点を表します。
	 * 
	 * @see StoryBlock
	 */
	public static final byte COMMAND_BLOCK_END = 1;
	/**
	 * シーンIDを設定するストーリーデータを表します。
	 * 
	 * @see StoryScene
	 */
	public static final byte COMMAND_SET_SCENEID = 2;
	/**
	 * チャプターをロードするストーリーデータを表します。
	 * 
	 * @see StoryLoadChapter
	 */
	public static final byte COMMAND_LOAD_CHAPTER = 3;
	/**
	 * チャプターを移動するストーリーデータを表します。
	 * 
	 * @see StoryMoveChapter
	 */
	public static final byte COMMAND_MOVE_CHAPTER = 4;
	/**
	 * 背景を変更するストーリーデータを表します。
	 * 
	 * @see StoryChangeBackGround
	 */
	public static final byte COMMAND_SET_BACKGROUND = 5;
	/**
	 * キャラクターを変更するストーリーデータを表します。
	 * 
	 * @see StoryChangeCharacter
	 */
	public static final byte COMMAND_SET_CHARACTER = 6;
	/**
	 * ボイスの再生を行うストーリーデータを表します。
	 * 
	 * @see StoryVoice
	 */
	public static final byte COMMAND_VOICE = 7;
	/**
	 * 背景エフェクトを実行するストーリーデータを表します。
	 * 
	 * @see StoryBackGroundEffect
	 */
	public static final byte COMMAND_EFFECT_CHARACTER = 8;
	/**
	 * 背景の範囲を変更するストーリーデータを表します。
	 * 
	 * @see StoryChangeBackGroundFigure
	 */
	public static final byte COMMAND_SET_BACKGROUND_FIGURE = 9;
	/**
	 * 文章を表示するストーリーデータを表します。
	 * 
	 * @see StoryShowWords
	 */
	public static final byte COMMAND_SHOW_WORDS = 10;
	/**
	 * ボタンを表示するストーリーデータを表します。
	 * 
	 * @see StoryButton
	 */
	public static final byte COMMAND_MAKE_BUTTON = 11;
	/**
	 * 条件分岐を行うストーリーデータを表します。
	 * 
	 * @see StoryIF
	 */
	public static final byte COMMAND_IF = 12;
	/**
	 * BGMを再生するストーリーデータを表します。
	 * 
	 * @see StoryPlayBGM
	 */
	public static final byte COMMAND_PLAY_BGM = 13;
	/**
	 * BGMを停止するストーリーデータを表します。
	 * 
	 * @see StoryStopBGM
	 */
	public static final byte COMMAND_STOP_BGM = 14;
	/**
	 * SEを再生するストーリーデータを表します。
	 * 
	 * @see StoryPlaySE
	 */
	public static final byte COMMAND_PLAY_SE = 15;
	/**
	 * ボックスを表示するストーリーデータを表します。
	 * 
	 * @see StoryShowBox
	 */
	public static final byte COMMAND_SHOW_BOX = 16;
	/**
	 * ボックスを隠すストーリーデータを表します。
	 * 
	 * @see StoryShowBox
	 */
	public static final byte COMMAND_HIDE_BOX = 17;
	/**
	 * 変数を設定するストーリーデータを表します。
	 * 
	 * @see StoryAssignment
	 */
	public static final byte COMMAND_SET_VARIABLE = 18;
	/**
	 * 背景色を変更するストーリーデータを表します。
	 * 
	 * @see StoryChangeBackGroundColor
	 */
	public static final byte COMMAND_SET_BACKGROUND_COLOR = 19;
	/**
	 * 背景エフェクトを実行するストーリーデータを表します。
	 * 
	 * @see StoryBackGroundEffect
	 */
	public static final byte COMMAND_EFFECT_BACKGROUND = 20;
	/**
	 * 乱数を発生させるストーリーデータを表します。
	 * 
	 * @see StoryRandom
	 */
	public static final byte COMMAND_RANDOM = 21;
	/**
	 * 計算を行うストーリーデータを表します。
	 * 
	 * @see StoryCalculation
	 */
	public static final byte COMMAND_CALCULATION = 22;
	/**
	 * ゲームを終了するストーリーデータを表します。
	 * 
	 * @see StoryExit
	 */
	public static final byte COMMAND_EXIT = 23;
	/**
	 * 指定時間待機するストーリーデータを表します。
	 * 
	 * @see StoryWait
	 */
	public static final byte COMMAND_WAIT = 24;
	/**
	 * このストーリーデータの処理が終了したかどうかを表します。
	 */
	private boolean finish;

	/**
	 * このストーリーデータの処理が終了したかどうかを返します。
	 * 
	 * @return 処理が終了している場合は <code>true</code>
	 */
	public final boolean isFinish() {
		return finish;
	}

	/**
	 * ストーリーデータの処理が終了した時に呼びます。
	 */
	protected final void finish() {
		finish = true;
	}

	/**
	 * 終了フラグを元に戻します。
	 */
	protected final void resetFinish() {
		finish = false;
	}

	/**
	 * ストーリーデータを初期化します。
	 * 
	 * @param story
	 *            このストーリーデータが含まれるストーリーを実行している {@link hide92795.novelengine.panel.PanelStory PanelStory} オブジェクト
	 */
	public abstract void init(final PanelStory story);

	/**
	 * ストーリーデータを更新します。
	 * 
	 * @param story
	 *            現在、ストーリーを実行している {@link hide92795.novelengine.panel.PanelStory PanelStory} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public void update(final PanelStory story, final int delta) {
	}

	/**
	 * 描画を行います。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public void render(final NovelEngine engine) {
	}

	/**
	 * システム設定からストーリーデータをスキップ可能かどうかを取得します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * 
	 * @return ストーリーデータをスキップ可能な場合は <code>true</code>
	 */
	public final boolean canSkip(final NovelEngine engine) {
		Properties properties = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
		int i = properties.getProperty(Setting.RENDER_SKIPPABLE);
		return i == 1;
	}

	/**
	 * このストーリーデータがパネルによる処理リストから削除される時に呼ばれます。
	 * 
	 * @param story
	 *            現在、ストーリーを実行している {@link hide92795.novelengine.panel.PanelStory PanelStory} オブジェクト
	 */
	public void dispose(final PanelStory story) {
	}

	@Override
	public void onLeftClickStart(final MouseEvent event) {
	}

	@Override
	public void onRightClickStart(final MouseEvent event) {
	}

	@Override
	public void onLeftClickFinish(final MouseEvent event) {
	}

	@Override
	public void onRightClickFinish(final MouseEvent event) {
	}

	@Override
	public void onKeyPressed(final NovelEngine engine, final int key) {
	}

	@Override
	public void onKeyReleased(final NovelEngine engine, final int key) {
	}
}
