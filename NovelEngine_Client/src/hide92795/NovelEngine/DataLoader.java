package hide92795.novelengine;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.command.CommandButton;
import hide92795.novelengine.data.Data;
import hide92795.novelengine.data.DataBasic;
import hide92795.novelengine.data.DataGui;
import hide92795.novelengine.data.DataMainMenu;
import hide92795.novelengine.data.DataStory;
import hide92795.novelengine.fader.FaderIn;
import hide92795.novelengine.fader.FaderInBlock;
import hide92795.novelengine.fader.FaderInDisappear;
import hide92795.novelengine.fader.FaderOutBlock;
import hide92795.novelengine.fader.FaderOutDisappear;
import hide92795.novelengine.gui.Button;
import hide92795.novelengine.queue.QueueSound;
import hide92795.novelengine.queue.QueueTexture;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryChangeBg;
import hide92795.novelengine.story.StoryChangeCharacter;
import hide92795.novelengine.story.StoryLoadChapter;
import hide92795.novelengine.story.StoryMoveChapter;
import hide92795.novelengine.story.StoryScene;
import hide92795.novelengine.story.StoryShowCG;
import hide92795.novelengine.story.StoryWords;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

public class DataLoader {
	public static Data loadData(String path, String name, Class<?> targetClass)
			throws FileNotFoundException, IOException {

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
		} else if (targetClass.equals(DataGui.class)) {
			returnData = parseGui(unpacker);
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
			int com = i.next().asIntegerValue().getInt();
			switch (com) {
			case Story.COMMAND_SET_CHAPTERID:
				// チャプター
				int chapterId = i.next().asIntegerValue().getInt();
				data.setChapterId(chapterId);
				break;
			case Story.COMMAND_SET_SCENEID:
				// シーン
				int sceneId = i.next().asIntegerValue().getInt();
				StoryScene scene = new StoryScene(sceneId);
				data.addStory(scene);
				break;
			case Story.COMMAND_LOAD_CHAPTER:
				// ロード
				int loadChapterId = i.next().asIntegerValue().getInt();
				StoryLoadChapter loadChapter = new StoryLoadChapter(
						loadChapterId);
				data.addStory(loadChapter);
				break;
			case Story.COMMAND_MOVE_CHAPTER:
				// 移動
				int movechapterid = i.next().asIntegerValue().getInt();
				StoryMoveChapter moveChapter = new StoryMoveChapter(
						movechapterid);
				data.addStory(moveChapter);
				break;
			case Story.COMMAND_CHANGE_BG:
				// 背景変更
				int changebgId = i.next().asIntegerValue().getInt();
				boolean isFade = i.next().asBooleanValue().getBoolean();
				StoryChangeBg bg = new StoryChangeBg(changebgId,
						new FaderOutDisappear(
								NovelEngine.theEngine, null, 2.5f, "#000000"), new FaderInDisappear(
								NovelEngine.theEngine, null, 2.5f, "#ffffff"));
				data.addStory(bg);
				break;
			case Story.COMMAND_CHANGE_CHARACTER:
				// キャラ変更
				int charId = i.next().asIntegerValue().getInt();
				int faceId = i.next().asIntegerValue().getInt();
				int placeId = i.next().asIntegerValue().getInt();
				StoryChangeCharacter c = new StoryChangeCharacter(charId,
						faceId, placeId);
				data.addStory(c);
				break;
			case Story.COMMAND_SHOW_CG:
				// CG表示
				int cgId = i.next().asIntegerValue().getInt();
				boolean isShow = i.next().asBooleanValue().getBoolean();
				StoryShowCG cg = new StoryShowCG(cgId, isShow);
				data.addStory(cg);
				break;
			case Story.COMMAND_SHOW_WORDS:
				// セリフ
				int charId1 = i.next().asIntegerValue().getInt();
				int voiceId = i.next().asIntegerValue().getInt();
				String text = i.next().asRawValue().getString();
				AttributedString as = Utils.parceWords(text);
				StoryWords w = new StoryWords(charId1, voiceId, as);
				data.addStory(w);
				break;
			case Story.COMMAND_MAKE_BUTTON:
				// ボタン作成
				int size = i.next().asIntegerValue().getInt();
				for (int i1 = 0; i1 < size; i1++) {
					String b = i.next().asRawValue().getString();
					int nextSceneId = i.next().asIntegerValue().getInt();
					String name = "_Story_Button_Chapter_"
							+ data.getChapterId() + "_" + i1;
					Button button = new Button(NovelEngine.theEngine,
							name.hashCode(), null, Button.BUTTON,
							Button.BUTTON_OM, Button.cl,
							new CommandButton(4, 0), new CommandButton(4));

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
		return data;
	}

	public static Data parseGui(Unpacker unpacker) throws IOException {
		File b = new File("./button.png");
		File bo = new File("./buttonom.png");
		FileInputStream fis = new FileInputStream(b);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] byt = new byte[1];
		while (fis.read(byt) > 0) {
			bos.write(byt);
		}
		fis.close();
		NovelEngine.theEngine.queue.offer(new QueueTexture(
				NovelEngine.theEngine, Button.BUTTON, bos.toByteArray()));

		FileInputStream fiso = new FileInputStream(bo);
		ByteArrayOutputStream boso = new ByteArrayOutputStream();
		while (fiso.read(byt) > 0) {
			boso.write(byt);
		}
		fiso.close();
		NovelEngine.theEngine.queue.offer(new QueueTexture(
				NovelEngine.theEngine, Button.BUTTON_OM, boso.toByteArray()));

		Button.cl = ImageIO.read(new File("./click.png"));
		return null;
	}
}
