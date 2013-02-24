package hide92795.novelengine.manager;

import hide92795.novelengine.character.DataCharacter;
import hide92795.novelengine.loader.LoaderCharacter;

import java.util.HashMap;

/**
 * キャラクターデータを管理するマネージャーです。
 *
 * @author hide92795
 */
public class CharacterManager {
	/**
	 * キャラクターデータを管理するマップです。
	 */
	private HashMap<Integer, DataCharacter> characters;
	/**
	 * 位置データを管理するマップです。
	 */
	private HashMap<Integer, int[]> positions;

	/**
	 * {@link hide92795.novelengine.manager.CharacterManager CharacterManager} のオブジェクトを生成します。
	 */
	public CharacterManager() {
		this.characters = new HashMap<Integer, DataCharacter>();
		this.positions = new HashMap<Integer, int[]>();
		load();
	}

	/**
	 * 外部データからキャラクターデータを読み込みます。<br>
	 * この段階で読み込まれるのはキャラクターの位置や表情などのデータであり、画像データは必要時に読み込まれます。
	 */
	private void load() {
		LoaderCharacter.load(this);
	}

	/**
	 * キャラクターを追加します。
	 *
	 * @param id
	 *            キャラクターデータのID
	 * @param data
	 *            キャラクターデータ
	 */
	public final void addCharacter(int id, DataCharacter data) {
		characters.put(id, data);
	}

	/**
	 * 指定されたIDのキャラクターデータを取得します。
	 *
	 * @param characterId
	 *            キャラクターデータのID
	 * @return キャラクターデータ
	 */
	public final DataCharacter getCharacter(int characterId) {
		return characters.get(characterId);
	}

	/**
	 * 位置データを追加します。
	 *
	 * @param positionId
	 *            位置データのID
	 * @param position
	 *            位置データ
	 */
	public final void addPosition(int positionId, int[] position) {
		positions.put(positionId, position);
	}

	/**
	 * 指定されたIDの位置データを取得します。
	 *
	 * @param positionId
	 *            位置データのID
	 * @return 位置データ
	 */
	public final int[] getPosition(int positionId) {
		return positions.get(positionId);
	}

}
