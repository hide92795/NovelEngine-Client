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
