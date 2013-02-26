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
	 * チャプターIDを設定するストーリーデータを表します。
	 */
	public static final byte COMMAND_SET_CHAPTERID = 2;
	/**
	 * シーンIDを設定するストーリーデータを表します。
	 *
	 * @see StoryScene
	 */
	public static final byte COMMAND_SET_SCENEID = 3;
	/**
	 * チャプターをロードするストーリーデータを表します。
	 *
	 * @see StoryLoadChapter
	 */
	public static final byte COMMAND_LOAD_CHAPTER = 4;
	/**
	 * チャプターを移動するストーリーデータを表します。
	 *
	 * @see StoryMoveChapter
	 */
	public static final byte COMMAND_MOVE_CHAPTER = 5;
	/**
	 * 背景を変更するストーリーデータを表します。
	 *
	 * @see StoryChangeBg
	 */
	public static final byte COMMAND_CHANGE_BG = 6;
	/**
	 * キャラクターを変更するストーリーデータを表します。
	 *
	 * @see TODO
	 */
	public static final byte COMMAND_CHANGE_CHARACTER = 7;
	/**
	 * キャラクターを移動するストーリーデータを表します。
	 *
	 * @see TODO
	 */
	public static final byte COMMAND_MOVE_CHARACTER = 8;
	/**
	 * するストーリーデータを表します。
	 *
	 * @see TODO
	 */
	public static final byte COMMAND_ACTION_CHARACTER = 9;
	/**
	 * CGを表示するストーリーデータを表します。
	 *
	 * @see TODO
	 */
	public static final byte COMMAND_SHOW_CG = 10;
	/**
	 * 文章を表示するストーリーデータを表します。
	 *
	 * @see TODO
	 */
	public static final byte COMMAND_SHOW_WORDS = 11;
	/**
	 * ボタンを表示するストーリーデータを表します。
	 *
	 * @see StoryButton
	 */
	public static final byte COMMAND_MAKE_BUTTON = 12;
	/**
	 * 条件分岐を行うストーリーデータを表します。
	 *
	 * @see StoryIF
	 */
	public static final byte COMMAND_IF = 13;
	/**
	 * BGMを再生するストーリーデータを表します。
	 *
	 * @see StoryPlayBGM
	 */
	public static final byte COMMAND_PLAY_BGM = 14;
	/**
	 * BGMを停止するストーリーデータを表します。
	 *
	 * @see StoryStopBGM
	 */
	public static final byte COMMAND_STOP_BGM = 15;
	/**
	 * SEを再生するストーリーデータを表します。
	 *
	 * @see StoryPlaySE
	 */
	public static final byte COMMAND_PLAY_SE = 16;
	/**
	 * ボックスを表示するストーリーデータを表します。
	 *
	 * @see StoryShowBox
	 */
	public static final byte COMMAND_SHOW_BOX = 17;
	/**
	 * ボックスを隠すストーリーデータを表します。
	 *
	 * @see StoryShowBox
	 */
	public static final byte COMMAND_HIDE_BOX = 18;
	/**
	 * 変数を設定するストーリーデータを表します。
	 *
	 * @see StoryAssignment
	 */
	public static final byte COMMAND_SET_VARIABLE = 19;
	/**
	 * 背景色を変更するストーリーデータを表します。
	 *
	 * @see StoryChangeBgColor
	 */
	public static final byte COMMAND_SET_BACKGROUND_COLOR = 20;
	/**
	 * エフェクトを実行するストーリーデータを表します。
	 *
	 * @see StoryEffect
	 */
	public static final byte COMMAND_EFFECT = 21;
	/**
	 * 乱数を発生させるストーリーデータを表します。
	 *
	 * @see StoryRandom
	 */
	public static final byte COMMAND_RANDOM = 22;
	/**
	 * 計算を行うストーリーデータを表します。
	 *
	 * @see StoryCalculation
	 */
	public static final byte COMMAND_CALCULATION = 23;
	/**
	 * ゲームを終了するストーリーデータを表します。
	 *
	 * @see StoryExit
	 */
	public static final byte COMMAND_EXIT = 24;
	/**
	 * 指定時間待機するストーリーデータを表します。
	 *
	 * @see StoryWait
	 */
	public static final byte COMMAND_WAIT = 25;
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
		Properties properties = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_SETTING);
		int i = properties.getProperty(Setting.SETTING_SKIPPABLE);
		return i == 1;
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
