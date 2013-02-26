package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.data.DataButton;
import hide92795.novelengine.manager.gui.ButtonManager.PositionSet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

/**
 * ボタンデータを外部ファイルから読み込むためのクラスです。
 *
 * @author hide92795
 */
public class LoaderButton extends Loader {

	/**
	 * ボタンデータを読み込みマップに格納します。
	 *
	 * @param loader
	 *            リソースをロードするためのローダー
	 * @param buttons
	 *            ボタンデータを保管するマップ
	 * @param positions
	 *            ボタンの位置データを保管するマップ
	 */
	public static void load(LoaderResource loader, HashMap<Integer, DataButton> buttons,
			HashMap<Integer, PositionSet> positions) {
		File path = new File(NovelEngine.getCurrentDir(), "object");
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(new File(path, "button.neo"));
			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			// ボタン本体
			int num_b = unpacker.readInt();

			for (int i = 0; i < num_b; i++) {
				int id = unpacker.readInt();
				DataButton button = new DataButton();
				button.setImageNormalId(unpacker.readInt());
				button.setImageOnMouseId(unpacker.readInt());
				button.setImageDisabledId(unpacker.readInt());
				button.setImageClickedId(unpacker.readInt());
				button.setCliclableId(unpacker.readInt());
				button.setTextXPos(unpacker.readInt());
				button.setTextYPos(unpacker.readInt());
				button.setWidth(unpacker.readInt());
				button.setHeight(unpacker.readInt());

				loader.loadImage(button.getImageNormalId());
				loader.loadImage(button.getImageOnMouseId());
				loader.loadImage(button.getImageDisabledId());
				loader.loadImage(button.getImageClickedId());

				buttons.put(id, button);
			}

			// ボタン位置
			int num_p = unpacker.readInt();

			for (int i = 0; i < num_p; i++) {
				int id = unpacker.readInt();
				int num_ps = unpacker.readInt();
				PositionSet ps = new PositionSet(num_ps);
				for (int order = 0; order < num_ps; order++) {
					int x = unpacker.readInt();
					int y = unpacker.readInt();
					ps.addPosition(order, x, y);
				}
				positions.put(id, ps);
			}

		} catch (Exception e) {
			throw new NovelEngineException(e, "");
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (IOException e) {
					throw new NovelEngineException(e, "");
				}
			}
		}
	}

}
