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
package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.Properties;
import hide92795.novelengine.SystemSettings;
import hide92795.novelengine.Utils;
import hide92795.novelengine.client.NovelEngine;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * 各種設定を管理するマネージャーです。<br>
 * 扱える値は <code>int</code> 型に収まる範囲のみです。<br>
 * 真偽値として擬似的に用いる場合は <code>1</code> を <code>true</code> 、<code>0</code> を <code>false</code> とみなします。<br>
 * <br>
 * 各変数のロードのタイミングです。<br>
 * <ul>
 * <li>{@link #VARIABLE_RAW} ロード・セーブされることはありません。
 * <li>{@link #VARIABLE_GLOBAL} ソフトの起動時に読み込まれます。
 * <li>{@link #VARIABLE_PRIVATE} 各セーブファイルが読み込まれる際に同時に読み込まれます。
 * <li>{@link #VARIABLE_TEMP} ロード・セーブされることはありません。
 * <li>{@link #VARIABLE_SYSTEM} ロード・セーブされることはありません。
 * <li>{@link #VARIABLE_SETTING} ソフトの起動時に読み込まれます。
 * <li>{@link #VARIABLE_RENDER} 各セーブファイルが読み込まれる際に同時に読み込まれます。起動時はデフォルトが読み込まれます。
 * </ul>
 * 
 * @author hide92795
 */
public class ConfigurationManager {
	/**
	 * 変数ではなく生の値を表します。変数名がそのまま値となります。
	 */
	public static final byte VARIABLE_RAW = -1;
	/**
	 * グローバル変数を表す定数です。
	 */
	public static final byte VARIABLE_GLOBAL = 0;
	/**
	 * プライベート変数を表す定数です。
	 */
	public static final byte VARIABLE_PRIVATE = 1;
	/**
	 * 一時変数を表す定数です。
	 */
	public static final byte VARIABLE_TEMP = 2;
	/**
	 * システム変数を表す定数です。
	 */
	public static final byte VARIABLE_SYSTEM = 3;
	/**
	 * ゲームの内部的な設定情報を表す定数です。
	 */
	public static final byte VARIABLE_SETTING = 4;
	/**
	 * ゲームの描画に関する設定情報を表す定数です。
	 */
	public static final byte VARIABLE_RENDER = 5;
	/**
	 * グローバル変数を表すプロパティです。
	 */
	private Properties global;
	/**
	 * ゲームの設定情報を表すプロパティです。
	 */
	private Properties setting;
	/**
	 * ゲームの描画に関する設定情報を表すプロパティです。
	 */
	private Properties render;

	/**
	 * {@link hide92795.novelengine.manager.ConfigurationManager ConfigurationManager} のオブジェクトを生成します。
	 */
	public ConfigurationManager() {
		global = new Properties();
		setting = new Properties();
		render = new Properties();
		loadSetting();
	}

	/**
	 * 指定した種類のプロパティを取得します。
	 * 
	 * @param type
	 *            プロパティの種類
	 * @return 対象のプロパティ
	 */
	public Properties getProperties(byte type) {
		switch (type) {
		case VARIABLE_SETTING:
			return setting;
		case VARIABLE_RENDER:
			return render;
		default:
			break;
		}
		return global;
	}

	/**
	 * 変数を取得します。
	 * 
	 * @param type
	 *            取得する変数の種類
	 * @param name
	 *            取得する変数の名前
	 * @return 取得した変数の値
	 */
	public int getValue(byte type, String name) {
		if (type == ConfigurationManager.VARIABLE_RAW) {
			return Integer.parseInt(name);
		} else {
			Properties p = getProperties(type);
			return p.getProperty(name);
		}
	}

	/**
	 * ソフトの起動時に読み込まれるプロパティをロードします。
	 */
	private void loadSetting() {
		File dir = new File(NovelEngine.getCurrentDir(), "save");
		if (!dir.exists()) {
			Logger.info("Save folder is not found!");
			Logger.info("Create new Save folder.");
			boolean mkdir = dir.mkdir();
			if (!mkdir) {
				Logger.info("Can't create Save folder.");
			}
		}
		File f_global = new File(dir, "global.neb");
		File f_setting = new File(dir, "setting.neb");

		// Global
		if (!f_global.exists()) {
			Logger.info("Global Variable File is not found!");
			Logger.info("Create new Global Variable File.");
			try {
				f_global.createNewFile();
			} catch (IOException e) {
				Logger.error("Can't create Global Variable File.");
				Utils.printStackTraceToLogger(e);
			}
		} else {
			try {
				global.load(f_global);
			} catch (Exception e) {
				Logger.error("Can't load Global Variable File.");
				Utils.printStackTraceToLogger(e);
			}
		}

		// SystemSettings
		if (!f_setting.exists()) {
			Logger.info("SystemSettings File is not found!");
			Logger.info("Create new SystemSettings File.");
			try {
				f_setting.createNewFile();
			} catch (IOException e) {
				Logger.error("Can't create SystemSettings File.");
				Utils.printStackTraceToLogger(e);
			}
			initSystemSetting();
		} else {
			try {
				setting.load(f_setting);
			} catch (Exception e) {
				Logger.error("Can't load SystemSettings File.");
				Utils.printStackTraceToLogger(e);
			}
		}

		// render.setProperty(SystemSettings.RENDER_PRESTART_BACKGROUND_COLOR, -1);
		render.setProperty(SystemSettings.RENDER_PRESTART_BACKGROUND_COLOR, Color.red.getRGB());
		render.setProperty(SystemSettings.RENDER_SKIPPABLE, 1);
		render.setProperty(SystemSettings.RENDER_WAIT_SE_FINISHED, 1);
		render.setProperty(SystemSettings.RENDER_STOP_SE_SKIPPED, 1);
		render.setProperty(SystemSettings.RENDER_WAIT_VOICE_FINISHED, 1);
		render.setProperty(SystemSettings.RENDER_STOP_VOICE_SKIPPED, 1);
		render.setProperty(SystemSettings.RENDER_DURATION_FADE_BGM, 500);
		render.setProperty(SystemSettings.RENDER_ENABLE_FADE_BGM, 0);
		render.setProperty(SystemSettings.RENDER_WORDS_CLEAR_ON_CLOSE_BOX, 1);

	}

	/**
	 * システム設定を初期化します。
	 */
	private void initSystemSetting() {
		setting.setProperty(SystemSettings.SETTING_WORDS_SPEED, 300);
	}

	/**
	 * ゲーム終了時に保存する設定を保存します。
	 */
	public void save() {
		File dir = new File(NovelEngine.getCurrentDir(), "save");
		File f_global = new File(dir, "global.neb");
		File f_setting = new File(dir, "setting.neb");

		// Global
		if (f_global.canWrite()) {
			try {
				global.store(f_global);
			} catch (Exception e) {
				Logger.error("Can't write Global Variable File.");
				Utils.printStackTraceToLogger(e);
			}
		} else {
			Logger.error("Can't write Global Variable File.");
		}
		// SystemSettings
		if (f_setting.canWrite()) {
			try {
				setting.store(f_setting);
			} catch (Exception e) {
				Logger.error("Can't write SystemSettings File.");
				Utils.printStackTraceToLogger(e);
			}
		} else {
			Logger.error("Can't write SystemSettings File.");
		}
	}
}
