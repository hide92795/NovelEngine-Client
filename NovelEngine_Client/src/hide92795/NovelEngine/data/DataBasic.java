package hide92795.novelengine.data;

public class DataBasic extends Data {
	private String gamename;
	private byte[][] icons;
	private String version;
	private int width;
	private int height;
	private boolean isR18;
	private boolean arrowResize;
	private boolean isShowMaximizeButton;
	private byte[] logo;
	private byte[][] logoSound;
	private int minWidth;
	private int minHeight;
	private int[] aspect;

	public final String getGamename() {
		return gamename;
	}

	public final void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public final byte[][] getIcons() {
		return icons;
	}

	public final void setIcons(byte[][] icons) {
		this.icons = icons;
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

	public final boolean isR18() {
		return isR18;
	}

	public final void setR18(boolean isR18) {
		this.isR18 = isR18;
	}

	public final boolean isArrowResize() {
		return arrowResize;
	}

	public final void setArrowResize(boolean arrowResize) {
		this.arrowResize = arrowResize;
	}

	public final boolean isShowMaximizeButton() {
		return isShowMaximizeButton;
	}

	public final void setShowMaximizeButton(boolean isShowMaximizeButton) {
		this.isShowMaximizeButton = isShowMaximizeButton;
	}

	public final byte[] getLogo() {
		return logo;
	}

	public final void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public final byte[][] getLogoSound() {
		return logoSound;
	}

	public final void setLogoSound(byte[][] logoSound) {
		this.logoSound = logoSound;
	}

	public final int getMinWidth() {
		return minWidth;
	}

	public final void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public final int getMinHeight() {
		return minHeight;
	}

	public final void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public final int[] getAspect() {
		return aspect;
	}

	public final void setAspect(int[] aspect) {
		this.aspect = aspect;
	}
}
