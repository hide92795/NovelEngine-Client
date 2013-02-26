package hide92795.novelengine.manager;

import hide92795.novelengine.Effectable;
import hide92795.novelengine.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bsh.ClassPathException;
import bsh.classpath.BshClassPath;

/**
 * 各種エフェクトを管理するマネージャーです。
 *
 * @author hide92795
 */
public class EffectManager {
	/**
	 * クラスをパッケージ名から取得するための {@link bsh.classpath.BshClassPath BshClassPath} オブジェクトです。
	 */
	private BshClassPath bcp;
	/**
	 * バックグラウンドに適用出来るエフェクターを格納します。
	 */
	private HashMap<Integer, ClassData> backgroundEffecter;

	// private HashMap<Integer, ClassData> characterFader;
	// private HashMap<Integer, ClassData> characterEffecter;

	/**
	 * {@link hide92795.novelengine.manager.EffectManager EffectManager} のオブジェクトを生成します。
	 */
	public EffectManager() {
		backgroundEffecter = new HashMap<Integer, EffectManager.ClassData>();
		try {
			bcp = BshClassPath.getUserClassPath();
		} catch (ClassPathException e) {
			e.printStackTrace();
		}
		create(backgroundEffecter, "hide92795.novelengine.background.effect");
	}

	/**
	 * 指定されたマップに該当パッケージ内のクラスデータを格納します。<br>
	 * クラスパス内に指定された全てのjarファイル内を検索します。
	 *
	 * @param map
	 *            データを格納するマップ
	 * @param packageName
	 *            検索を行うパッケージ名
	 */
	private void create(HashMap<Integer, ClassData> map, String packageName) {
		List<String> list = search(packageName);
		for (String className : list) {
			Class<? extends Effectable> c = null;
			try {
				c = Class.forName(className).asSubclass(Effectable.class);
				Field fid = c.getField("ID");
				Field fconstructor = c.getField("CONSTRUCTOR");
				int id = fid.getInt(null);
				Class<?>[] constructor = (Class<?>[]) fconstructor.get(null);
				ClassData cd = new ClassData(c, constructor);
				map.put(id, cd);
				Logger.info("Add class " + className);
			} catch (ClassNotFoundException e) {
				Logger.info("Error on class mapping - " + className);
			} catch (Exception e) {
				if (c != null && !c.isInterface()) {
					Logger.info("Could not register class - " + className);
				}
			}
		}

	}

	/**
	 * 指定されたパッケージ内に存在するクラス名のリストを返します。
	 *
	 * @param targetPackageName
	 *            検索を行う対象のパッケージ名
	 * @return クラス名を格納したリスト
	 */
	private List<String> search(String targetPackageName) {
		List<String> ret = new ArrayList<String>();
		// パッケージ名一覧を取得する
		Set<?> set = bcp.getPackagesSet();
		Iterator<?> i = set.iterator();
		while (i.hasNext()) {
			search(ret, targetPackageName, (String) i.next());
		}
		return ret;
	}

	/**
	 * 指定されたパッケージ内に存在するクラスをリストに格納します。
	 *
	 * @param ret
	 *            格納するリスト
	 * @param name
	 *            取得するパッケージ名
	 * @param packageName
	 *            検索を行うパッケージ名
	 */
	private void search(List<String> ret, String name, String packageName) {
		// パッケージ内のクラス名を一覧する
		Set<?> set = bcp.getClassesForPackage(packageName);
		Iterator<?> i = set.iterator();
		while (i.hasNext()) {
			String className = (String) i.next();
			// 内部クラスは無視する
			if (className.indexOf("$") >= 0) {
				continue;
			}
			if (className.indexOf(name) != -1) {
				ret.add(className);
			}
		}
	}

	/**
	 * バックグラウンドエフェクトから対象のエフェクトのクラスデータを取得します。
	 *
	 * @param id
	 *            取得するエフェクトのID
	 * @return 対象のエフェクトのクラスデータ
	 */
	public ClassData getBackgroundEffect(int id) {
		return backgroundEffecter.get(id);
	}

	/**
	 * エフェクトの生成に必要なデータを保管するクラスです。
	 *
	 * @author hide92795
	 */
	public class ClassData {
		/**
		 * エフェクトのクラスです。
		 */
		private final Class<? extends Effectable> targetClass;
		/**
		 * エフェクトの生成のコンストラクターに必要な引数リストです。　
		 */
		private final Class<?>[] constructor;

		/**
		 * 各データを保存してエフェクトを作成できるようにします。
		 *
		 * @param targetClass
		 *            エフェクトのクラス
		 * @param constructor
		 *            エフェクトの生成のコンストラクターに必要な引数リスト
		 */
		public ClassData(Class<? extends Effectable> targetClass, Class<?>[] constructor) {
			this.targetClass = targetClass;
			this.constructor = constructor;
		}

		/**
		 * このクラスデータが表すエフェクトをインスタンス化します。
		 *
		 * @param argument
		 *            インスタンス化に使用する引数
		 * @return エフェクトのオブジェクト
		 * @throws NoSuchMethodException
		 *             一致するメソッドが見つからない場合
		 * @throws InvocationTargetException
		 *             基本となるコンストラクタが例外をスローする場合
		 * @throws InstantiationException
		 *             基本となるコンストラクタを宣言するクラスが <code>abstract</code> クラスを表す場合
		 * @throws SecurityException
		 * @throws IllegalAccessException
		 *             この <code>Constructor</code> オブジェクトが言語アクセス制御を実施し、基本となるコンストラクタにアクセスできない場合
		 */
		public Effectable instantiation(Object[] argument) throws InstantiationException, IllegalAccessException,
				InvocationTargetException, NoSuchMethodException {
			return targetClass.getConstructor(constructor).newInstance(argument);
		}

		/**
		 * このエフェクトの生成に必要な引数のクラスのリストを返します。
		 *
		 * @return エフェクトの生成に必要な引数のクラスのリスト
		 */
		public Object[] getArgumentList() {
			return constructor;
		}
	}

}
