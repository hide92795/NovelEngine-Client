package hide92795.novelengine.manager;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import soundly.AudioData;
import soundly.Soundly;
import soundly.XSound;

/**
 * サウンドデータを管理するためのマネージャーです。
 *
 * @author hide92795
 */
public class SoundManager {
	/**
	 * サウンドIDとそれに対応するサウンドを格納するマップです。
	 */
	private HashMap<Integer, XSound> sounds;
	/**
	 * サウンドを再生するために使う {@link soundly.Soundly Soundly} オブジェクトです。
	 */
	private Soundly sound;

	/**
	 * {@link hide92795.novelengine.manager.SoundManager SoundManager} のオブジェクトを生成します。
	 */
	public SoundManager() {
		sounds = new HashMap<Integer, XSound>();
		sound = Soundly.get();
		sound.init();
		sound.setMusicTrackCount(2);
	}

	/**
	 * 指定されたIDのサウンドを取得します。
	 *
	 * @param id
	 *            サウンドID
	 * @return 指定されたIDのサウンド
	 */
	public final XSound getSound(final int id) {
		XSound p = sounds.get(id);
		return p;
	}

	/**
	 * サウンドを登録します。
	 *
	 * @param id
	 *            サウンドID
	 * @param sound
	 *            サウンドデータが格納された <code>byte</code> 配列
	 */
	public final void putSound(final int id, final byte[] sound) {
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

	/**
	 * 再生中のサウンドを更新します。
	 *
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public final void update(final int delta) {
		sound.update(delta);
	}

	/**
	 * 指定されたサウンドIDのサウンドが登録されているかを返します。
	 *
	 * @param id
	 *            検索するサウンドID
	 * @return 登録されている場合は <code>true</code>
	 */
	public final boolean isLoaded(final int id) {
		return sounds.containsKey(id);
	}
}
