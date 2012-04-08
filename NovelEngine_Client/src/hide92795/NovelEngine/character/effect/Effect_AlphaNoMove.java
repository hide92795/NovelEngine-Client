package hide92795.novelengine.character.effect;

public class Effect_AlphaNoMove extends CharacterEffect {
	private int totalTime;
	private int elapsedTime;

	public Effect_AlphaNoMove(int time) {
		totalTime = time;
	}

	@Override
	public void update(int delta) {
		if(!isFinish()){
			elapsedTime += delta;
			double alpha = (double) elapsedTime / totalTime;
			if (alpha >= 1.0d) {
				finish();
				return;
			}
			setAlpha(1.0d - alpha);
		}
		
	}
}
