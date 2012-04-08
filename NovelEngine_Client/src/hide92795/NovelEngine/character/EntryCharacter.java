package hide92795.novelengine.character;

import hide92795.novelengine.client.NovelEngine;

public class EntryCharacter {
	protected NovelEngine engine;
	private final int placeId;
	protected Character primaryCharacter;
	protected int primaryFaceID;
	private Character secondaryCharacter;
	private int secondaryFaceID;
	private double alpha;
	private double x;
	private double y;

	public EntryCharacter(NovelEngine engine, int placeId) {
		this.engine = engine;
		this.placeId = placeId;
		int[] pos = engine.dataGui.getPortraitPosition(placeId);
		x = pos[0];
		y = pos[1];
	}

	public void render() {
		if (primaryCharacter != null) {
			primaryCharacter.render(engine, primaryFaceID, x, y, alpha);
		}
		if (secondaryCharacter != null) {
			secondaryCharacter.render(engine, secondaryFaceID, x, y,
					1.0d - alpha);
		}

	}

	public void finish() {
		primaryCharacter = secondaryCharacter;
		primaryFaceID = secondaryFaceID;
		secondaryCharacter = null;
		secondaryFaceID = 0;
		alpha = 1.0d;
	}

	public final int getPositionID() {
		return placeId;
	}

	public final Character getSecondaryCharacter() {
		return secondaryCharacter;
	}

	public final void setSecondaryCharacter(Character secondaryCharacter) {
		this.secondaryCharacter = secondaryCharacter;
	}

	public final int getSecondaryFaceID() {
		return secondaryFaceID;
	}

	public final void setSecondaryFaceID(int secondaryFaceID) {
		this.secondaryFaceID = secondaryFaceID;
	}

	public final double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public boolean isChanging() {
		if (secondaryCharacter == null) {
			return false;
		}
		return true;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
