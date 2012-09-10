package hide92795.novelengine.manager;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import soundly.AudioData;
import soundly.Soundly;
import soundly.XSound;

public class SoundManager {
	private HashMap<Integer, XSound> sounds;
	private Soundly sound;

	public SoundManager() {
		sounds = new HashMap<Integer, XSound>();
		sound = Soundly.get();
		sound.init();
		sound.setMusicTrackCount(2);
	}

	public XSound getSound(int id) {
		XSound p = sounds.get(id);
		return p;
	}

	public void putSound(int id, byte[] sound) {
		XSound sound2 = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(sound);
		try {
			Audio audio = AudioLoader.getAudio("OGG", bis);
			sound2 = new XSound(new AudioData(audio.getBufferID()));
		} catch (Exception e) {
			System.err.println("サウンドをロードできませんでした。");
			e.printStackTrace();
		}
		sounds.put(id, sound2);
	}

	public void update(int delta) {
		sound.update(delta);
	}

	public boolean isLoaded(int id) {
		return sounds.containsKey(id);
	}

	public void trace() {

	}
}
