package hide92795.novelengine.figure;

import hide92795.novelengine.client.NovelEngine;

/**
 * 背景を切り抜いて表示する際の切り抜きの図形に関する処理をします。
 *
 * @author hide92795
 */
public abstract class Figure {
	/**
	 * ステンシルバッファに領域を書き込みます。
	 * @param engine 
	 */
	public abstract void renderStencil(NovelEngine engine);
}
