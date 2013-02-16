package hide92795.novelengine.story;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.PanelStory;

import org.lwjgl.input.Keyboard;

/**
 * SEを再生するストーリーデータです。
 *
 * @author hide92795
 */
public class StoryPlaySE extends Story {
	/**
	 * 再生するサウンドのURLです。
	 */
	private URL url;
	/**
	 * このサウンドを管理するための名前です。
	 */
	private String sourcename;

	/**
	 * SEを再生するストーリーデータを生成します。
	 *
	 * @param id
	 *            再生を行うSEのID
	 */
	public StoryPlaySE(int id) {
		String filename = id + ".nea";
		File path = new File(NovelEngine.getCurrentDir(), "sound");
		File file = new File(path, filename);
		try {
			this.url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new NovelEngineException(e, "StoryPlaySE#CONSTRUCTOR");
		}
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
		sourcename = null;
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			sourcename = story.engine().getSoundManager().playAsSE(url, ".nea");
			finish();
		}
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int eventKey) {
		if (eventKey == Keyboard.KEY_RETURN) {
			skip(engine);
		}
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		skip(event.engine());
	}

	/**
	 * スキップ可能な場合にスキップを行います。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void skip(NovelEngine engine) {
		if (canSkip(engine) && sourcename != null) {
			engine.getSoundManager().stop(sourcename);
		}
	}
}
