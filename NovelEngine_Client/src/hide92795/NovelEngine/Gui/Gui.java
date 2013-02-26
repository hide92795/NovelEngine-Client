package hide92795.novelengine.gui;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.gui.listener.KeybordListener;
import hide92795.novelengine.panel.Panel;

/**
 * ユーザーがマウス及びキーボードなどで操作可能なオブジェクトを提供します。
 *
 * @author hide92795
 */
public abstract class Gui implements KeybordListener, Clickable {
	/**
	 * GUIオブジェクトを配置する点のX座標です。
	 */
	private final int x;
	/**
	 * GUIオブジェクトを配置する点のY座標です。
	 */
	private final int y;
	/**
	 * GUIオブジェクトの横幅です。
	 */
	private final int width;
	/**
	 * GUIオブジェクトの縦幅です。
	 */
	private final int height;
	/**
	 * クリック可能な領域を表すbooleanの配列です。width * height個の配列で、クリック可能ならtrueを対象の要素に格納します。
	 *
	 * @see #isClickableAt(int, int)
	 */
	private boolean[] clickable;

	/**
	 * このGuiオブジェクトがクリック可能かを表します
	 */
	private boolean enabled;

	/**
	 * このGuiオブジェクトを描画するかしないかを表します。
	 */
	private boolean visible;

	/**
	 * 指定された引数でGuiオブジェクトを生成します。
	 *
	 * @param x
	 *            このGuiオブジェクトのX座標
	 * @param y
	 *            このGuiオブジェクトのY座標
	 * @param width
	 *            このGuiオブジェクトの横幅
	 * @param height
	 *            このGuiオブジェクトの縦幅
	 * @param clickable
	 *            クリック可能なエリア
	 */
	public Gui(final int x, final int y, final int width, final int height, final boolean[] clickable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.clickable = clickable;
	}

	/**
	 * GUIオブジェクトを更新します。
	 *
	 * @param panel
	 *            現在、画面を描画している{@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public abstract void update(Panel panel, int delta);

	/**
	 * GUIオブジェクトを描画します。
	 *
	 * @param engine
	 *            　実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public abstract void render(NovelEngine engine);

	/**
	 * クリックされた点がこのGuiオブジェクトのクリック範囲内かを判定します。
	 *
	 * @param x
	 *            クリックされたウィンドウ内のX座標
	 * @param y
	 *            クリックされたウィンドウ内のY座標
	 * @return クリック位置がGuiの範囲内かつクリック可能な領域ならtrue
	 */
	public final boolean isClickableAt(final int x, final int y) {
		// クリック可能範囲か
		if (getX() < x && x < getX() + getWidth() && getY() < y && y < getY() + getHeight()) {
			int inGuiX = x - getX();
			int inGuiY = y - getY();
			int offset = inGuiX + (inGuiY * width);
			return clickable[offset];
		}
		return false;
	}

	@Override
	public void onLeftClickStart(final MouseEvent event) {
	}

	@Override
	public void onRightClickStart(final MouseEvent event) {
	}

	@Override
	public void onLeftClickFinish(final MouseEvent event) {
	}

	@Override
	public void onRightClickFinish(final MouseEvent event) {
	}

	@Override
	public void onKeyPressed(final NovelEngine engine, final int key) {
	}

	@Override
	public void onKeyReleased(final NovelEngine engine, final int key) {
	}

	/**
	 * このGuiオブジェクトのX座標を返します
	 *
	 * @return GuiオブジェクトのX座標
	 */
	public final int getX() {
		return x;
	}

	/**
	 * このGuiオブジェクトのY座標を返します
	 *
	 * @return GuiオブジェクトのY座標
	 */
	public final int getY() {
		return y;
	}

	/**
	 * このGuiオブジェクトの横幅を返します
	 *
	 * @return Guiオブジェクトの横幅
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * このGuiオブジェクトの縦幅を返します
	 *
	 * @return Guiオブジェクトの縦幅
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * このGuiオブジェクトが有効かを返します。
	 *
	 * @return このGuiオブジェクトが有効ならtrue
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * このGuiオブジェクトが現在有効か無効かを設定します。
	 *
	 * @param enabled
	 *            このGuiオブジェクトを有効にするならtrue、無効にするならfalse
	 */
	public final void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * このGuiオブジェクトが描画されるかを設定します。
	 *
	 * @return このGuiオブジェクトが描画されるならtrue
	 */
	public final boolean isVisible() {
		return visible;
	}

	/**
	 * このGuiオブジェクトが描画されるかどうかを設定します。
	 *
	 * @param visible
	 *            このGuiオブジェクトを描画するならtrue、しないならfalse
	 */
	public final void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
