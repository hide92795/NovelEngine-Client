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

import hide92795.novelengine.Properties;
import hide92795.novelengine.box.Box;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
import hide92795.novelengine.manager.FontManager.DataFont;
import hide92795.novelengine.queue.QueueImage;
import hide92795.novelengine.words.EntityWords;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文章データを管理するマネージャーです。
 * 
 * @author hide92795
 */
public class WordsManager {
	/**
	 * 文字用のイメージIDです。
	 */
	private int nextImageId;

	/**
	 * {@link hide92795.novelengine.manager.WordsManager WordsManager} のオブジェクトを生成します。
	 */
	public WordsManager() {
		nextImageId = 0;
	}

	/**
	 * 重複のない、文字用のイメージIDを取得します。
	 * 
	 * @return 次に使用可能なイメージID
	 */
	public int getNextImageId() {
		nextImageId--;
		return nextImageId;
	}

	/**
	 * 指定された文字列の画像を作成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param words
	 *            作成したデータを格納する文章データ
	 * @param words_str
	 *            作成する文字列
	 * @param initVariable
	 *            初期変数
	 */
	public void createWords(NovelEngine engine, EntityWords words, String words_str,
			ConcurrentHashMap<String, Integer> initVariable) {
		Properties renderProp = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);

		int boxId;
		if (initVariable.containsKey(ConfigurationManager.VARIABLE_RENDER + Setting.RENDER_MESSAGE_BOX)) {
			boxId = initVariable.get(ConfigurationManager.VARIABLE_RENDER + Setting.RENDER_MESSAGE_BOX);
		} else {
			boxId = renderProp.getProperty(Setting.RENDER_MESSAGE_BOX);
		}
		Box box = engine.getBoxManager().getBox(boxId);
		int width = box.getAreaWidth();

		int fontId;
		if (initVariable.containsKey(ConfigurationManager.VARIABLE_RENDER + Setting.RENDER_FONT)) {
			fontId = initVariable.get(ConfigurationManager.VARIABLE_RENDER + Setting.RENDER_FONT);
		} else {
			fontId = renderProp.getProperty(Setting.RENDER_FONT);
		}
		DataFont fontdata = engine.getFontManager().getFont(fontId);

		BufferedImage[] images = drawText(words_str, width, fontdata);

		for (BufferedImage image : images) {
			int imageId = getNextImageId();
			QueueImage q = new QueueImage(engine, imageId, image);
			engine.getQueueManager().enqueue(q);
			words.addLine(imageId, image.getWidth(), image.getHeight());
		}
		words.addFinish();
	}

	/**
	 * 縁取り文字の画像を作成します。
	 * 
	 * @param words
	 *            作成する文字列
	 * @param width
	 *            1行の横幅
	 * @param fontdata
	 *            使用するフォントデータ
	 * @return 行ごとに分けられた画像
	 */
	public BufferedImage[] drawText(String words, int width, DataFont fontdata) {
		Font font = fontdata.getFont();
		int decorationType = fontdata.getDecorationType();
		Color inner = fontdata.getInnerColor();
		Color edge = fontdata.getEdgeColor();

		LinkedList<BufferedImage> list = new LinkedList<BufferedImage>();
		String textonly = words.replaceAll("\\[改行\\]", "\n");
		textonly = textonly.replaceAll("\\[サイズ\\s\\d+\\]", "");
		textonly = textonly.replaceAll("\\[\\/サイズ\\]", "");
		AttributedString as_inner = new AttributedString(textonly);
		AttributedString as_edge = new AttributedString(textonly);

		as_inner.addAttribute(TextAttribute.FONT, font);
		as_edge.addAttribute(TextAttribute.FONT, font);
		as_inner.addAttribute(TextAttribute.FOREGROUND, inner);
		as_edge.addAttribute(TextAttribute.FOREGROUND, edge);
		Pattern p = Pattern.compile("\\[サイズ\\s(\\d+)\\](.+)\\[/サイズ\\]");
		Matcher m = p.matcher(words);
		while (m.find()) {
			int size = Integer.parseInt(m.group(1));
			String str = m.group(2);
			int begin = textonly.indexOf(str);
			int end = begin + str.length() - 1;
			Font f1 = font.deriveFont((float) 30 * size / 100);
			as_inner.addAttribute(TextAttribute.FONT, f1, begin, end);
			as_edge.addAttribute(TextAttribute.FONT, f1, begin, end);
		}

		LineBreakMeasurer lbm_inner = new LineBreakMeasurer(as_inner.getIterator(), new FontRenderContext(null, true,
				true));
		LineBreakMeasurer lbm_edge = new LineBreakMeasurer(as_edge.getIterator(), new FontRenderContext(null, true,
				true));

		while (lbm_inner.getPosition() < textonly.length()) {
			TextLayout tl_inner = lbm_inner.nextLayout(width);
			TextLayout tl_edge = lbm_edge.nextLayout(width);
			int w = (int) Math.ceil(tl_inner.getAdvance()) + 4;
			int h = (int) Math.ceil(tl_inner.getDescent() + tl_inner.getAscent()) + 4;
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2d = bi.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int x = 1, y = h - 6;
			switch (decorationType) {
			case FontManager.TEXT_NONE:
				tl_inner.draw(g2d, x, y);
				break;
			case FontManager.TEXT_SHADOWED:
				tl_edge.draw(g2d, x + 1, y);
				tl_edge.draw(g2d, x + 1, y + 1);
				tl_edge.draw(g2d, x, y + 1);
				tl_inner.draw(g2d, x, y);
				break;
			case FontManager.TEXT_EDGED:
				tl_edge.draw(g2d, x - 1, y);
				tl_edge.draw(g2d, x - 1, y - 1);
				tl_edge.draw(g2d, x, y - 1);
				tl_edge.draw(g2d, x + 1, y - 1);
				tl_edge.draw(g2d, x + 1, y);
				tl_edge.draw(g2d, x + 1, y + 1);
				tl_edge.draw(g2d, x, y + 1);
				tl_edge.draw(g2d, x - 1, y + 1);
				tl_inner.draw(g2d, x, y);
				break;
			default:
				break;
			}
			list.add(bi);
		}
		return list.toArray(new BufferedImage[0]);
	}
}
