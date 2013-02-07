package hide92795.novelengine.manager.gui;

import hide92795.novelengine.gui.data.DataButton;
import hide92795.novelengine.loader.LoaderButton;
import hide92795.novelengine.loader.LoaderResource;

import java.util.HashMap;

/**
 * ボタンの描画に必要なデータを保存するマネージャーです。
 *
 * @author hide92795
 */
public class ButtonManager {
	/**
	 * ボタンデータを保管するマップです。
	 */
	private HashMap<Integer, DataButton> buttons;
	/**
	 * ボタンの位置データを保管するマップです。
	 */
	private HashMap<Integer, PositionSet> positions;

	/**
	 * {@link hide92795.novelengine.manager.GuiManager.ButtonManager ButtonManager} のオブジェクトを生成します。
	 */
	public ButtonManager() {
		buttons = new HashMap<Integer, DataButton>();
		positions = new HashMap<Integer, PositionSet>();
	}

	/**
	 * ボタンデータを読み込みます。
	 *
	 * @param loader
	 *            リソースをロードするためのローダー
	 */
	public void load(LoaderResource loader) {
		LoaderButton.load(loader, buttons, positions);
	}

	/**
	 * 指定されたIDのボタンデータを取得します。
	 *
	 * @param buttonId
	 *            ボタンID
	 * @return ボタンデータ
	 */
	public DataButton getButton(int buttonId) {
		return buttons.get(buttonId);
	}

	/**
	 * 指定されたIDの座標データの集合を取得します。
	 *
	 * @param positionId
	 *            座標データのID
	 * @return 座標データの集合
	 */
	public PositionSet getPositionSet(int positionId) {
		return positions.get(positionId);
	}

	/**
	 * 複数のボタンの座標を保存するための集合です。
	 *
	 * @author hide92795
	 */
	public static class PositionSet {
		/**
		 * 座標を保存する配列です。
		 */
		private int[][] positions;

		/**
		 * 指定された個数のボタンの座標を保存する集合を作成します。
		 *
		 * @param num
		 *            この集合が保存するボタンの座標の数
		 */
		public PositionSet(int num) {
			positions = new int[num][2];
		}

		/**
		 * 座標を追加します。
		 *
		 * @param order
		 *            ボタンの順番
		 * @param x
		 *            ボタンのX座標
		 * @param y
		 *            ボタンのY座標
		 */
		public void addPosition(int order, int x, int y) {
			positions[order][0] = x;
			positions[order][1] = y;
		}

		/**
		 * 指定された番号のボタンの座標を取得します。
		 *
		 * @param order
		 *            ボタンの順番
		 * @return ボタン座標が保存された配列
		 */
		public int[] getPosition(int order) {
			return positions[order];
		}
	}
}