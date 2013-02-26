package hide92795.novelengine.background;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilOp;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.background.figure.Figure_EntireScreen;
import hide92795.novelengine.client.NovelEngine;

import org.newdawn.slick.opengl.Texture;

/**
 * ストーリー上で背景とキャラクターを保持する１枚のレイヤーとして機能します。
 *
 * @author hide92795
 */
public class BackGround {
	/**
	 * 背景のイメージIDです。
	 */
	private int imageId;
	/**
	 * X軸方向の描画の起点です。
	 */
	private int x;
	/**
	 * Y軸方向の描画の起点です。
	 */
	private int y;
	/**
	 * 描画をする際の拡大率です。
	 */
	private float magnificartion = 1.0f;

	// private LinkedList<EntityCharacter> characters;
	/**
	 * 描画範囲を示す{@link hide92795.novelengine.background.Figure Figure}です
	 */
	private Figure figure;

	/**
	 * 背景色の赤成分です。
	 */
	private float red;
	/**
	 * 背景色の緑成分です。
	 */
	private float green;
	/**
	 * 背景色の青成分です。
	 */
	private float blue;
	/**
	 * 背景全体のアルファ値です。
	 */
	private float alpha = 1.0f;

	/**
	 * {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトを指定された描画範囲で作成します。<br>
	 * 背景色のデフォルトは白、アルファ値のデフォルトは透明(0.0f)です。
	 *
	 * @param figure
	 *            描画範囲を示す{@link hide92795.novelengine.background.Figure Figure}
	 */
	public BackGround(Figure figure) {
		this.figure = figure;
		// characters = new LinkedList<EntityCharacter>();
	}

	/**
	 * {@link hide92795.novelengine.background.BackGround BackGround} オブジェクトを画面全体を描画範囲として作成します。<br>
	 * 背景色のデフォルトは白、アルファ値のデフォルトは透明(0.0f)です。
	 *
	 */
	public BackGround() {
		this(new Figure_EntireScreen());
	}

	/**
	 * 描画範囲を表すステンシル領域を描画します。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public void createStencil(NovelEngine engine) {
		glStencilFunc(GL_ALWAYS, 1, ~0);
		glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
		glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
		figure.renderStencil(engine);
	}

	/**
	 * 背景画像、キャラクターなどの描画を行います。
	 *
	 * @param engine
	 *            実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public void render(NovelEngine engine) {
		glColorMask(true, true, true, true);
		glDepthMask(true);
		glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
		glStencilFunc(GL_EQUAL, 1, ~0);
		// 背景
		Texture texture = engine.getImageManager().getImage(imageId);
		if (texture != null) {
			Renderer.renderImage(texture, alpha, x, y);
		} else {
			Renderer.renderColor(red, green, blue, alpha);
		}
		// キャラクター
		// for (EntityCharacter character : characters) {
		// character.render();
		// }
	}

	/**
	 * 現在の背景のイメージIDを取得します。
	 *
	 * @return 現在の背景のイメージID
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * 背景のイメージIDを設定します。
	 *
	 * @param imageId
	 *            設定するイメージID
	 */
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	/**
	 * 現在のアルファ値を取得します。
	 *
	 * @return 現在のアルファ値
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * アルファ値を設定します。
	 *
	 * @param alpha
	 *            設定するアルファ値
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	/**
	 * 現在のX軸方向の位置を取得します。
	 *
	 * @return 現在のX軸方向の位置
	 */
	public int getX() {
		return x;
	}

	/**
	 * X軸方向の位置を設定します。
	 *
	 * @param x
	 *            設定するX軸方向の位置
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 現在のY軸方向の位置を取得します。
	 *
	 * @return 現在のY軸方向の位置
	 */
	public int getY() {
		return y;
	}

	/**
	 * Y軸方向の位置を設定します。
	 *
	 * @param y
	 *            設定するY軸方向の位置
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 現在の拡大率を取得します。
	 *
	 * @return 現在の拡大率
	 */
	public float getMagnificartion() {
		return magnificartion;
	}

	/**
	 * 拡大率を設定します。
	 *
	 * @param magnificartion
	 *            設定する拡大率
	 */
	public void setMagnificartion(float magnificartion) {
		this.magnificartion = magnificartion;
	}

	/**
	 * 現在の描画範囲を表す{@link hide92795.novelengine.background.Figure}オブジェクトを取得します。
	 *
	 * @return 現在の描画範囲を表す{@link hide92795.novelengine.background.Figure}オブジェクト
	 */
	public Figure getFigure() {
		return figure;
	}

	/**
	 * 描画範囲を設定します。
	 *
	 * @param figure
	 *            描画範囲を表す{@link hide92795.novelengine.background.Figure}オブジェクト
	 */
	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	/**
	 * 現在の背景色の赤成分を取得します。
	 *
	 * @return 現在の背景色の赤成分
	 */
	public float getRed() {
		return red;
	}

	/**
	 * 背景色の赤成分を設定します。
	 *
	 * @param red
	 *            設定する背景色の赤成分
	 */
	public void setRed(float red) {
		this.red = red;
	}

	/**
	 * 現在の背景色の緑成分を取得します。
	 *
	 * @return 現在の背景色の緑成分
	 */
	public float getGreen() {
		return green;
	}

	/**
	 * 背景色の緑成分を設定します。
	 *
	 * @param green
	 *            設定する背景色の緑成分
	 */
	public void setGreen(float green) {
		this.green = green;
	}

	/**
	 * 現在の背景色の青成分を取得します。
	 *
	 * @return 現在の背景色の青成分
	 */
	public float getBlue() {
		return blue;
	}

	/**
	 * 背景色の青成分を設定します。
	 *
	 * @param blue
	 *            設定する背景色の青成分
	 */
	public void setBlue(float blue) {
		this.blue = blue;
	}
}
