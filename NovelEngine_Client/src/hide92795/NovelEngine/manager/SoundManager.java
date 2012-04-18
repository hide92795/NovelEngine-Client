package hide92795.novelengine.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

public class SoundManager {
	private LinkedHashMap<Integer, Audio> sounds;

	public SoundManager() {
		sounds = new LinkedHashMap<Integer, Audio>();
	}

	public Audio getSound(int id, byte[] sound) {
		Audio p = null;
		if (sounds.containsKey(id)) {
			p = sounds.get(id);
		} else {
			ByteArrayInputStream bis = new ByteArrayInputStream(sound);
			try {
				p = AudioLoader.getAudio("WAV", bis);
			} catch (IOException e) {
				System.err.println("サウンドをロードできませんでした。");
				e.printStackTrace();
			}
			sounds.put(id, p);
		}
		return p;
	}

	public Audio getSound(int id) {
		Audio p = sounds.get(id);
		return p;
	}

	public void putSound(int id, byte[] sound) {
		Audio p = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(sound);
		try {
			p = AudioLoader.getAudio("WAV", bis);
		} catch (Exception e) {
			System.err.println("サウンドをロードできませんでした。");
			e.printStackTrace();
		}
		sounds.put(id, p);
	}
}
