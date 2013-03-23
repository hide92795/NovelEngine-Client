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

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.Properties;
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
		Properties properties = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
		int enable_fade = properties.getProperty(Setting.RENDER_ENABLE_FADE_BGM);
		if (enable_fade == 1) {
			int duration_fade = properties.getProperty(Setting.RENDER_DURATION_FADE_BGM);
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
	 * @param sourcename
	 *            このサウンドの名前
	 */
	public void playAsSE(URL url, String identifier, String sourcename) {
		sound.newSource(false, sourcename, url, sourcename + identifier, false, 0, 0, 0, 0, 0);
		sound.play(sourcename);
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
