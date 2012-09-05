package hide92795.novelengine.gui;

public class Button {
	private final int posX;
	private final int posY;
	private final int width;
	private final int height;
	private final int textureNormalId;
	private final int textureOnMouseId;
	private final byte[] clickable;

	public Button(int posX, int posY, byte[] clickable, int width, int height, int textureNormalId, int textureOnMouseId) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.textureNormalId = textureNormalId;
		this.textureOnMouseId = textureOnMouseId;
		this.clickable = clickable;
	}

	private boolean isClickable(int x, int y) {
		int color = getColorAt(x, y);
		if (color == -16777216) {
			return true;
		} else {
			return false;
		}
	}

	private int getColorAt(int x, int y) {
		int offset = x + (y * width);
		offset *= 4;
		int r = translate(clickable[offset]);
		int g = translate(clickable[offset + 1]);
		int b = translate(clickable[offset + 2]);
		return ((255 & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
	}

	private int translate(byte b) {
		if (b < 0) {
			return 256 + b;
		}
		return b;
	}

}
