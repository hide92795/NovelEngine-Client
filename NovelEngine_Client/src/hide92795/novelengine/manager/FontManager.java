package hide92795.novelengine.manager;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderFont;

import java.awt.Font;
import java.util.HashMap;

/**
 * フォントデータを管理するためのマネージャーです。
 *
 * @author hide92795
 */
public class FontManager {
	/**
	 * フォントデータを格納するマップ
	 */
	private HashMap<Integer, Font> fonts;

	/**
	 * {@link hide92795.novelengine.manager.FontManager FontManager} のオブジェクトを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public FontManager(NovelEngine engine) {
		fonts = new HashMap<Integer, Font>();
		load(engine);
	}

	/**
	 * フォントデータをロードします。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void load(NovelEngine engine) {
		LoaderFont.load(engine, fonts);
	}
}
