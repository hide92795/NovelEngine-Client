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

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

/**
 * 音声データを管理するためのマネージャーです。<br>
 * このクラスは将来別のクラスに移動される予定です。
 *
 * @author hide92795
 */
@Deprecated
public class VoiceManager {

	private HashMap<Integer, Audio> voices;

	public VoiceManager() {
		voices = new HashMap<Integer, Audio>();
	}

	public Audio getVoice(int id) {
		Audio p = voices.get(id);
		return p;
	}

	public void putVoice(int id, byte[] voice) {
		Audio p = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(voice);
		try {
			p = AudioLoader.getAudio("OGG", bis);
		} catch (Exception e) {
			System.err.println("ボイスデータをロードできませんでした。");
			e.printStackTrace();
		}
		voices.put(id, p);
	}

	public boolean isLoaded(int id) {
		return voices.containsKey(id);
	}

}
