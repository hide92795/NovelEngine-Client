package hide92795.novelengine.loader.item;

import hide92795.novelengine.AspectRatio;

import java.nio.ByteBuffer;

/**
 * 進行中のゲームの基本データを格納します。
 *
 * @author hide92795
 */
public class DataBasic {
	/**
	 * 進行中のゲームの名前です。
	 */
	private String gamename;
	/**
	 * 進行中のゲームのバージョンです。
	 */
	private String version;
	/**
	 * 進行中のゲームのデフォルトの画面横幅です。
	 */
	private int width;
	/**
	 * 進行中のゲームのデフォルトの画面縦幅です。
	 */
	private int height;
	/**
	 * ウィンドウのサイズをユーザーが変更できるかどうかです。
	 */
	private boolean arrowResize;
	/**
	 * ウィンドウのアイコンです。
	 */
	private ByteBuffer[] icons;
	/**
	 * ウィンドウ内のゲーム画面のアスペクト比を保つための {@link hide92795.novelengine.AspectRatio AspectRatio} オブジェクトです。
	 */
	private AspectRatio aspectRatio;

	/**
	 * ゲーム名を取得します。
	 *
	 * @return 実行中のゲームの名前
	 */
	public final String getGamename() {
		return gamename;
	}

	/**
	 * ゲーム名を設定します。
	 *
	 * @param gamename
	 *            設定するゲーム名
	 */
	public final void setGamename(final String gamename) {
		this.gamename = gamename;
	}

	/**
	 * ゲームのバージョンを取得します。
	 *
	 * @return 実行中のゲームのバージョン
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * ゲームのバージョンを設定します。
	 *
	 * @param version
	 *            設定するゲームのバージョン
	 */
	public final void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * ゲームのデフォルトの画面横幅を取得します
	 *
	 * @return ゲームのデフォルトの画面横幅
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * ゲームのデフォルトの画面横幅を設定します
	 *
	 * @param width
	 *            ゲームのデフォルトの画面横幅
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * ゲームのデフォルトの画面縦幅を取得します
	 *
	 * @return ゲームのデフォルトの画面縦幅
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * ゲームのデフォルトの画面縦幅を設定します
	 *
	 * @param height
	 *            ゲームのデフォルトの画面縦幅
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * ゲームのウィンドウのサイズをユーザーが変更できるかを取得します。
	 *
	 * @return ユーザーが変更できる場合はtrue
	 */
	public final boolean isArrowResize() {
		return arrowResize;
	}

	/**
	 * ゲームのウィンドウのサイズをユーザーが変更できるかを設定します。
	 *
	 * @param arrowResize
	 *            ユーザーが変更できる場合はtrue
	 */
	public final void setArrowResize(final boolean arrowResize) {
		this.arrowResize = arrowResize;
	}

	/**
	 * ゲームのウィンドウに表示するアイコンを格納した配列を取得します。
	 *
	 * @return 表示するアイコンを格納した配列
	 */
	public final ByteBuffer[] getIcons() {
		return icons;
	}

	/**
	 * ゲームのウィンドウに表示するアイコンを格納した配列を設定します。
	 *
	 * @param icons
	 *            表示するアイコンを格納した配列
	 */
	public final void setIcons(final ByteBuffer[] icons) {
		this.icons = icons;
	}

	/**
	 * ウィンドウ内のゲーム画面のアスペクト比を保つための {@link hide92795.novelengine.AspectRatio AspectRatio} オブジェクトを取得します。
	 *
	 * @return アスペクト比を保つための {@link hide92795.novelengine.AspectRatio AspectRatio} オブジェクト
	 */
	public final AspectRatio getAspectRatio() {
		return aspectRatio;
	}

	/**
	 * ウィンドウ内のゲーム画面のアスペクト比を保つための {@link hide92795.novelengine.AspectRatio AspectRatio} オブジェクトを設定します。
	 *
	 * @param width
	 *            横幅の比
	 * @param height
	 *            縦幅の比
	 */
	public final void setAspectRatio(final int width, final int height) {
		this.aspectRatio = new AspectRatio(width, height);
	}
}
