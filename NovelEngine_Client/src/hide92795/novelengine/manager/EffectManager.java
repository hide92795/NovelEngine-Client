//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package hide92795.novelengine.manager;

import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.background.effect.TransitionFade;
import hide92795.novelengine.background.effect.TransitionSlide;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 各種エフェクトを管理するマネージャーです。
 * 
 * @author hide92795
 */
public class EffectManager {
	/**
	 * バックグラウンドに適用出来るエフェクターを格納します。
	 */
	private HashMap<Integer, ClassData<BackGroundEffect>> backgroundEffect;

	/**
	 * {@link hide92795.novelengine.manager.EffectManager EffectManager} のオブジェクトを生成します。
	 */
	public EffectManager() {
		backgroundEffect = new HashMap<Integer, ClassData<BackGroundEffect>>();
		init();
	}

	/**
	 * 内蔵のエフェクトを登録します。
	 */
	private void init() {
		addBackGroundEffect(TransitionFade.ID, TransitionFade.class, TransitionFade.CONSTRUCTOR);
		addBackGroundEffect(TransitionSlide.ID, TransitionSlide.class, TransitionSlide.CONSTRUCTOR);
	}

	/**
	 * 背景エフェクトを登録します。
	 * 
	 * @param id
	 *            背景エフェクトのID
	 * @param c
	 *            背景エフェクトのクラス
	 * @param constructor
	 *            背景エフェクトの生成に必要な引数の配列
	 */
	public void addBackGroundEffect(int id, Class<? extends BackGroundEffect> c, Class<?>[] constructor) {
		backgroundEffect.put(id, new ClassData<BackGroundEffect>(c, constructor));
	}

	/**
	 * バックグラウンドエフェクトから対象のエフェクトのクラスデータを取得します。
	 * 
	 * @param id
	 *            取得するエフェクトのID
	 * @return 対象のエフェクトのクラスデータ
	 */
	public ClassData<BackGroundEffect> getBackgroundEffect(int id) {
		return backgroundEffect.get(id);
	}

	/**
	 * エフェクトの生成に必要なデータを保管するクラスです。
	 * 
	 * @author hide92795
	 */
	public class ClassData<T> {
		/**
		 * エフェクトのクラスです。
		 */
		private final Class<? extends T> targetClass;
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
		public ClassData(Class<? extends T> targetClass, Class<?>[] constructor) {
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
		public T instantiation(Object[] argument) throws InstantiationException, IllegalAccessException,
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
