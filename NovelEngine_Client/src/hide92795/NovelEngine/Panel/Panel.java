package hide92795.novelengine.panel;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.gui.listener.KeybordListener;
import hide92795.novelengine.gui.listener.MouseListener;

/**
 * 画面描画を担当するクラスの抽象クラスです。
 *
 * @author hide92795
 */
public abstract class Panel implements MouseListener, KeybordListener {

	/**
	 * 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクトです。
	 */
	private NovelEngine engine;

	/**
	 * {@link hide92795.novelengine.panel.Panel Panel} のオブジェクトを生成します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public Panel(NovelEngine engine) {
		this.engine = engine;
	}

	/**
	 * 描画を行います。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public abstract void render(NovelEngine engine);

	/**
	 * アップデートを行います。
	 *
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public abstract void update(int delta);

	/**
	 * パネルを初期化及び使えるようにします。
	 */
	public abstract void init();

	/**
	 * 実行中の{@link hide92795.novelengine.client.NovelEngine} オブジェクトを返します。
	 *
	 * @return 実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public NovelEngine engine() {
		return engine;
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
	}

	@Override
	public void onRightClickStart(MouseEvent event) {
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
	}

	@Override
	public void onRightClickFinish(MouseEvent event) {
	}

	@Override
	public void onKeyPressed(NovelEngine engine, int key) {
	}

	@Override
	public void onKeyReleased(NovelEngine engine, int key) {
	}

}
