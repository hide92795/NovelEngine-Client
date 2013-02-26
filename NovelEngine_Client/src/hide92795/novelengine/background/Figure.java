package hide92795.novelengine.background;

import hide92795.novelengine.client.NovelEngine;

/**
 * {@link hide92795.novelengine.background.BackGround BackGround}
 * の描画範囲を決めるステンシル領域の描画に関する処理をします。
 *
 * @author hide92795
 */
public abstract class Figure {
	/**
	 * ステンシルバッファに領域を書き込みます。
	 *
	 * @param engine
	 *            NovelEngineのインスタンス
	 */
	public abstract void renderStencil(NovelEngine engine);
}
