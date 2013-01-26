package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.background.EffectBackGround;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.EffectManager.ClassData;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryAssignment;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.story.StoryChangeBg;
import hide92795.novelengine.story.StoryChangeBgColor;
import hide92795.novelengine.story.StoryEffect;
import hide92795.novelengine.story.StoryIF;
import hide92795.novelengine.story.StoryLoadChapter;
import hide92795.novelengine.story.StoryMoveChapter;
import hide92795.novelengine.story.StoryPlayBGM;
import hide92795.novelengine.story.StoryPlaySE;
import hide92795.novelengine.story.StoryRandom;
import hide92795.novelengine.story.StoryScene;
import hide92795.novelengine.story.StoryShowBox;
import hide92795.novelengine.story.StoryStopBGM;

import java.awt.Color;
import java.io.File;

import javax.crypto.CipherInputStream;

import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;

/**
 * ストーリーデータを外部ファイルから読み込むクラスです。
 *
 * @author hide92795
 */
public class LoaderStory extends Loader {
	/**
	 * ストーリーデータを外部ファイルから読み込み、必要なリソースをローダーに登録します。
	 *
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param file
	 *            ストーリーデータが格納されているファイル
	 * @param id
	 *            読み込むストーリーのチャプターID
	 * @return 読み込まれたストーリーデータ
	 */
	public static DataStory load(final NovelEngine engine, final File file, final int id) {
		LoaderResource resourceLoader = new LoaderResource(engine, id);
		DataStory data = new DataStory(id);
		try {
			CipherInputStream cis = Loader.createCipherInputStream(file);

			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			UnpackerIterator i = unpacker.iterator();
			// int wordsCounter = 0;
			while (i.hasNext()) {
				Value v = i.next();
				byte command = v.asIntegerValue().getByte();
				switch (command) {
				case Story.COMMAND_BLOCK_START: {
					// ブロックスタート
					StoryBlock story = new StoryBlock(true);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_BLOCK_END: {
					// ブロックエンド
					StoryBlock story = new StoryBlock(false);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_SET_SCENEID: {
					// シーン
					int sceneId = i.next().asIntegerValue().getInt();
					StoryScene story = new StoryScene(sceneId);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_LOAD_CHAPTER: {
					// ロード
					int chapterId = i.next().asIntegerValue().getInt();
					StoryLoadChapter story = new StoryLoadChapter(chapterId);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_MOVE_CHAPTER: {
					// 移動
					int chapterId = i.next().asIntegerValue().getInt();
					StoryMoveChapter story = new StoryMoveChapter(chapterId);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_CHANGE_BG: {
					// 背景変更
					// int 画像ID, byte 対象, int 左上X座標, int 左上Y座標, int 拡大率, int 遅延（ms）
					int bgId = i.next().asIntegerValue().getInt();
					byte target = i.next().asIntegerValue().getByte();
					int xPos = i.next().asIntegerValue().getInt();
					int yPos = i.next().asIntegerValue().getInt();
					int magnification = i.next().asIntegerValue().getInt();
					int delay = i.next().asIntegerValue().getInt();
					resourceLoader.loadImage(bgId);
					StoryChangeBg story = new StoryChangeBg(bgId, target, xPos, yPos, magnification, delay);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_CHANGE_CHARACTER:
					// キャラ変更
					break;
				case Story.COMMAND_SHOW_CG:
					// CG表示
					break;
				case Story.COMMAND_SHOW_WORDS:
					// セリフ
					break;
				case Story.COMMAND_MAKE_BUTTON:
					// ボタン作成
					break;
				case Story.COMMAND_IF: {
					// もし
					// byte 演算子, byte 左変数タイプ ,int 左数字, byte 右変数タイプ ,int 右数字, int 真, int 偽
					byte operator = i.next().asIntegerValue().getByte();
					byte leftVarType = i.next().asIntegerValue().getByte();
					String leftVarName = i.next().asRawValue().getString();
					byte rightVarType = i.next().asIntegerValue().getByte();
					String rightVarName = i.next().asRawValue().getString();
					int trueGoto = i.next().asIntegerValue().getInt();
					int falseGoto = i.next().asIntegerValue().getInt();
					StoryIF story = new StoryIF(operator, leftVarType, leftVarName, rightVarType, rightVarName,
							trueGoto, falseGoto);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_PLAY_BGM: {
					// 再生
					int bgmid = i.next().asIntegerValue().getInt();
					resourceLoader.loadSound(bgmid);
					StoryPlayBGM story = new StoryPlayBGM(bgmid);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_STOP_BGM: {
					// 停止
					StoryStopBGM story = new StoryStopBGM();
					data.addStory(story);
					break;
				}
				case Story.COMMAND_PLAY_SE: {
					// SE再生
					int seId = i.next().asIntegerValue().getInt();
					boolean wait = i.next().asBooleanValue().getBoolean();
					resourceLoader.loadSound(seId);
					StoryPlaySE s = new StoryPlaySE(seId, wait);
					data.addStory(s);
					break;
				}
				case Story.COMMAND_SHOW_BOX: {
					// ボックス表示
					StoryShowBox story = new StoryShowBox(true);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_HIDE_BOX: {
					// ボックス閉じる
					StoryShowBox story = new StoryShowBox(false);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_SET_BACKGROUND_COLOR: {
					// 背景色
					// byte 対象, int 色, int アルファ値
					byte target = i.next().asIntegerValue().getByte();
					int color_i = i.next().asIntegerValue().getInt();
					int alpha = i.next().asIntegerValue().getInt();
					Color color = new Color(color_i);
					StoryChangeBgColor story = new StoryChangeBgColor(target, color.getRed(), color.getGreen(),
							color.getBlue(), alpha);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_EFFECT: {
					// エフェクト
					// 数値 対象, 数値 遅延（ms）, エフェクター エフェクト
					byte target = i.next().asIntegerValue().getByte();
					int delay = i.next().asIntegerValue().getInt();
					int effectId = i.next().asIntegerValue().getInt();
					ClassData cd = engine.getEffectManager().getBackgroundEffect(effectId);
					Object[] argClass = cd.getArgumentList();
					int argCount = argClass.length;
					Object[] c_obj = new Object[argCount];
					for (int j = 0; j < argCount; j++) {
						Value val = i.next();
						if (argClass[j].equals(int.class)) {
							// int
							c_obj[j] = val.asIntegerValue().asIntegerValue().getInt();
						}
					}
					EffectBackGround effectBackGround = (EffectBackGround) cd.instantiation(c_obj);
					StoryEffect s_effect = new StoryEffect(target, delay, effectBackGround);
					data.addStory(s_effect);
					break;
				}
				case Story.COMMAND_SET_VARIABLE: {
					// 設定
					// byte type, int name, int value
					byte type = i.next().asIntegerValue().getByte();
					String name = i.next().asRawValue().getString();
					int value = i.next().asIntegerValue().getInt();
					StoryAssignment story = new StoryAssignment(type, name, value);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_RANDOM: {
					// 乱数
					// byte type, int name, int num
					byte type = i.next().asIntegerValue().getByte();
					String name = i.next().asRawValue().getString();
					int num = i.next().asIntegerValue().getInt();
					StoryRandom story = new StoryRandom(type, name, num);
					data.addStory(story);
					break;
				}
				default:
					break;
				}
			}
		} catch (Exception e) {
			throw new NovelEngineException(e, String.valueOf(id));
		} finally {
			// ロード完了の通知
			resourceLoader.loadImage(0);
			resourceLoader.loadWords(0, null);
			resourceLoader.loadSound(0);
			resourceLoader.loadVoice(0);
		}
		return data;
	}
}
