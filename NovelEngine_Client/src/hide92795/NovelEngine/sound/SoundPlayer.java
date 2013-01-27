package hide92795.novelengine.sound;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
import soundly.XSound;

/**
 * サウンドデータを再生するクラスです。
 *
 * @author hide92795
 */
public class SoundPlayer {
	/**
	 * 現在再生を行なっているBGMです。
	 */
	private static SoundPlayer primaryBGM;
	/**
	 * 再生するサウンドです。
	 */
	private XSound sound;

	/**
	 * プレイヤーを生成します。
	 *
	 * @param sound
	 *            再生するサウンド
	 */
	public SoundPlayer(XSound sound) {
		this.sound = sound;
	}

	/**
	 * サウンドを初期化し、再度再生できるようにします。
	 */
	public void init() {
		sound.seek(0.0f);
		sound.rewind();
	}

	/**
	 * サウンドをSEとして再生します。
	 */
	public void playAsSE() {
		sound.setMusic(false);
		sound.setLooping(false);
		sound.setVolume(1.0f);
		sound.queue();
	}

	/**
	 * サウンドをBGMとして再生します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public void playAsBGM(NovelEngine engine) {
		int duration = engine.getSettingManager().getValue(ConfigurationManager.VARIABLE_SETTING,
				Setting.SETTING_DURATION_FADE_BGM);
		sound.setVolume(0f);
		sound.setMusic(true);
		sound.setLooping(true);

		if (primaryBGM != null) {
			primaryBGM.sound.fade(duration, 0f, true);
		}
		sound.fade(duration, 1f, false);
		sound.queue();
		primaryBGM = this;
	}

	/**
	 * サウンドを停止します。
	 */
	public void stop() {
		sound.stop();
	}

	/**
	 * サウンドを停止します。
	 *
	 * @return サウンドの再生が停止している場合は <code>true</code>
	 */
	public boolean isStopped() {
		return sound.isStopped();
	}

	/**
	 * 現在再生されているBGMを停止します。
	 */
	public static void stopPrimaryBGM() {
		primaryBGM.stop();
	}
}
