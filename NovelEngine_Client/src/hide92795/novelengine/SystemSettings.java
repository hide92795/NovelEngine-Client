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
package hide92795.novelengine;

/**
 * システム変数及びゲームの内部的な設定情報の変数一覧です。
 * 
 * @author hide92795
 */
public class SystemSettings {
	/**
	 * このクラスはユーティリティクラスのためオブジェクト化できません。
	 */
	private SystemSettings() {
	}

	/**
	 * ウィンドウのX座標を表します。
	 */
	public static final String SETTING_WINDOW_X = "window_X";
	/**
	 * ウィンドウのY座標を表します。
	 */
	public static final String SETTING_WINDOW_Y = "window_y";
	/**
	 * ウィンドウの横幅を表します。
	 */
	public static final String SETTING_WINDOW_WIDTH = "window_width";
	/**
	 * ウィンドウの高さを表します。
	 */
	public static final String SETTING_WINDOW_HEIGHT = "window_height";
	/**
	 * 文章表示のスピードを表します。<br>
	 * 単位は px/s(ピクセル/秒) です
	 */
	public static final String SETTING_WORDS_SPEED = "words_speed";
	/**
	 * {@link hide92795.novelengine.panel.PanelPrestartStory PanelPrestartStory 上で描画する背景色を表します。}
	 */
	public static final String RENDER_PRESTART_BACKGROUND_COLOR = "prestart_bgcolor";
	/**
	 * 各ストーリーデータをスキップできるかを表します。
	 */
	public static final String RENDER_SKIPPABLE = "skippable";
	/**
	 * BGMの変更時にフェードを行うかを表します。
	 */
	public static final String RENDER_ENABLE_FADE_BGM = "enable_fade_bgm";
	/**
	 * BGMの変更時のフェードに掛ける時間を表します。
	 */
	public static final String RENDER_DURATION_FADE_BGM = "duration_fade_bgm";
	/**
	 * 使用するメッセージボックスのIDを表します。
	 */
	public static final String RENDER_MESSAGE_BOX = "message_box";
	/**
	 * 使用するフォントのIDを表します。
	 */
	public static final String RENDER_FONT = "font";
	/**
	 * メッセージボックスが隠れた際に表示していた文章を消すかどうかを表します。
	 */
	public static final String RENDER_WORDS_CLEAR_ON_CLOSE_BOX = "words_clear_on_close_box";
	/**
	 * SEの再生が終了するまでそのストーリーデータを待機させるかを表します。
	 */
	public static final String RENDER_WAIT_SE_FINISHED = "wait_se_finished";
	/**
	 * ストーリーデータがスキップされた際にSEの再生を止めるかどうかを表します。
	 */
	public static final String RENDER_STOP_SE_SKIPPED = "stop_se_skipped";
	/**
	 * SEの再生が終了するまでそのストーリーデータを待機させるかを表します。
	 */
	public static final String RENDER_WAIT_VOICE_FINISHED = "wait_voice_finished";
	/**
	 * ストーリーデータがスキップされた際にSEの再生を止めるかどうかを表します。
	 */
	public static final String RENDER_STOP_VOICE_SKIPPED = "stop_voice_skipped";
	/**
	 * 現在の西暦を表します。
	 */
	public static final String SYSTEM_YEAR = "year";
	/**
	 * 現在の月を表します。
	 */
	public static final String SYSTEM_MONTH = "month";
	/**
	 * 現在の日付を表します。
	 */
	public static final String SYSTEM_DATE = "date";
	/**
	 * 現在の曜日を表します。<br>
	 * 日曜日を1とし、1日進むごとに1ずつ増えます。
	 */
	public static final String SYSTEM_DAY = "day";
	/**
	 * 現在の時間を24時間計式で表します。
	 */
	public static final String SYSTEM_HOUR = "hour";
	/**
	 * 現在の分を表します。
	 */
	public static final String SYSTEM_MINUTE = "minute";
	/**
	 * 現在の秒を表します。
	 */
	public static final String SYSTEM_SECOND = "second";
	/**
	 * 現在のミリ秒を表します。
	 */
	public static final String SYSTEM_MILLISECOND = "millisecond";
}