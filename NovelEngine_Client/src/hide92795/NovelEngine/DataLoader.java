package hide92795.NovelEngine;

import hide92795.NovelEngine.Client.NovelEngine;
import hide92795.NovelEngine.Command.CommandButton;
import hide92795.NovelEngine.EventQueue.QueueSound;
import hide92795.NovelEngine.EventQueue.QueueTexture;
import hide92795.NovelEngine.Gui.Button;
import hide92795.NovelEngine.SettingData.Data;
import hide92795.NovelEngine.SettingData.DataBasic;
import hide92795.NovelEngine.SettingData.DataMainMenu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

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

			Button b = new Button(name, position, texid, texidOm, image, com,
					comOm);
			button[i] = b;
		}
		data.setButtons(button);
		data.setButtonRenderingSeq(p.read(int[].class));
		return data;
	}
}
