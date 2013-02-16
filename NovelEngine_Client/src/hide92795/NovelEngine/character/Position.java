package hide92795.novelengine.character;

/**
 * キャラクターを配置できる位置を表すクラスです。
 *
 * @author hide92795
 */
public class Position {
	/**
	 * この位置データのIDです。
	 */
	private final int id;
	/**
	 * この位置データが表すX座標です。
	 */
	private final int x;
	/**
	 * この位置データが表すY座標です。
	 */
	private final int y;

	/**
	 * キャラクターを配置できる位置を表すデータを生成します。
	 *
	 * @param id
	 *            位置データのID
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public Position(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	/**
	 * この位置データのIDを取得します。
	 *
	 * @return この位置データのID
	 */
	public final int getId() {
		return id;
	}

	/**
	 * この位置データが表すX座標を取得します。
	 *
	 * @return X座標
	 */
	public final int getX() {
		return x;
	}

	/**
	 * この位置データが表すY座標を取得します。
	 *
	 * @return Y座標
	 */
	public final int getY() {
		return y;
	}

}
