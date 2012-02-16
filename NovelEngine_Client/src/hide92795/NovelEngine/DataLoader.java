package hide92795.NovelEngine;

import hide92795.NovelEngine.client.NovelEngine;
import hide92795.NovelEngine.command.CommandButton;
import hide92795.NovelEngine.data.Data;
import hide92795.NovelEngine.data.DataBasic;
import hide92795.NovelEngine.data.DataMainMenu;
import hide92795.NovelEngine.data.DataStory;
import hide92795.NovelEngine.gui.Button;
import hide92795.NovelEngine.queue.QueueSound;
import hide92795.NovelEngine.queue.QueueTexture;
import hide92795.NovelEngine.story.Story;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.msgpack.MessagePack;
import org.msgpack.type.IntegerValue;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

public class DataLoader {
	public static Data loadData(String path, String name, Class<?> targetClass)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		Data returnData = null;
		File file = NovelEngine.getCurrentDir();
		if (path != null)
			file = new File(file, path);
		file = new File(file, name);
		FileInputStream fis = new FileInputStream(file);
		MessagePack msgpack = new MessagePack();
		Unpacker unpacker = msgpack.createUnpacker(fis);
		if (targetClass.equals(DataBasic.class)) {
			returnData = parseBasic(unpacker);
		} else if (targetClass.equals(DataMainMenu.class)) {
			returnData = parseMainMenu(unpacker);
		} else if (targetClass.equals(DataStory.class)) {
			returnData = parseStory(unpacker);
		}
		fis.close();
		return returnData;
	}

	private static Data parseBasic(Unpacker p) throws IOException {
		DataBasic data = new DataBasic();
		data.setGamename(p.readString());
		int size = p.readInt();
		byte[][] icons = new byte[size][];
		for (int i = 0; i < size; i++) {
			icons[i] = p.readByteArray();
		}
		data.setIcons(icons);
		data.setVersion(p.readString());
		data.setWidth(p.readInt());
		data.setHeight(p.readInt());
		data.setR18(p.readBoolean());
		data.setArrowResize(p.readBoolean());
		data.setShowMaximizeButton(p.readBoolean());
		data.setLogo(p.readByteArray());

		int size1 = p.readInt();
		byte[][] logoSounds = new byte[size1][];
		for (int i = 0; i < size1; i++) {
			logoSounds[i] = p.readByteArray();
		}
		data.setLogoSound(logoSounds);
		data.setMinWidth(p.readInt());
		data.setMinHeight(p.readInt());
		data.setAspect(p.read(int[].class));
		return data;
	}

	private static Data parseMainMenu(Unpacker p) throws IOException {
		DataMainMenu data = new DataMainMenu();
		int size1 = p.readInt();
		int[] images = new int[size1];
		for (int i = 0; i < size1; i++) {
			byte[] b = p.readByteArray();
			String ids = "_MainMenu_Bg_" + i;
			QueueTexture q = new QueueTexture(NovelEngine.theEngine,
					ids.hashCode(), b);
			NovelEngine.theEngine.queue.offer(q);
			images[i] = ids.hashCode();
		}
		data.setBackImageIds(images);
		int idBGM = "_MainMenu_BGM".hashCode();
		QueueSound qs = new QueueSound(NovelEngine.theEngine, idBGM,
				p.readByteArray());
		NovelEngine.theEngine.queue.offer(qs);
		data.setBackMusic(idBGM);
		int size = p.readInt();
		Button[] button = new Button[size];
		for (int i = 0; i < size; i++) {
			int name = p.readInt();
			int[] position = p.read(int[].class);
			byte[] _image = p.read(byte[].class);
			byte[] _imageom = p.read(byte[].class);
			byte[] _clickable = p.read(byte[].class);
			int command = p.readInt();
			int id = p.readInt();
			int commandom = p.readInt();
			int idom = p.readInt();
			String idstr = "_MM_Button_" + name;
			int texid = idstr.hashCode();
			QueueTexture q = new QueueTexture(NovelEngine.theEngine, texid,
					_image);
			NovelEngine.theEngine.queue.offer(q);
			idstr = idstr + "_Om";
			int texidOm = idstr.hashCode();
			QueueTexture q1 = new QueueTexture(NovelEngine.theEngine, texidOm,
					_imageom);
			NovelEngine.theEngine.queue.offer(q1);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					_clickable));

			CommandButton com = new CommandButton(command, id);
			CommandButton comOm = new CommandButton(commandom, idom);

			Button b = new Button(NovelEngine.theEngine, name, position, texid,
					texidOm, image, com, comOm);
			button[i] = b;
		}
		data.setButtons(button);
		data.setButtonRenderingSeq(p.read(int[].class));
		return data;
	}

	private static Data parseStory(Unpacker p) throws IOException {
		DataStory data = new DataStory();
		UnpackerIterator i = p.iterator();
		System.out.println(Sys.getTime());
		while (i.hasNext()) {
			Value v = i.next();
			int in = v.asIntegerValue().getInt();
			switch (in) {
			case Story.COMMAND_SET_SCENEID:
				// シーン
				int sceneid = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_LOAD_CHAPTER:
				// ロード
				int loadsceneid = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_MOVE_CHAPTER:
				// 移動
				int movesceneid = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_CHANGE_BG:
				// 背景変更
				int changebgId = i.next().asIntegerValue().getInt();
				boolean isFade = i.next().asBooleanValue().getBoolean();
				break;
			case Story.COMMAND_CHANGE_CHARACTER:
				// キャラ変更
				int charaId = i.next().asIntegerValue().getInt();
				int faceId = i.next().asIntegerValue().getInt();
				int placeId = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_SHOW_CG:
				// CG表示
				int cgId = i.next().asIntegerValue().getInt();
				boolean isShow = i.next().asBooleanValue().getBoolean();
				break;
			case Story.COMMAND_SHOW_WORDS:
				// セリフ
				int charaId1 = i.next().asIntegerValue().getInt();
				int voiceId = i.next().asIntegerValue().getInt();
				String text = i.next().asRawValue().getString();
				System.out.println(text);
				break;
			case Story.COMMAND_MAKE_BUTTON:
				// ボタン作成
				int size = i.next().asIntegerValue().getInt();
				for (int i1 = 0; i1 < size; i1++) {
					String b = i.next().asRawValue().getString();
					int nextSceneId = i.next().asIntegerValue().getInt();
				}
				break;
			case Story.COMMAND_IF:
				// もし
				int toSceneId = i.next().asIntegerValue().getInt();
				int size1 = i.next().asIntegerValue().getInt();
				for (int i1 = 0; i1 < size1; i1++) {
					int flagId = i.next().asIntegerValue().getInt();
					int con = i.next().asIntegerValue().getInt();
					String b = i.next().asRawValue().getString();
					
				}
				break;
			case Story.COMMAND_PLAY_BGM:
				// 再生
				int sId = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_STOP_BGM:
				// 停止
				break;
			case Story.COMMAND_PLAY_SE:
				// SE再生
				int seId = i.next().asIntegerValue().getInt();
				break;
			case Story.COMMAND_SHOW_BOX:
				// ボックス表示
				break;
			case Story.COMMAND_HIDE_BOX:
				// ボックス閉じる
				break;

			default:
				break;
			}
		}
		System.out.println(Sys.getTime());
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
