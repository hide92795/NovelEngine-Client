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
package hide92795.novelengine.background.figure;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;

/**
 * {@link hide92795.novelengine.background.BackGround BackGround} の描画範囲を決めるステンシル領域の描画に関する処理をします。
 * 
 * @author hide92795
 */
public abstract class Figure {
	/**
	 * 画面全体を範囲とするフィギュアデータです。
	 */
	public static final byte FIGURE_ENTIRE_SCREEN = 0;
	/**
	 * 三角形を範囲とするフィギュアデータです。
	 */
	public static final byte FIGURE_TRIANGLE = 1;
	/**
	 * 四角形を範囲とするフィギュアデータです。
	 */
	public static final byte FIGURE_QUADANGLE = 2;
	/**
	 * 多角形を範囲とするフィギュアデータです。
	 */
	public static final byte FIGURE_POLYGON = 3;
	/**
	 * 円形を範囲とするフィギュアデータです。
	 */
	public static final byte FIGURE_CIRCLE = 4;

	/**
	 * 描画する線を表す配列です。
	 */
	private final Line[] lines;

	/**
	 * 描画範囲を決めるフィギュアデータを生成します。
	 * 
	 * @param lines
	 *            描画する線を表す配列
	 */
	public Figure(Line[] lines) {
		this.lines = lines;
	}

	/**
	 * ステンシルバッファに領域を書き込みます。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public abstract void renderStencil(NovelEngine engine);

	/**
	 * 範囲の境目の線を描画します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public abstract void renderLine(NovelEngine engine);

	/**
	 * 描画する線を表す配列を取得します。
	 * 
	 * @return 描画する線を表す配列
	 */
	protected Line[] getLines() {
		return lines;
	}

	/**
	 * 描画範囲の境界に描画する線の情報を表します。
	 * 
	 * @author hide92795
	 */
	public static class Line {
		/**
		 * 線の端点の番号です。
		 */
		private final int start;
		/**
		 * 線のもう片方の端点の番号です。
		 */
		private final int end;
		/**
		 * 線の色の赤色成分です。
		 */
		private final byte red;
		/**
		 * 線の色の緑色成分です。
		 */
		private final byte green;
		/**
		 * 線の色の青色成分です。
		 */
		private final byte blue;
		/**
		 * 線の色のアルファ値です。
		 */
		private final byte alpha;
		/**
		 * 線の太さです。
		 */
		private final int width;

		/**
		 * 描画範囲の境界に描画する線の情報を作成します。
		 * 
		 * @param start
		 *            線の端点の番号
		 * @param end
		 *            線のもう片方の端点の番号
		 * @param red
		 *            線の色の赤色成分
		 * @param green
		 *            線の色の緑色成分
		 * @param blue
		 *            線の色の青色成分
		 * @param alpha
		 *            線の色のアルファ値
		 * @param width
		 *            線の太さ
		 */
		public Line(int start, int end, byte red, byte green, byte blue, byte alpha, int width) {
			this.start = start;
			this.end = end;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
			this.width = width;
		}

		/**
		 * 描画範囲の境界に線を描画します。
		 * 
		 * @param apexes
		 *            頂点の座標の配列
		 */
		public void render(int[][] apexes) {
			int x = apexes[start][0];
			int y = apexes[start][1];
			int x1 = apexes[end][0];
			int y1 = apexes[end][1];
			Renderer.renderLine(red, green, blue, alpha, x, y, x1, y1, width);
		}

		/**
		 * 描画範囲の境界に線を描画します。
		 * 
		 * @param apexes
		 *            頂点の座標の配列
		 */
		public void renderAsConsecutiveLine(float[][] apexes) {
			Renderer.renderLineAsConsecutive(red, green, blue, alpha, width, start, end, apexes);
		}
	}
}
