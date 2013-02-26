package hide92795.novelengine.gui.entity;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.data.DataButton;
import hide92795.novelengine.gui.event.MouseEvent;
import hide92795.novelengine.panel.Panel;
import hide92795.novelengine.story.StoryButton;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

/**
 * クリックすることが可能なボタンを提供します。
 *
 * @author hide92795
 */
public class EntityButton extends EntityGui {
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
	private int textureDisableId;
	/**
	 * 左クリックが押下可能な場所で開始された時にtrueです。
	 */
	private boolean validClickStart;
	/**
	 * 描画されるテクスチャのIDを示します。
	 */
	private int renderTexture;
	/**
	 * マウス関連のイベントを無視するかどうかを表します。
	 */
	private boolean ignoreMouseEvent;

	/**
	 * クリック可能な領域を表すbooleanの配列です。width * height個の配列で、クリック可能ならtrueを対象の要素に格納します。
	 *
	 * @see #isClickableAt(int, int)
	 */
	private boolean[] clickable;
	private final int buttonId;
	private final int jumpSceneId;
	private int width;
	private int height;
	private StoryButton owner;
	private int id;

	/**
	 * @param buttonId
	 *            描画するボタンのID
	 * @param jumpSceneId
	 *            クリックされた際に移動するシーンID
	 */
	public EntityButton(int buttonId, int jumpSceneId) {
		this.buttonId = buttonId;
		this.jumpSceneId = jumpSceneId;
	}

	@Override
	public void init(NovelEngine engine) {
		DataButton button = engine.getGuiManager().getButtonManager().getButton(buttonId);
		this.textureNormalId = button.getImageNormalId();
		this.textureOnMouseId = button.getImageOnMouseId();
		this.textureDisableId = button.getImageDisabledId();
		this.width = button.getWidth();
		this.height = button.getHeight();
		this.clickable = engine.getGuiManager().getClickable(button.getCliclableId());
		setEnabled(true);
	}

	/**
	 * このボタンを管理しているストーリーデータを設定します。
	 *
	 * @param story
	 *            このボタンを管理しているストーリーデータ
	 */
	public void setOwner(StoryButton story) {
		this.owner = story;
	}

	@Override
	public void update(Panel panel, int delta) {
		int x = panel.engine().getMouseX();
		int y = panel.engine().getMouseY();
		boolean drawOnMouseTexture = false;
		if (!ignoreMouseEvent) {
			if (isClickableAt(x, y)) {
				// 左クリックが押下中か
				if (Mouse.isButtonDown(0)) {
					// クリック開始地点が有効かどうか
					if (validClickStart) {
						drawOnMouseTexture = true;
					}
				} else {
					drawOnMouseTexture = true;
					validClickStart = false;
				}
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
			renderTexture = textureDisableId;
		}
	}

	@Override
	public void render(NovelEngine engine) {
		Texture texture = engine.getImageManager().getImage(renderTexture);
		Renderer.renderImage(texture, 1.0f, getX(), getY());
	}

	@Override
	public void onLeftClickStart(MouseEvent event) {
		if (isEnabled() && !ignoreMouseEvent) {
			validClickStart = true;
		}
	}

	@Override
	public void onLeftClickFinish(MouseEvent event) {
		// クリック開始が有効かつ終了地点が有効
		if (validClickStart && !ignoreMouseEvent) {
			owner.buttonClicked(id, jumpSceneId);
		}
	}

	@Override
	public boolean isClickableAt(int x, int y) {
		// クリック可能範囲か
		if (getX() < x && x < getX() + width && getY() < y && y < getY() + height) {
			int inGuiX = x - getX();
			int inGuiY = y - getY();
			int offset = inGuiX + (inGuiY * width);
			return clickable[offset];
		}
		return false;
	}

	/**
	 * このボタンのIDを設定します。
	 *
	 * @param i
	 *            ボタンのID
	 */
	public void setId(int i) {
		this.id = i;
	}

	/**
	 * マウスイベントを無視するかどうかを設定します。
	 *
	 * @param ignoreMouseEvent
	 *            マウスイベントを無視するかどうか
	 */
	public void setIgnoreMouseEvent(boolean ignoreMouseEvent) {
		this.ignoreMouseEvent = ignoreMouseEvent;
	}
}
