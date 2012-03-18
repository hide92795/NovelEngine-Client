package hide92795.novelengine.data;

import java.util.HashMap;
import hide92795.novelengine.Character;

public class DataCharacter extends Data {
	private HashMap<Integer, Character> characters;

	public DataCharacter() {
		characters = new HashMap<Integer, Character>();
	}

	public void addCharacter(Character c) {
		characters.put(c.getCharacterId(), c);
	}
	
	public Character getCharacter(int characterId){
		return characters.get(characterId);
	}
}
