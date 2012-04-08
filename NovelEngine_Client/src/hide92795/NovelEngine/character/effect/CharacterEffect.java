package hide92795.novelengine.character.effect;

import hide92795.novelengine.character.EntryCharacter;

public abstract class CharacterEffect {

	protected EntryCharacter entry;

	public final void setEntry(EntryCharacter entry) {
		this.entry = entry;
	}

	public abstract void update(int delta);

	public final double getX() {
		return entry.getX();
	}

	public final double getY() {
		return entry.getY();
	}

	public final void setX(double x) {
		entry.setX(x);
	}

	public final void setY(double y) {
		entry.setY(y);
	}

	public final void setXY(double x, double y) {
		entry.setX(x);
		entry.setY(y);
	}

	public final double getAlpha() {
		return entry.getAlpha();
	}

	public final void setAlpha(double alpha) {
		entry.setAlpha(alpha);
	}

	public final boolean isFinish() {
		boolean b = entry.isChanging();
		return !b;
	}

	public final void finish() {
		entry.finish();
	}
}
