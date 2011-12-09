package hide92795.NovelEngine.SettingData;

import hide92795.NovelEngine.Gui.Button;

public class DataMainMenu extends Data {
	private int[] backImageIds;
	private int backMusic;
	private Button[] buttons;
	private int[] buttonRenderingSeq;
	private byte[] temp;

	public int[] getBackImageIds() {
		return backImageIds;
	}

	public void setBackImageIds(int[] backImage) {
		this.backImageIds = backImage;
	}

	public int getBackMusic() {
		return backMusic;
	}

	public void setBackMusic(int backMusic) {
		this.backMusic = backMusic;
	}

	public Button[] getButtons() {
		return buttons;
	}

	public void setButtons(Button[] objects) {
		this.buttons = objects;
	}

	public int[] getButtonRenderingSeq() {
		return buttonRenderingSeq;
	}

	public void setButtonRenderingSeq(int[] buttonRenderingSeq) {
		this.buttonRenderingSeq = buttonRenderingSeq;
	}

	public void setTemp(byte[] _image) {
		this.temp = _image;
	}
	
	public byte[] getTemp() {
		return temp;
	}
}
