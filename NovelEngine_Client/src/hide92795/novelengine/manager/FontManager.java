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

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderFont;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

/**
 * フォントデータを管理するためのマネージャーです。
 * 
 * @author hide92795
 */
public class FontManager {
	/**
	 * 装飾なしの文字を表します。
	 */
	public static final int TEXT_NONE = 0;
	/**
	 * 影付き文字を表します。
	 */
	public static final int TEXT_SHADOWED = 1;
	/**
	 * 縁取り文字を表します。
	 */
	public static final int TEXT_EDGED = 2;
	/**
	 * フォントデータを格納するマップです。
	 */
	private HashMap<Integer, DataFont> fonts;

	/**
	 * {@link hide92795.novelengine.manager.FontManager FontManager} のオブジェクトを生成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public FontManager(NovelEngine engine) {
		fonts = new HashMap<Integer, DataFont>();
		load(engine);
	}

	/**
	 * フォントデータをロードします。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	private void load(NovelEngine engine) {
		LoaderFont.load(engine, fonts);
	}

	/**
	 * 指定したIDのフォントを取得します。
	 * 
	 * @param fontId
	 *            取得するフォントのID
	 * @return フォントデータ
	 */
	public DataFont getFont(int fontId) {
		return fonts.get(fontId);
	}

	/**
	 * フォントデータを保存します。
	 * 
	 * @author hide92795
	 */
	public static class DataFont {
		/**
		 * このフォントデータが表すフォントです。
		 */
		private final Font font;
		/**
		 * フォントの装飾タイプを表します。
		 */
		private final int decorationType;
		/**
		 * このフォントの文字色です。
		 */
		private final Color inner;
		/**
		 * このフォントの輪郭の色です。
		 */
		private final Color edge;

		/**
		 * @param font
		 *            このフォントデータが表すフォント
		 * @param decorationType
		 *            フォントの装飾タイプ
		 * @param inner
		 *            このフォントの文字色
		 * @param edge
		 *            このフォントの輪郭の色
		 */
		public DataFont(Font font, int decorationType, Color inner, Color edge) {
			this.font = font;
			this.decorationType = decorationType;
			this.inner = inner;
			this.edge = edge;
		}

		/**
		 * このフォントデータが表すフォントを取得します。
		 * 
		 * @return このフォントデータが表すフォント
		 */
		public final Font getFont() {
			return font;
		}

		/**
		 * フォントの装飾タイプを取得します。
		 * 
		 * @return フォントの装飾タイプ
		 */
		public final int getDecorationType() {
			return decorationType;
		}

		/**
		 * このフォントの文字色を取得します。
		 * 
		 * @return このフォントの文字色
		 */
		public final Color getInnerColor() {
			return inner;
		}

		/**
		 * このフォントの輪郭の色を取得します。
		 * 
		 * @return このフォントの輪郭の色
		 */
		public final Color getEdgeColor() {
			return edge;
		}
	}
}
