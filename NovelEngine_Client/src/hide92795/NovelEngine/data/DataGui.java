package hide92795.novelengine.data;

import java.util.HashMap;

public class DataGui extends Data {
	public static final int GUI_MESSAGEBOX = 0;
	public static final int GUI_WAITCURSOR = 1;
	public static final int GUI_PORTRAIT = 2;

	private final int boxImageId;
	private int boxXpos;
	private int boxYpos;
	private int boxNameXpos;
	private int boxNameYpos;
	private int boxWordsXpos;
	private int boxWordsYpos;
	private int boxWordsX1pos;
	private int boxWordsY1pos;

	private final int waitCursorImageId;
	private int waitCursorSize;
	private int waitCursorCount;
	private float[][] waitCursorList;
	
	private HashMap<Integer, int[]> portraitPosition;

	public DataGui() {
		this.boxImageId = "_MessageBox".hashCode();
		this.waitCursorImageId = "_WaitCursor".hashCode();
		portraitPosition = new HashMap<Integer, int[]>();
	}

	public final int getBoxImageId() {
		return boxImageId;
	}

	public final int getBoxXpos() {
		return boxXpos;
	}

	public final void setBoxXpos(int boxXpos) {
		this.boxXpos = boxXpos;
	}

	public final int getBoxYpos() {
		return boxYpos;
	}

	public final void setBoxYpos(int boxYpos) {
		this.boxYpos = boxYpos;
	}

	public final int getBoxNameXpos() {
		return boxNameXpos;
	}

	public final void setBoxNameXpos(int boxNameXpos) {
		this.boxNameXpos = boxNameXpos;
	}

	public final int getBoxNameYpos() {
		return boxNameYpos;
	}

	public final void setBoxNameYpos(int boxNameYpos) {
		this.boxNameYpos = boxNameYpos;
	}

	public final int getBoxWordsXpos() {
		return boxWordsXpos;
	}

	public final void setBoxWordsXpos(int boxWordsXpos) {
		this.boxWordsXpos = boxWordsXpos;
	}

	public final int getBoxWordsYpos() {
		return boxWordsYpos;
	}

	public final void setBoxWordsYpos(int boxWordsYpos) {
		this.boxWordsYpos = boxWordsYpos;
	}

	public final int getBoxWordsX1pos() {
		return boxWordsX1pos;
	}

	public final void setBoxWordsX1pos(int boxWordsX1pos) {
		this.boxWordsX1pos = boxWordsX1pos;
	}

	public final int getBoxWordsY1pos() {
		return boxWordsY1pos;
	}

	public final void setBoxWordsY1pos(int boxWordsY1pos) {
		this.boxWordsY1pos = boxWordsY1pos;
	}

	public final int getWaitCursorImageId() {
		return waitCursorImageId;
	}

	public final int getWaitCursorSize() {
		return waitCursorSize;
	}

	public final void setWaitCursorSize(int waitCursorSize) {
		this.waitCursorSize = waitCursorSize;
	}

	public final int getWaitCursorCount() {
		return waitCursorCount;
	}

	public final void setWaitCursorCount(int waitCursorCount) {
		this.waitCursorCount = waitCursorCount;
	}

	public final float[][] getWaitCursorList() {
		return waitCursorList;
	}

	public final void setWaitCursorList(float[][] waitCursorList) {
		this.waitCursorList = waitCursorList;
	}
	
	public void addPortraitPosition(int posId, int[] pos){
		this.portraitPosition.put(posId, pos);
	}
	
	public int[] getPortraitPosition(int posId){
		return this.portraitPosition.get(posId);
	}

}
