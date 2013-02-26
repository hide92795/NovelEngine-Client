package hide92795.novelengine.box;

/**
 * メッセージボックスの描画に関するデータを保存します。<br>
 * このクラスは起動時に位置計算用のコードが埋め込まれた非abstractクラスとして実行されます。
 *
 * @author hide92795
 */
public abstract class Box {
	/**
	 * このメッセージボックスの画像IDです。
	 */
	private int imageId;
	private int x;
	private int y;

	public void update(int delta) {

	}

	protected abstract int updateX(int delta);

	protected abstract int updateY(int delta);

	protected abstract int updateAlpha(int delta);

}
