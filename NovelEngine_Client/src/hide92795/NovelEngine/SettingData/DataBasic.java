package hide92795.NovelEngine.SettingData;

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

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public byte[][] getIcons() {
		return icons;
	}

	public void setIcons(byte[][] icons) {
		this.icons = icons;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isR18() {
		return isR18;
	}

	public void setR18(boolean isR18) {
		this.isR18 = isR18;
	}

	public boolean isArrowResize() {
		return arrowResize;
	}

	public void setArrowResize(boolean arrowResize) {
		this.arrowResize = arrowResize;
	}

	public boolean isShowMaximizeButton() {
		return isShowMaximizeButton;
	}

	public void setShowMaximizeButton(boolean isShowMaximizeButton) {
		this.isShowMaximizeButton = isShowMaximizeButton;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[][] getLogoSound() {
		return logoSound;
	}

	public void setLogoSound(byte[][] logoSound) {
		this.logoSound = logoSound;
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

	public int[] getAspect() {
		return aspect;
	}

	public void setAspect(int[] aspect) {
		this.aspect = aspect;
	}
}
