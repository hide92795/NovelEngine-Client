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
package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.Properties;
import hide92795.novelengine.box.Box;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * メッセージボックスに関するデータを外部ファイルから読み込むためのクラスです。
 *
 * @author hide92795
 */
public class LoaderBox extends Loader {
	/**
	 * メッセージボックスに関するデータを読み込み、マップに格納します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 *
	 * @param loader
	 *            リソースをロードするためのローダー
	 * @param boxes
	 *            メッセージボックスに関するデータを保管するマップ
	 */
	public static void load(NovelEngine engine, LoaderResource loader, HashMap<Integer, Box> boxes) {
		File path = new File(NovelEngine.getCurrentDir(), "object");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "box.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			ClassPool pool = ClassPool.getDefault();
			CtClass cc_box = pool.get("hide92795.novelengine.box.Box");

			int num = unpacker.readInt();

			for (int i = 0; i < num; i++) {
				int id = unpacker.readInt();
				boolean isDefault = unpacker.readBoolean();

				if (isDefault) {
					Properties p = engine.getConfigurationManager()
							.getProperties(ConfigurationManager.VARIABLE_SETTING);
					

				}

				int imageId = unpacker.readInt();
				loader.loadImage(imageId);

				// 位置データ
				int showX = unpacker.readInt();
				int hideX = unpacker.readInt();
				int showY = unpacker.readInt();
				int hideY = unpacker.readInt();
				float showA = unpacker.readFloat();
				float hideA = unpacker.readFloat();

				// 移動用計算式
				String f_showX = unpacker.readString();
				String f_showY = unpacker.readString();
				String f_showA = unpacker.readString();

				String f_hideX = unpacker.readString();
				String f_hideY = unpacker.readString();
				String f_hideA = unpacker.readString();

				// クラス作成
				CtClass cc = pool.makeClass("hide92795.novelengine.box.Box" + id);
				cc.setSuperclass(cc_box);

				StringBuilder sb = new StringBuilder();
				sb.append("protected int updateShowX(int time) {");
				sb.append("return ").append(f_showX).append(";");
				sb.append("}");
				CtMethod m_showX = CtNewMethod.make(sb.toString(), cc);

				sb = new StringBuilder();
				sb.append("protected int updateHideX(int time) {");
				sb.append("return ").append(f_hideX).append(";");
				sb.append("}");
				CtMethod m_hideX = CtNewMethod.make(sb.toString(), cc);

				sb = new StringBuilder();
				sb.append("protected int updateShowY(int time) {");
				sb.append("return ").append(f_showY).append(";");
				sb.append("}");
				CtMethod m_showY = CtNewMethod.make(sb.toString(), cc);

				sb = new StringBuilder();
				sb.append("protected int updateHideY(int time) {");
				sb.append("return ").append(f_hideY).append(";");
				sb.append("}");
				CtMethod m_hideY = CtNewMethod.make(sb.toString(), cc);

				sb = new StringBuilder();
				sb.append("protected float updateShowAlpha(int time) {");
				sb.append("return ").append(f_showA).append(";");
				sb.append("}");
				CtMethod m_showA = CtNewMethod.make(sb.toString(), cc);

				sb = new StringBuilder();
				sb.append("protected float updateHideAlpha(int time) {");
				sb.append("return ").append(f_hideA).append(";");
				sb.append("}");
				CtMethod m_hideA = CtNewMethod.make(sb.toString(), cc);

				cc.addMethod(m_showX);
				cc.addMethod(m_hideX);
				cc.addMethod(m_showY);
				cc.addMethod(m_hideY);
				cc.addMethod(m_showA);
				cc.addMethod(m_hideA);

				@SuppressWarnings("unchecked")
				Class<Box> c = cc.toClass();
				Constructor<Box> constructor = c.getConstructor(Box.CONSTRUCTOR);
				Object[] args = { imageId, showX, hideX, showY, hideY, showA, hideA };
				Box box = constructor.newInstance(args);
				boxes.put(id, box);
			}
		} catch (Exception e) {
			throw new NovelEngineException(e, "");
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					throw new NovelEngineException(e, "");
				}
			}
		}

	}
}
