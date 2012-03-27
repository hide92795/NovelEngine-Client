package hide92795.novelengine;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.command.CommandButton;
import hide92795.novelengine.data.Data;
import hide92795.novelengine.data.DataBasic;
import hide92795.novelengine.data.DataCharacter;
import hide92795.novelengine.data.DataGui;
import hide92795.novelengine.data.DataMainMenu;
import hide92795.novelengine.data.DataStory;
import hide92795.novelengine.fader.Fader;
import hide92795.novelengine.fader.FaderIn;
import hide92795.novelengine.fader.FaderInBlock;
import hide92795.novelengine.fader.FaderInAlpha;
import hide92795.novelengine.fader.FaderInSlide;
import hide92795.novelengine.fader.FaderOut;
import hide92795.novelengine.fader.FaderOutBlock;
import hide92795.novelengine.fader.FaderOutAlpha;
import hide92795.novelengine.fader.FaderOutSlide;
import hide92795.novelengine.fader.FaderPair;
import hide92795.novelengine.fader.FaderPairAlpha;
import hide92795.novelengine.gui.Button;
import hide92795.novelengine.queue.QueueSound;
import hide92795.novelengine.queue.QueueTexture;
import hide92795.novelengine.queue.QueueWords;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryChangeBg;
import hide92795.novelengine.story.StoryChangeCharacter;
import hide92795.novelengine.story.StoryLoadChapter;
import hide92795.novelengine.story.StoryMoveChapter;
import hide92795.novelengine.story.StoryScene;
import hide92795.novelengine.story.StoryShowBox;
import hide92795.novelengine.story.StoryShowCG;
import hide92795.novelengine.story.StoryWords;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

public class DataLoader {
	public static Data loadData(NovelEngine engine, String path, String name,
			Class<?> targetClass) throws FileNotFoundException, IOException {

		Data returnData = null;
		File file = NovelEngine.getCurrentDir();
		if (path != null)
			file = new File(file, path);
		file = new File(file, name);
		FileInputStream fis = new FileInputStream(file);
		MessagePack msgpack = new MessagePack();
		Unpacker unpacker = msgpack.createUnpacker(fis);
		if (targetClass.equals(DataBasic.class)) {
			returnData = parseBasic(engine, unpacker);
		} else if (targetClass.equals(DataMainMenu.class)) {
			returnData = parseMainMenu(engine, unpacker);
		} else if (targetClass.equals(DataStory.class)) {
			returnData = parseStory(engine, unpacker);
		} else if (targetClass.equals(DataGui.class)) {
			returnData = parseGui(engine, unpacker);
		} else if (targetClass.equals(DataCharacter.class)) {
			returnData = parseCharacter(engine, unpacker);
		}
		fis.close();
		return returnData;
	}

	private static Data parseBasic(NovelEngine engine, Unpacker p)
			throws IOException {
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

	private static Data parseMainMenu(NovelEngine engine, Unpacker p)
			throws IOException {
		System.out.println(Sys.getTime());
		DataMainMenu data = new DataMainMenu();
		int size1 = p.readInt();
		int[] images = new int[size1];
		for (int i = 0; i < size1; i++) {
			byte[] b = p.readByteArray();
			String ids = "_MainMenu_Bg_" + i;
			QueueTexture q = new QueueTexture(engine, ids.hashCode(), b);
			engine.queue.offer(q);
			images[i] = ids.hashCode();
		}
		data.setBackImageIds(images);
		int idBGM = "_MainMenu_BGM".hashCode();
		QueueSound qs = new QueueSound(engine, idBGM, p.readByteArray());
		engine.queue.offer(qs);
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
			QueueTexture q = new QueueTexture(engine, texid, _image);
			engine.queue.offer(q);
			idstr = idstr + "_Om";
			int texidOm = idstr.hashCode();
			QueueTexture q1 = new QueueTexture(engine, texidOm, _imageom);
			engine.queue.offer(q1);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
					_clickable));

			CommandButton com = new CommandButton(command, id);
			CommandButton comOm = new CommandButton(commandom, idom);

			Button b = new Button(engine, name, position, texid, texidOm,
					image, com, comOm);
			button[i] = b;
		}
		data.setButtons(button);
		data.setButtonRenderingSeq(p.read(int[].class));
		System.out.println(Sys.getTime());
		return data;
	}

	private static Data parseStory(NovelEngine engine, Unpacker p)
			throws IOException {
		DataStory data = new DataStory();
		UnpackerIterator i = p.iterator();
		System.out.println(Sys.getTime());
		int wordsCounter = 0;
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
				boolean skippable = i.next().asBooleanValue().getBoolean();
				int fadetype = i.next().asIntegerValue().getInt();
				FaderPair pair = null;
				switch (fadetype) {
				case Fader.FADER_NONE:
					break;
				case Fader.FADER_ALPHA:
					float af_time = i.next().asFloatValue().getFloat();
					boolean af_via = i.next().asBooleanValue().getBoolean();
					String af_color = i.next().asRawValue().getString();
					FaderOutAlpha fadeoutalpha = new FaderOutAlpha(engine,
							null, af_time, af_color);
					FaderInAlpha fadeinalpha = new FaderInAlpha(engine, null,
							af_time, af_color);
					pair = new FaderPairAlpha(fadeoutalpha, fadeinalpha,
							skippable, af_via);
					break;
				case Fader.FADER_BLOCK:
					int bf_x = i.next().asIntegerValue().getInt();
					int bf_y = i.next().asIntegerValue().getInt();
					String bf_color = i.next().asRawValue().getString();
					FaderOutBlock fadeoutblock = new FaderOutBlock(engine,
							null, bf_x, bf_y, bf_color);
					FaderInBlock fadeinblock = new FaderInBlock(engine, null,
							bf_x, bf_y, bf_color);
					pair = new FaderPair(fadeoutblock, fadeinblock, skippable);
					break;
				case Fader.FADER_SLIDE:
					int sf_x = i.next().asIntegerValue().getInt();
					int sf_y = i.next().asIntegerValue().getInt();
					String sf_color = i.next().asRawValue().getString();
					FaderOutSlide fadeoutslide = new FaderOutSlide(engine,
							null, sf_x, sf_y, sf_color);
					FaderInSlide fadeinslide = new FaderInSlide(engine, null,
							sf_x, sf_y, sf_color);
					pair = new FaderPair(fadeoutslide, fadeinslide, skippable);
					break;
				}
				StoryChangeBg bg = new StoryChangeBg(changebgId, pair);
				data.addStory(bg);
				break;
			case Story.COMMAND_CHANGE_CHARACTER:
				// キャラ変更
				int cc_count = i.next().asIntegerValue().getInt();
				StoryChangeCharacter c = new StoryChangeCharacter(cc_count);
				for (int j = 0; j < cc_count; j++) {
					int characterId = i.next().asIntegerValue().getInt();
					int faceId = i.next().asIntegerValue().getInt();
					int placeId = i.next().asIntegerValue().getInt();
					boolean fade = i.next().asBooleanValue().getBoolean();
					c.addChange(j, characterId, faceId, placeId, fade);
				}
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
				QueueWords qw = new QueueWords(engine, data.getChapterId(),
						wordsCounter, text);
				engine.wordsManager.offer(qw);
				StoryWords w = new StoryWords(charId1, voiceId, text,
						wordsCounter);
				data.addStory(w);
				wordsCounter++;
				break;
			case Story.COMMAND_MAKE_BUTTON:
				// ボタン作成
				int size = i.next().asIntegerValue().getInt();
				for (int i1 = 0; i1 < size; i1++) {
					String b = i.next().asRawValue().getString();
					int nextSceneId = i.next().asIntegerValue().getInt();
					String name = "_Story_Button_Chapter_"
							+ data.getChapterId() + "_" + i1;
					Button button = new Button(engine, name.hashCode(), null,
							Button.BUTTON, Button.BUTTON_OM, Button.cl,
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
				StoryShowBox box = new StoryShowBox(true);
				data.addStory(box);
				break;
			case Story.COMMAND_HIDE_BOX:
				// ボックス閉じる
				StoryShowBox box1 = new StoryShowBox(false);
				data.addStory(box1);
				break;
			default:
				break;
			}
		}
		System.out.println(Sys.getTime());
		return data;
	}

	private static Data parseGui(NovelEngine engine, Unpacker p)
			throws IOException {
		DataGui data = new DataGui();
		boolean end = false;
		while (!end) {
			try {
				int type = p.readInt();
				switch (type) {
				case DataGui.GUI_MESSAGEBOX:
					byte[] boximg = p.readByteArray();
					QueueTexture qt = new QueueTexture(engine,
							data.getBoxImageId(), boximg);
					engine.queue.offer(qt);
					int imgXpos = p.readInt();
					int imgYpos = p.readInt();
					int nameXpos = p.readInt();
					int nameYpos = p.readInt();
					int messageXpos = p.readInt();
					int messageYpos = p.readInt();
					int messageX1pos = p.readInt();
					int messageY1pos = p.readInt();
					data.setBoxXpos(imgXpos);
					data.setBoxYpos(imgYpos);
					data.setBoxNameXpos(nameXpos);
					data.setBoxNameYpos(nameYpos);
					data.setBoxWordsXpos(messageXpos);
					data.setBoxWordsYpos(messageYpos);
					data.setBoxWordsX1pos(messageX1pos);
					data.setBoxWordsY1pos(messageY1pos);
					break;
				case DataGui.GUI_WAITCURSOR:
					byte[] cursorimg = p.readByteArray();
					QueueTexture qt1 = new QueueTexture(engine,
							data.getWaitCursorImageId(), cursorimg);
					engine.queue.offer(qt1);
					int size = p.readInt();
					data.setWaitCursorSize(size);
					int count = p.readInt();
					data.setWaitCursorCount(count);
					float[][] list = new float[count][];
					for (int j = 0; j < count; j++) {
						float cursorXpos = p.readFloat();
						float cursorYpos = p.readFloat();
						float cursorX1pos = p.readFloat();
						float cursorY1pos = p.readFloat();
						list[j] = new float[] { cursorXpos, cursorYpos,
								cursorX1pos, cursorY1pos };
					}
					data.setWaitCursorList(list);
					break;
				case DataGui.GUI_PORTRAIT:
					int count1 = p.readInt();
					for (int i = 0; i < count1; i++) {
						int id = p.readInt();
						int x = p.readInt();
						int y = p.readInt();
						int[] pos = { x, y };
						data.addPortraitPosition(id, pos);
					}
					break;
				default:
					break;
				}
			} catch (EOFException e) {
				end = true;
			}
		}

		File b = new File("./button.png");
		File bo = new File("./buttonom.png");
		File box = new File("./box.png");

		FileInputStream fis = new FileInputStream(b);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] byt = new byte[1];
		while (fis.read(byt) > 0) {
			bos.write(byt);
		}
		fis.close();
		engine.queue.offer(new QueueTexture(engine, Button.BUTTON, bos
				.toByteArray()));

		FileInputStream fiso = new FileInputStream(bo);
		ByteArrayOutputStream boso = new ByteArrayOutputStream();
		while (fiso.read(byt) > 0) {
			boso.write(byt);
		}
		fiso.close();
		engine.queue.offer(new QueueTexture(engine, Button.BUTTON_OM, boso
				.toByteArray()));

		FileInputStream fisb = new FileInputStream(box);
		ByteArrayOutputStream bosb = new ByteArrayOutputStream();
		while (fisb.read(byt) > 0) {
			bosb.write(byt);
		}
		fisb.close();

		engine.queue
				.offer(new QueueTexture(engine, 123456, bosb.toByteArray()));

		Button.cl = ImageIO.read(new File("./click.png"));
		return data;
	}

	private static Data parseCharacter(NovelEngine engine, Unpacker p)
			throws IOException {
		System.out.println("DataLoader.parseCharacter()");
		DataCharacter data = new DataCharacter();
		boolean end = false;
		while (!end) {
			try {
				int charaImgId = p.readInt();
				System.out.println(charaImgId);
				byte[] charaImg = p.readByteArray();
				QueueTexture q = new QueueTexture(engine, charaImgId, charaImg);
				engine.queue.offer(q);
			} catch (EOFException e) {
				end = true;
			}
		}
		File dir = NovelEngine.getCurrentDir();
		dir = new File(dir, "ch");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (!file.isDirectory() && file.getName().endsWith(".dat")) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					MessagePack msgpack = new MessagePack();
					Unpacker unpacker = msgpack.createUnpacker(fis);
					int charaId = unpacker.readInt();
					String name = unpacker.readString();
					Character c = new Character(charaId, name);
					end = false;
					while (!end) {
						System.out.println("DataLoader.parseCharacter()");
						try {
							int faceid = unpacker.readInt();
							int imageid = unpacker.readInt();
							c.addFace(faceid, imageid);
							System.out.println("DataLoader.parseCharacter()");
						} catch (EOFException e) {
							end = true;
						}
					}
					data.addCharacter(c);

				} finally {
					fis.close();
				}
			}
		}
		return data;
	}
}
