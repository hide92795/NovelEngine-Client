package hide92795.novelengine.manager;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
import hide92795.novelengine.soundsystem.XCodecJOrbis;

import java.net.URL;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

/**
 * サウンドデータを管理するためのマネージャーです。
 *
 * @author hide92795
 */
public class SoundManager {
	/**
	 * サウンドを再生するために使う {@link paulscode.sound.SoundSystem SoundSystem} オブジェクトです。
	 */
	private SoundSystem sound;
	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトです。
	 */
	private NovelEngine engine;

	/**
	 * {@link hide92795.novelengine.manager.SoundManager SoundManager} のオブジェクトを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public SoundManager(NovelEngine engine) {
		this.engine = engine;
		initSoundSystem();
		sound = new SoundSystem();
	}

	/**
	 * サウンドシステムを初期化します。
	 */
	private void initSoundSystem() {
		try {
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("nea", XCodecJOrbis.class);
		} catch (SoundSystemException e) {
			throw new NovelEngineException(e, "SoundManager#initSoundSystem");
		}
	}

	/**
	 * ロードしたサウンドを解放します。
	 */
	public void clean() {
		sound.cleanup();
	}

	/**
	 * サウンドをBGMとして再生します。
	 *
	 * @param url
	 *            再生するサウンドのURL
	 * @param identifier
	 *            ファイルの拡張子
	 */
	public void playAsBGM(URL url, String identifier) {
		int enable_fade = engine.getConfigurationManager().getValue(ConfigurationManager.VARIABLE_SETTING,
				Setting.SETTING_ENABLE_FADE_BGM);
		if (enable_fade == 1) {
			int duration_fade = engine.getConfigurationManager().getValue(ConfigurationManager.VARIABLE_SETTING,
					Setting.SETTING_DURATION_FADE_BGM);
			if (sound.playing("BGM")) {
				sound.fadeOut("BGM", url, identifier, duration_fade);
				return;
			}
		}
		sound.backgroundMusic("BGM", url, identifier, true);

	}

	/**
	 * サウンドをSEとして再生します。
	 *
	 * @param url
	 *            再生するサウンドのURL
	 * @param identifier
	 *            ファイルの拡張子
	 * @return このサウンドを管理するための名前
	 */
	public String playAsSE(URL url, String identifier) {
		String sourcename = sound.quickPlay(false, url, identifier, false, 0, 0, 0, 0, 0);
		return sourcename;
	}

	/**
	 * 指定された名前のサウンドを停止します。
	 *
	 * @param sourcename
	 *            サウンドを管理するための名前
	 */
	public void stop(String sourcename) {
		sound.stop(sourcename);
	}
}
