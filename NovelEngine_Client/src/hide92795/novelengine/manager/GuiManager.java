package hide92795.novelengine.manager;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderGui;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.manager.gui.ButtonManager;

import java.util.HashMap;

/**
 * オブジェクトの描画データを保存するマネージャーです。
 *
 * @author hide92795
 */
public class GuiManager {
	/**
	 * クリック可能な領域を表すboolean配列のマップです。
	 */
	private HashMap<Integer, boolean[]> clickable;
	/**
	 * ボタンデータを格納するマネージャーです。
	 */
	private final ButtonManager buttonManager;

	/**
	 * {@link hide92795.novelengine.manager.GuiManager GuiManager} のオブジェクトを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public GuiManager(NovelEngine engine) {
		clickable = new HashMap<Integer, boolean[]>();
		buttonManager = new ButtonManager();
	}

	/**
	 * 指定されたIDのクリック可能な領域を表す配列を返します。
	 *
	 * @param clickableId
	 *            クリック可能な領域のID
	 * @return クリック可能な領域を表す配列
	 */
	public boolean[] getClickable(int clickableId) {
		return clickable.get(clickableId);
	}

	/**
	 * ボタンを管理するマネージャーを取得します。
	 *
	 * @return buttonManager ボタンを管理するマネージャー
	 */
	public ButtonManager getButtonManager() {
		return buttonManager;
	}

	/**
	 * GUIに関連するデータを読み込みます。
	 *
	 * @param loader
	 *            リソースをロードするためのローダー
	 */
	public void loadResource(LoaderResource loader) {
		LoaderGui.load(clickable);
		buttonManager.load(loader);
	}
}
