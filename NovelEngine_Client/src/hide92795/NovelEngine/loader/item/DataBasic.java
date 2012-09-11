package hide92795.novelengine.loader.item;

import hide92795.novelengine.AspectRatio;

import java.nio.ByteBuffer;

public class DataBasic extends Data {
	private String gamename;
	private String version;
	private int width;
	private int height;
	private boolean arrowResize;
	private ByteBuffer[] icons;
	private AspectRatio aspectRatio;

	public final String getGamename() {
		return gamename;
	}

	public final void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public final String getVersion() {
		return version;
	}

	public final void setVersion(String version) {
		this.version = version;
	}

	public final int getWidth() {
		return width;
	}

	public final void setWidth(int width) {
		this.width = width;
	}

	public final int getHeight() {
		return height;
	}

	public final void setHeight(int height) {
		this.height = height;
	}

	public final boolean isArrowResize() {
		return arrowResize;
	}

	public final void setArrowResize(boolean arrowResize) {
		this.arrowResize = arrowResize;
	}

	public final ByteBuffer[] getIcons() {
		return icons;
	}

	public final void setIcons(ByteBuffer[] icons) {
		this.icons = icons;
	}

	public final AspectRatio getAspectRatio() {
		return aspectRatio;
	}

	public final void setAspectRatio(int width, int height) {
		this.aspectRatio = new AspectRatio(width, height);
	}
}
