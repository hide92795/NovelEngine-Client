package hide92795.novelengine.data;

import hide92795.novelengine.gui.Button;

public class DataMainMenu extends Data {
	private int[] backImageIds;
	private int backMusic;
	private Button[] buttons;
	private int[] buttonRenderingSeq;
	private byte[] temp;

	public final int[] getBackImageIds() {
		return backImageIds;
	}

	public final void setBackImageIds(int[] backImage) {
		this.backImageIds = backImage;
	}

	public final int getBackMusic() {
		return backMusic;
	}

	public final void setBackMusic(int backMusic) {
		this.backMusic = backMusic;
	}

	public final Button[] getButtons() {
		return buttons;
	}

	public final void setButtons(Button[] objects) {
		this.buttons = objects;
	}

	public final int[] getButtonRenderingSeq() {
		return buttonRenderingSeq;
	}

	public final void setButtonRenderingSeq(int[] buttonRenderingSeq) {
		this.buttonRenderingSeq = buttonRenderingSeq;
	}

	public final void setTemp(byte[] _image) {
		this.temp = _image;
	}
	
	public final byte[] getTemp() {
		return temp;
	}
}
