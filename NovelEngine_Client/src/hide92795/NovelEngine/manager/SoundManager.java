package hide92795.novelengine.manager;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

public class SoundManager {
	private HashMap<Integer, Audio> sounds;

	public SoundManager() {
		sounds = new HashMap<Integer, Audio>();
	}

	public Audio getSound(int id) {
		Audio p = sounds.get(id);
		return p;
	}

	public void putSound(int id, byte[] sound) {
		Audio p = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(sound);
		try {
			p = AudioLoader.getAudio("OGG", bis);
		} catch (Exception e) {
			System.err.println("サウンドをロードできませんでした。");
			e.printStackTrace();
		}
		sounds.put(id, p);
	}

	public boolean isLoaded(int id) {
		return sounds.containsKey(id);
	}
}
