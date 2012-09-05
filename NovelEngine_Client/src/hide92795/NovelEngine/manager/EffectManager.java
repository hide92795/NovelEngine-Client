package hide92795.novelengine.manager;

import hide92795.novelengine.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bsh.ClassPathException;
import bsh.classpath.BshClassPath;

public class EffectManager {
	private BshClassPath bcp;
	private HashMap<Integer, ClassData> backgroundEffecter;
	private HashMap<Integer, ClassData> bgImageEffecter;
	private HashMap<Integer, ClassData> characterFader;
	private HashMap<Integer, ClassData> characterEffecter;

	public EffectManager() {
		backgroundEffecter = new HashMap<Integer, EffectManager.ClassData>();
		try {
			bcp = BshClassPath.getUserClassPath();
		} catch (ClassPathException e) {
			e.printStackTrace();
		}
		init();
	}

	private void init() {
		create(backgroundEffecter, "hide92795.novelengine.background.effect");
	}

	private void create(HashMap<Integer, ClassData> map, String packageName) {
		List<String> list = search(packageName);
		for (String className : list) {
			Class<?> c = null;
			try {
				c = Class.forName(className);
				Field fid = c.getField("ID");
				Field fconstructor = c.getField("CONSTRUCTOR");
				int id = fid.getInt(null);
				Class<?>[] constructor = (Class<?>[]) fconstructor.get(null);
				ClassData cd = new ClassData();
				cd.constructor = constructor;
				cd.targetClass = c;
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

	private List<String> search(String name) {
		List<String> ret = new ArrayList<String>();
		// パッケージ名一覧を取得する
		Set<?> set = bcp.getPackagesSet();
		Iterator<?> i = set.iterator();
		while (i.hasNext()) {
			search(ret, name, (String) i.next());
		}
		return ret;
	}

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

	public class ClassData {
		public Class<?> targetClass;
		public Class<?>[] constructor;
	}

	public ClassData getBackgroundEffect(int id){
		return backgroundEffecter.get(id);
	}

}
