package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;
import hide92795.novelengine.Properties;
import hide92795.novelengine.client.NovelEngine;

import java.awt.Color;
import java.io.File;

/**
 * 各種設定を管理するマネージャーです。<br>
 * 扱える値は <code>int</code> 型に収まる範囲のみです。<br>
 * 真偽値として擬似的に用いる場合は <code>1</code> を <code>true</code> 、<code>0</code> を <code>false</code> とみなします。<br>
 * <br>
 * 各変数のロードのタイミングです。<br>
 * ・{@link #VARIABLE_RAW} ロード・セーブされることはありません。<br>
 * ・{@link #VARIABLE_GLOBAL} ソフトの起動時に読み込まれます。<br>
 * ・{@link #VARIABLE_PRIVATE} 各セーブファイルが読み込まれる際に同時に読み込まれます。<br>
 * ・{@link #VARIABLE_TEMP} ロード・セーブされることはありません。<br>
 * ・{@link #VARIABLE_SYSTEM} ロード・セーブされることはありません。<br>
 * ・{@link #VARIABLE_SETTING} ソフトの起動時に読み込まれます。<br>
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
	 * グローバル変数を表すプロパティです。
	 */
	private Properties global;
	/**
	 * ゲームの設定情報を表すプロパティです。
	 */
	private Properties setting;

	/**
	 * {@link hide92795.novelengine.manager.ConfigurationManager ConfigurationManager} のオブジェクトを生成します。
	 */
	public ConfigurationManager() {
		global = new Properties();
		setting = new Properties();
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
		try {
			File file = NovelEngine.getCurrentDir();
			file = new File(file, "global.nef");
			global.load(file);
		} catch (Exception e) {
			Logger.info("Global Variable File is not found!");
			Logger.info("Create a new Global Variable File.");
		}
		setting.setProperty(Setting.SETTING_PRESTART_BACKGROUND_COLOR, -1);
		setting.setProperty(Setting.SETTING_PRESTART_BACKGROUND_COLOR, Color.red.getRGB());
		setting.setProperty(Setting.SETTING_SKIPPABLE, 1);
		setting.setProperty(Setting.SETTING_DURATION_FADE_BGM, 500);
	}

	/**
	 * システム変数及びゲームの内部的な設定情報の変数一覧です。
	 *
	 * @author hide92795
	 */
	public static class Setting {
		/**
		 * {@link hide92795.novelengine.panel.PanelPrestartStory PanelPrestartStory 上で描画する背景色を表します。}
		 */
		public static final String SETTING_PRESTART_BACKGROUND_COLOR = "prestart_bgcolor";
		/**
		 * 各ストーリーデータをスキップできるかを表します。
		 */
		public static final String SETTING_SKIPPABLE = "skippable";
		/**
		 * BGMの変更時のフェードに掛ける時間を表します。
		 */
		public static final String SETTING_DURATION_FADE_BGM = "duration_fade_bgm";
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
	}
}
