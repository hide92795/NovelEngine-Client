package hide92795.novelengine.manager;

import hide92795.novelengine.character.EntryCharacter;
import hide92795.novelengine.client.NovelEngine;

import java.util.HashMap;
import java.util.Set;

public class CharacterManager {
	/**
	 * Key:位置ID Val:キャラ
	 */
	private HashMap<Integer, EntryCharacter> characters;
	public NovelEngine engine;

	public CharacterManager(NovelEngine engine) {
		this.engine = engine;
		characters = new HashMap<Integer, EntryCharacter>();
	}

	public void render() {
		Set<Integer> set = characters.keySet();
		for (int i : set) {
			EntryCharacter ec = characters.get(i);
			ec.render();
		}
	}

	public EntryCharacter get(int posId) {
		EntryCharacter ec = characters.get(posId);
		if (ec == null) {
			ec = new EntryCharacter(engine, posId);
			characters.put(posId, ec);
		}
		
		return ec;
	}

	public void reset() {
		characters.clear();
	}
}