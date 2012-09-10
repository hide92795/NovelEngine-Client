package hide92795.novelengine.loader.item;

public class DataBasic extends Data {
	private String gamename;
	private String version;
	private int width;
	private int height;
	private int minWidth;
	private int minHeight;
	private boolean arrowResize;
//	private boolean isShowMaximizeButton;

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

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public boolean isArrowResize() {
		return arrowResize;
	}

	public void setArrowResize(boolean arrowResize) {
		this.arrowResize = arrowResize;
	}
}
