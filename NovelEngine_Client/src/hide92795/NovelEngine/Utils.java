package hide92795.NovelEngine;
import org.lwjgl.util.Color;



public class Utils {
	public static Color integerToColor(int color) {
		return new Color((color & 0x000000FF), (color & 0x0000FF00) >> 8,
				(color & 0x00FF0000) >> 16);
	}
}
