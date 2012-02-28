package hide92795.novelengine;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.util.Color;

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
}
