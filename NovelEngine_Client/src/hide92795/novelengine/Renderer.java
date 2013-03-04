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
package hide92795.novelengine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glColor4ub;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.client.NovelEngine;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.opengl.Texture;

/**
 * 描画に関する処理を行います。
 *
 * @author hide92795
 */
public class Renderer {
	/**
	 * このクラスはユーティリティクラスのためオブジェクト化できません。
	 */
	private Renderer() {
	}

	/**
	 * 指定したテクスチャを左上を起点に描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 */
	public static void renderImage(Texture texture) {
		renderImage(texture, 1.0f);
	}

	/**
	 * 指定したテクスチャを左上を起点に指定したアルファ値で描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 */
	public static void renderImage(Texture texture, float alpha) {
		renderImage(texture, alpha, 0.0f, 0.0f);
	}

	/**
	 * 指定したテクスチャを指定された点を起点に指定したアルファ値で描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y) {
		renderImage(texture, alpha, x, y, 1.0f);
	}

	/**
	 * 指定したテクスチャを指定された点を起点から指定した拡大率で拡大縮小し、指定したアルファ値で描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param magnificartion
	 *            拡大率
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float magnificartion) {
		renderImage(texture, alpha, x, y, x + texture.getTextureWidth() * magnificartion,
				y + texture.getTextureHeight() * magnificartion);
	}

	/**
	 * 指定したテクスチャを指定した範囲内に収まるように拡大縮小し、指定したアルファ値で描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param x1
	 *            画像を描画する右下の点のX座標
	 * @param y1
	 *            画像を描画する右下の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float x1, float y1) {
		texture.bind();
		glColor4d(1.0f, 1.0f, 1.0f, alpha);
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x1, y);
			glTexCoord2f(1, 1);
			glVertex2f(x1, y1);
			glTexCoord2f(0, 1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	/**
	 * 画面全体を指定した色で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 */
	public static void renderColor(float red, float green, float blue) {
		renderColor(red, green, blue, 1.0f);
	}

	/**
	 * 画面全体を指定した色とアルファ値で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 * @param alpha
	 *            アルファ値(0.0f～1.0fの範囲内)
	 */
	public static void renderColor(float red, float green, float blue, float alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.getEngine().getDefaultWidth(), NovelEngine.getEngine()
				.getDefaultHeight());
	}

	/**
	 * 指定した範囲内を指定した色とアルファ値で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 * @param alpha
	 *            アルファ値(0.0f～1.0fの範囲内)
	 * @param x
	 *            塗りつぶす範囲の左上の点のX座標
	 * @param y
	 *            塗りつぶす範囲の左上の点のY座標
	 * @param x1
	 *            塗りつぶす範囲の右下の点のX座標
	 * @param y1
	 *            塗りつぶす範囲の右下の点のY座標
	 */
	public static void renderColor(float red, float green, float blue, float alpha, float x, float y, float x1, float y1) {
		glColor4d(red, green, blue, alpha);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x1, y);
			glVertex2f(x1, y1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	/**
	 * 画面全体を指定した色で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 */
	public static void renderColor(byte red, byte green, byte blue) {
		renderColor(red, green, blue, (byte) 0xFF);
	}

	/**
	 * 画面全体を指定した色とアルファ値で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 * @param alpha
	 *            アルファ値(0～255の範囲内)
	 */
	public static void renderColor(byte red, byte green, byte blue, byte alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.getEngine().getDefaultWidth(), NovelEngine.getEngine()
				.getDefaultHeight());
	}

	/**
	 * 指定した範囲内を指定した色とアルファ値で塗りつぶします。
	 *
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 * @param alpha
	 *            アルファ値(0～255の範囲内)
	 * @param x
	 *            塗りつぶす範囲の左上の点のX座標
	 * @param y
	 *            塗りつぶす範囲の左上の点のY座標
	 * @param x1
	 *            塗りつぶす範囲の右下の点のX座標
	 * @param y1
	 *            塗りつぶす範囲の右下の点のY座標
	 */
	public static void renderColor(byte red, byte green, byte blue, byte alpha, float x, float y, float x1, float y1) {
		glColor4ub(red, green, blue, alpha);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x1, y);
			glVertex2f(x1, y1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	public static BufferedImage[] drawEdgedText(String words, Color inner, Color edge) {
		LinkedList<BufferedImage> list = new LinkedList<BufferedImage>();
		String textonly = words.replaceAll("\\[改行\\]", "\n");
		textonly = textonly.replaceAll("\\[サイズ\\s\\d+\\]", "");
		textonly = textonly.replaceAll("\\[\\/サイズ\\]", "");
		AttributedString as_inner = new AttributedString(textonly);
		AttributedString as_edge = new AttributedString(textonly);

		Font f = new Font("ＭＳ ゴシック", Font.PLAIN, 30);

		as_inner.addAttribute(TextAttribute.FONT, f);
		as_edge.addAttribute(TextAttribute.FONT, f);
		as_inner.addAttribute(TextAttribute.FOREGROUND, inner);
		as_edge.addAttribute(TextAttribute.FOREGROUND, edge);
		Pattern p = Pattern.compile("\\[サイズ\\s(\\d+)\\](.+)\\[/サイズ\\]");
		Matcher m = p.matcher(words);
		while (m.find()) {
			int size = Integer.parseInt(m.group(1));
			String str = m.group(2);
			int begin = textonly.indexOf(str);
			int end = begin + str.length() - 1;
			Font f1 = new Font("ＭＳ ゴシック", Font.PLAIN, 30 * size / 100);
			as_inner.addAttribute(TextAttribute.FONT, f1, begin, end);
			as_edge.addAttribute(TextAttribute.FONT, f1, begin, end);
		}

		LineBreakMeasurer lbm_inner = new LineBreakMeasurer(as_inner.getIterator(), new FontRenderContext(null, true,
				true));
		LineBreakMeasurer lbm_edge = new LineBreakMeasurer(as_edge.getIterator(), new FontRenderContext(null, true,
				true));

		while (lbm_inner.getPosition() < textonly.length()) {
			TextLayout tl_inner = lbm_inner.nextLayout(610);
			TextLayout tl_edge = lbm_edge.nextLayout(610);
			int w = (int) Math.ceil(tl_inner.getAdvance()) + 4;
			int h = (int) Math.ceil(tl_inner.getDescent() + tl_inner.getAscent()) + 4;
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2d = bi.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			int x = 1, y = h - 6;
			tl_edge.draw(g2d, x - 1, y);
			tl_edge.draw(g2d, x - 1, y - 1);
			tl_edge.draw(g2d, x, y - 1);
			tl_edge.draw(g2d, x + 1, y - 1);
			tl_edge.draw(g2d, x + 1, y);
			tl_edge.draw(g2d, x + 1, y + 1);
			tl_edge.draw(g2d, x, y + 1);
			tl_edge.draw(g2d, x - 1, y + 1);
			tl_inner.draw(g2d, x, y);
			// g2d.drawRect(0, 0, w - 2, h - 1);
			list.add(bi);
		}
		return list.toArray(new BufferedImage[0]);
	}
}
