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
import hide92795.novelengine.manager.ConfigurationManager.Setting;

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
				int imageId = unpacker.readInt();
				loader.loadImage(imageId);

				// 位置データ
				int showX = unpacker.readInt();
				int showY = unpacker.readInt();
				float showAlpha = unpacker.readFloat();

				int hideX = unpacker.readInt();
				int hideY = unpacker.readInt();
				float hideAlpha = unpacker.readFloat();

				// 名前表示エリアデータ
				int nameType = unpacker.readInt();
				int nameX = unpacker.readInt();
				int nameY = unpacker.readInt();

				// 表示エリアデータ
				int areaLightUpX = unpacker.readInt();
				int areaLightUpY = unpacker.readInt();

				int areaRightDownX = unpacker.readInt();
				int areaRightDownY = unpacker.readInt();

				// 移動データ
				String showMoveRatio = unpacker.readString();
				String showMoveX = unpacker.readString();
				String showMoveY = unpacker.readString();
				String showMoveAlpha = unpacker.readString();

				String hideMoveRatio = unpacker.readString();
				String hideMoveX = unpacker.readString();
				String hideMoveY = unpacker.readString();
				String hideMoveAlpha = unpacker.readString();

				// クラス作成
				CtClass cc = pool.makeClass("hide92795.novelengine.box.Box" + id);
				cc.setSuperclass(cc_box);

				// updateShowRatio
				StringBuilder sb = new StringBuilder();
				sb.append("protected float updateShowRatio(int time) {");
				sb.append("return (float)(").append(showMoveRatio).append(");");
				sb.append("}");
				CtMethod m_showRatio = CtNewMethod.make(sb.toString(), cc);

				// updateShowX
				sb = new StringBuilder();
				sb.append("protected float updateShowX(float ratio) {");
				sb.append("return (float)(").append(showMoveX).append(");");
				sb.append("}");
				CtMethod m_showX = CtNewMethod.make(sb.toString(), cc);

				// updateShowY
				sb = new StringBuilder();
				sb.append("protected float updateShowY(float ratio) {");
				sb.append("return (float)(").append(showMoveY).append(");");
				sb.append("}");
				CtMethod m_showY = CtNewMethod.make(sb.toString(), cc);

				// updateShowAlpha
				sb = new StringBuilder();
				sb.append("protected float updateShowAlpha(float ratio) {");
				sb.append("return (float)(").append(showMoveAlpha).append(");");
				sb.append("}");
				CtMethod m_showAlpha = CtNewMethod.make(sb.toString(), cc);

				// updateHideRatio
				sb = new StringBuilder();
				sb.append("protected float updateHideRatio(int time) {");
				sb.append("return (float)(").append(hideMoveRatio).append(");");
				sb.append("}");
				CtMethod m_hideRatio = CtNewMethod.make(sb.toString(), cc);

				// updateHideX
				sb = new StringBuilder();
				sb.append("protected float updateHideX(float ratio) {");
				sb.append("return (float)(").append(hideMoveX).append(");");
				sb.append("}");
				CtMethod m_hideX = CtNewMethod.make(sb.toString(), cc);

				// updateHideY
				sb = new StringBuilder();
				sb.append("protected float updateHideY(float ratio) {");
				sb.append("return (float)(").append(hideMoveY).append(");");
				sb.append("}");
				CtMethod m_hideY = CtNewMethod.make(sb.toString(), cc);

				// updateHideAlpha
				sb = new StringBuilder();
				sb.append("protected float updateHideAlpha(float ratio) {");
				sb.append("return (float)(").append(hideMoveAlpha).append(");");
				sb.append("}");
				CtMethod m_hideAlpha = CtNewMethod.make(sb.toString(), cc);

				cc.addMethod(m_showRatio);
				cc.addMethod(m_showX);
				cc.addMethod(m_showY);
				cc.addMethod(m_showAlpha);

				cc.addMethod(m_hideRatio);
				cc.addMethod(m_hideX);
				cc.addMethod(m_hideY);
				cc.addMethod(m_hideAlpha);

				@SuppressWarnings("unchecked")
				Class<Box> c = cc.toClass();
				Constructor<Box> constructor = c.getConstructor(Box.CONSTRUCTOR);
				Object[] args = { imageId, showX, showY, showAlpha, hideX, hideY, hideAlpha, nameType, nameX, nameY,
						areaLightUpX, areaLightUpY, areaRightDownX, areaRightDownY };
				Box box = constructor.newInstance(args);
				boxes.put(id, box);
			}
			// デフォルトボックス
			int defaultId = unpacker.readInt();
			Properties p = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
			p.setProperty(Setting.RENDER_MESSAGE_BOX, defaultId);
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
