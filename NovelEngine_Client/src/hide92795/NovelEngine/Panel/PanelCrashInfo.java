package hide92795.novelengine.panel;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

/**
 * エンジンが再起不可能な例外が発生した場合に表示されるパネルです。
 *
 * @author hide92795
 */
public class PanelCrashInfo extends Panel {

	/**
	 * パネルを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param exception
	 *            発生した例外
	 */
	public PanelCrashInfo(final NovelEngine engine, final Exception exception) {
		super(engine);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void init() {
	}

	@Override
	public final void render(final NovelEngine engine) {
		Renderer.renderColor(1.0f, 0.5f, 1.0f);
	}

	@Override
	public void update(final int delta) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
