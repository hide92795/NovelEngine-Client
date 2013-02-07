package hide92795.novelengine;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 重複のない番号を提供するクラスです。<br>
 * 初期容量×負荷係数の量の数字がリクエストされたら現在の容量の２倍の量の数字が新しくストックされます。<br>
 * デフォルトの初期容量は100、負荷係数は0.75です。
 *
 * @author hide92795
 */
public class UniqueNumberProvider {
	/**
	 * 数字を保管するセットです。
	 */
	private TreeSet<Integer> set;
	/**
	 * 現在使用している数字の個数です。
	 */
	private int using;
	/**
	 * 負荷係数です。
	 */
	private float loadFactor;
	/**
	 * 次に容量を増やすラインです。
	 */
	private int nextCapacity;

	/**
	 * デフォルトの初期容量(16)とデフォルトの負荷係数(0.75)でプロバイダーを生成します。
	 */
	public UniqueNumberProvider() {
		this(16, 0.75f);
	}

	/**
	 * 指定された初期容量と負荷係数でプロバイダーを生成します。
	 *
	 * @param initialCapacity
	 *            初期容量
	 * @param loadFactor
	 *            負荷係数
	 */
	public UniqueNumberProvider(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		this.nextCapacity = (int) (initialCapacity * loadFactor);
		this.set = new TreeSet<Integer>(new AscendingComparator());
		for (int i = 0; i < initialCapacity; i++) {
			set.add(i);
		}
	}

	/**
	 * 次の数字を取得します。
	 *
	 * @return 次の数字
	 */
	public int get() {
		using++;
		checkCapacity();
		return set.pollFirst();
	}

	/**
	 * 指定された数字を使用可能にします。
	 *
	 * @param i
	 *            開放する数字
	 */
	public void release(int i) {
		boolean success = set.add(i);
		if (success) {
			using--;
		}
	}

	/**
	 * 現在の使用量を検査し、必要なら容量を数を増やします。
	 */
	private void checkCapacity() {
		if (using >= nextCapacity) {
			int num = set.last() + 1;
			for (int i = 0; i < num; i++) {
				set.add(num + i);
			}
			nextCapacity = (int) (num * 2 * loadFactor);
		}
	}

	/**
	 * 昇順に順序付けを行うコンパレーターです。
	 *
	 * @author hide92795
	 */
	private class AscendingComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}

	}

}
