package hide92795.novelengine.gui;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.gui.listener.MouseListener;
import hide92795.novelengine.panel.Panel;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 * クリックすることが可能なボタンを提供します。
 *
 * @author hide92795
 */
public class Button extends Gui {
	/**
	 * 通常時に表示されるテクスチャのIDです。
	 */
	private int textureNormalId;
	/**
	 * オンマウス時に表示されるテクスチャのIDです。
	 */
	private int textureOnMouseId;
	/**
	 * ボタンが無効の時に表示されるテクスチャのIDです。
	 */
	private int textureDisable;
	/**
	 * 左クリックが押下可能な場所で開始された時にtrueです。
	 */
	private boolean validClickStart;
	/**
	 * このボタンがクリックされた際に呼ばれる {@link hide92795.novelengine.gui.listener.MouseListener} です。
	 *
	 * @see #setListener(MouseListener)
	 */
	private MouseListener listener;
	/**
	 * 描画されるテクスチャのIDを示します。
	 */
	private int renderTexture;

	/**
	 * ボタンを作成します。ボタンがクリックされた時は、指定された {@link hide92795.novelengine.gui.listener.MouseListener MouseListener} の onClick
	 * メソッドが呼ばれます。
	 *
	 * @param x
	 *            ボタンを配置する点のX座標
	 * @param y
	 *            ボタンを配置する点のY座標
	 * @param clickable
	 *            クリック可能な領域
	 * @param width
	 *            ボタンの横幅
	 * @param height
	 *            ボタンの縦幅
	 * @param textureNormalId
	 *            通常時に表示されるテクスチャのID
	 * @param textureOnMouseId
	 *            オンマウス時に表示されるテクスチャのID
	 *
	 * @see MouseListener
	 *
	 */
	public Button(int x, int y, int width, int height, boolean[] clickable, int textureNormalId, int textureOnMouseId) {
		super(x, y, width, height, clickable);
		this.textureNormalId = textureNormalId;
		this.textureOnMouseId = textureOnMouseId;
	}

	@Override
	public void update(Panel panel, int delta) {
		int x = panel.engine().getMouseX();
		int y = panel.engine().getMouseY();
		boolean drawOnMouseTexture = false;
		if (isClickableAt(x, y)) {
			// 左クリックが押下中か
			if (Mouse.isButtonDown(0)) {
				// クリック開始地点が有効かどうか
				if (validClickStart) {
					drawOnMouseTexture = true;
				}
			} else {
				drawOnMouseTexture = true;
			}
		}
		// 描画するテクスチャの決定
		if (isEnabled()) {
			// 有効
			if (drawOnMouseTexture) {
				// オンマウス
				renderTexture = textureOnMouseId;
			} else {
				// 通常
				renderTexture = textureNormalId;
			}
		} else {
			// 無効
			renderTexture = textureDisable;
		}
	}

	@Override
	public void render(NovelEngine engine) {
		Texture texture = engine.getImageManager().getImage(renderTexture);
		Renderer.renderImage(texture, 1.0f, getX(), getY());
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		// クリック開始地点が有効か
		if (isClickableAt(event.getX(), event.getY())) {
			validClickStart = true;
			listener.onLeftClickStart(event);
		} else {
			validClickStart = false;
		}
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
		// クリック開始が有効かつ終了地点が有効
		if (validClickStart && isClickableAt(event.getX(), event.getY())) {
			listener.onLeftClickFinish(event);
		}
	}

	/**
	 * このボタンがクリックされた際に呼ばれる {@link hide92795.novelengine.gui.listener.MouseListener} を設定します。
	 *
	 * @param listener
	 *            通知を受け取る {@link hide92795.novelengine.gui.listener.MouseListener}
	 */
	public void setListener(MouseListener listener) {
		this.listener = listener;
	}
}
