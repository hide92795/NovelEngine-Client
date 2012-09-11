package hide92795.novelengine;

import static org.lwjgl.opengl.GL11.*;
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

import hide92795.novelengine.client.NovelEngine;

import org.newdawn.slick.opengl.Texture;

/**
 * 描画に関する処理を行います。
 *
 * @author hide92795
 */
public class Renderer {
	/**
	 * 指定したテクスチャを左上を起点に描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 */
	public static void renderBgImage(Texture texture) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, 1.0f, 0, 0, texture.getTextureWidth(), texture.getTextureHeight());
	}

	/**
	 * 指定したテクスチャを指定したアルファ値で描画します。
	 *
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 */
	public static void renderBgImage(Texture texture, float alpha) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, alpha, 0, 0, texture.getTextureWidth(), texture.getTextureHeight());
	}

	public static void renderImage(Texture texture, float x, float y, float alpha, float magnificartion) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, alpha, x, y, x + texture.getTextureWidth() * magnificartion,
				y + texture.getTextureHeight() * magnificartion);
	}

	public static void renderImage(Texture texture, float alpha, float x, float y, float x1, float y1) {
		renderImage(texture, 1.0f, 1.0f, 1.0f, alpha, x, y, x1, y1);
	}

	public static void renderImage(Texture texture, float red, float green, float blue, float a, float x, float y,
			float x1, float y1) {
		texture.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4d(red, green, blue, a);
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

	public static void renderColor(float red, float green, float blue) {
		renderColor(red, green, blue, 1.0f, 0, 0, NovelEngine.theEngine.dataBasic.getWidth(),
				NovelEngine.theEngine.dataBasic.getHeight());
	}

	public static void renderColor(float red, float green, float blue, float alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.theEngine.dataBasic.getWidth(),
				NovelEngine.theEngine.dataBasic.getHeight());
	}

	public static void renderColor(float red, float green, float blue, float alpha, float x, float y, float x1, float y1) {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4d(red, green, blue, alpha);
			glVertex2f(x, y);
			glVertex2f(x1, y);
			glVertex2f(x1, y1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	public static void renderColor(byte red, byte green, byte blue) {
		renderColor(red, green, blue, 1.0f, 0, 0, NovelEngine.theEngine.dataBasic.getWidth(),
				NovelEngine.theEngine.dataBasic.getHeight());
	}

	public static void renderColor(byte red, byte green, byte blue, byte alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.theEngine.dataBasic.getWidth(),
				NovelEngine.theEngine.dataBasic.getHeight());
	}

	public static void renderColor(byte red, byte green, byte blue, byte alpha, float x, float y, float x1, float y1) {
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4ub(red, green, blue, alpha);
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
