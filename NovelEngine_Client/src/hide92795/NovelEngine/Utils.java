package hide92795.NovelEngine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;

public class Utils {
	public static Color integerToColor(int color) {
		return new Color((color & 0x000000FF), (color & 0x0000FF00) >> 8,
				(color & 0x00FF0000) >> 16);
	}

	public static AttributedString parceWords(String words) {
		String textonly = words.replaceAll("\\[改行\\]", "\n");
		textonly = textonly.replaceAll("\\[サイズ\\s\\d+\\]", "");
		textonly = textonly.replaceAll("\\[\\/サイズ\\]", "");
		AttributedString as = new AttributedString(textonly);
		Font f = new Font("メイリオ", Font.PLAIN, 20);
		as.addAttribute(TextAttribute.FONT, f);
		Pattern p = Pattern.compile("\\[サイズ\\s(\\d+)\\](.+)\\[/サイズ\\]");
		Matcher m = p.matcher(words);
		while (m.find()) {
			int size = Integer.parseInt(m.group(1));
			String str = m.group(2);
			int begin = textonly.indexOf(str);
			int end = begin + str.length() - 1;
			Font f1 = new Font("メイリオ", Font.PLAIN, 20 * size / 100);
			as.addAttribute(TextAttribute.FONT, f1, begin, end);
		}
		return as;
	}

	public static void renderBgImage(Texture tex) {
		tex.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(tex.getTextureWidth(), 0);
			glTexCoord2f(1, 1);
			glVertex2f(tex.getTextureWidth(), tex.getTextureHeight());
			glTexCoord2f(0, 1);
			glVertex2f(0, tex.getTextureHeight());
		}
		glEnd();
	}
}
