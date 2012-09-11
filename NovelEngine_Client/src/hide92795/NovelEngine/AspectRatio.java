package hide92795.novelengine;

import org.lwjgl.opengl.GL11;

public class AspectRatio {
	private final int ratioWidth;
	private final int ratioHeight;

	public AspectRatio(int width, int height) {
		this.ratioWidth = width;
		this.ratioHeight = height;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AspectRatio#");
		sb.append(ratioWidth);
		sb.append(":");
		sb.append(ratioHeight);
		return sb.toString();
	}

	public void adjust(int width, int height) {
		int x = 0, y = height, w = width, h = height;
		//横を基準に縦の長さを求める
		int adj_height = Math.round((float) width * ratioHeight / ratioWidth);
		if (height > adj_height) {
			//横はそのまま 縦を短く
			x = 0;
			y = (height - adj_height) / 2;
			w = width;
			h = adj_height;
		} else if (height < adj_height) {
			//縦はそのまま 横を短く
			int adj_width = Math.round((float) height * ratioWidth / ratioHeight);
			x = (width - adj_width) / 2;
			y = 0;
			w = adj_width;
			h = height;
		}
		GL11.glViewport(x, y, w, h);
	}
}
