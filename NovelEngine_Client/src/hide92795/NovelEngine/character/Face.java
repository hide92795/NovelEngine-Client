package hide92795.novelengine.character;

/**
 * １人のキャラクターの１つの表情を管理するクラスです。
 *
 * @author hide92795
 */
public class Face {
	/**
	 * 表示される画像のIDです。<br>
	 * 0番目には通常時表示される画像、それ以降には瞬き時に表示される順番に画像IDが格納されます。
	 */
	private final int[] faceDiff;
	/**
	 * まばたき実行時に各画像が変更される間隔です。
	 */
	private final int interval;

	/**
	 * １つの表情を管理するクラスを生成します。
	 *
	 * @param faceDiff
	 *            表示される画像のID
	 * @param interval
	 *            まばたき実行時に各画像が変更される間隔
	 */
	public Face(int[] faceDiff, int interval) {
		this.faceDiff = faceDiff;
		this.interval = interval;
	}

	/**
	 * この表情に登録されている画像を取得します。
	 *
	 * @return この表情に登録されている画像
	 */
	public final int[] getFaceDiff() {
		return faceDiff;
	}

	/**
	 * この表情に登録されている画像の個数を返します。
	 *
	 * @return この表情に登録されている画像の個数
	 */
	public final int size() {
		return faceDiff.length;
	}

	/**
	 * 指定された順番の画像IDを返します。
	 *
	 * @param order
	 *            取得する画像IDの順番
	 * @return 指定された順番の画像ID
	 */
	public int getImage(int order) {
		return faceDiff[order];
	}

}
