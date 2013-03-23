//
// NovelEngine Project
//
// Copyright (C) 2013 - hide92795
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package hide92795.novelengine.loader;

import hide92795.novelengine.NovelEngineException;
import hide92795.novelengine.background.EffectBackGround;
import hide92795.novelengine.character.DataCharacter;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.entity.EntityButton;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.manager.EffectManager.ClassData;
import hide92795.novelengine.manager.FontManager.DataFont;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryAssignment;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.story.StoryButton;
import hide92795.novelengine.story.StoryChangeBg;
import hide92795.novelengine.story.StoryChangeBgColor;
import hide92795.novelengine.story.StoryChangeCharacter;
import hide92795.novelengine.story.StoryEffect;
import hide92795.novelengine.story.StoryExit;
import hide92795.novelengine.story.StoryIF;
import hide92795.novelengine.story.StoryLoadChapter;
import hide92795.novelengine.story.StoryMoveChapter;
import hide92795.novelengine.story.StoryPlayBGM;
import hide92795.novelengine.story.StoryPlaySE;
import hide92795.novelengine.story.StoryRandom;
import hide92795.novelengine.story.StoryScene;
import hide92795.novelengine.story.StoryShowBox;
import hide92795.novelengine.story.StoryShowWords;
import hide92795.novelengine.story.StoryStopBGM;
import hide92795.novelengine.story.StoryWait;
import hide92795.novelengine.words.EntityWords;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
	public static DataStory load(NovelEngine engine, File file, int id) {
		LoaderResource resourceLoader = new LoaderResource(engine, id);
		DataStory data = new DataStory(id);
		HashMap<String, Integer> initVariable = new HashMap<String, Integer>();
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(file);

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
					data.addStory(StoryBlock.BLOCKSTART);
					break;
				}
				case Story.COMMAND_BLOCK_END: {
					// ブロックエンド
					data.addStory(StoryBlock.BLOCKEND);
					break;
				}
				case Story.COMMAND_SET_SCENEID: {
					// シーン
					int sceneId = i.next().asIntegerValue().getInt();
					StoryScene story = new StoryScene(sceneId);
					initVariable = new HashMap<String, Integer>();
					// 処理
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
				case Story.COMMAND_CHANGE_CHARACTER: {
					// キャラ変更
					int characterId = i.next().asIntegerValue().getInt();
					byte target = i.next().asIntegerValue().getByte();
					int delay = i.next().asIntegerValue().getInt();
					int positionId = i.next().asIntegerValue().getInt();
					int faceId = i.next().asIntegerValue().getInt();
					int[] faceImages = engine.getCharacterManager().getCharacter(characterId).getFace(faceId)
							.getFaceDiff();
					resourceLoader.loadImages(faceImages);
					StoryChangeCharacter story = new StoryChangeCharacter(characterId, target, delay, positionId,
							faceId);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_SHOW_CG:
					// CG表示
					break;
				case Story.COMMAND_SHOW_WORDS: {
					// セリフ
					int characterId = unpacker.readInt();
					String words_str = unpacker.readString();
					DataCharacter character = engine.getCharacterManager().getCharacter(characterId);
					if (!character.isCreatedNameImage()) {
						String name = character.getName();
						int fontId = character.getFont();
						int imageId = engine.getWordsManager().getNextImageId();
						DataFont font = engine.getFontManager().getFont(fontId);
						resourceLoader.loadWords(name, imageId, font);
						character.setNameImageId(imageId);
					}
					EntityWords words = new EntityWords(characterId);
					resourceLoader.loadWords(words, words_str, initVariable);
					StoryShowWords story = new StoryShowWords(characterId, words);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_MAKE_BUTTON: {
					// ボタン作成
					int num = i.next().asIntegerValue().getInt();
					int positionId = i.next().asIntegerValue().getInt();
					EntityButton[] buttons = new EntityButton[num];
					for (int j = 0; j < num; j++) {
						int buttonId = i.next().asIntegerValue().getInt();
						int jumpSceneId = i.next().asIntegerValue().getInt();
						EntityButton button = new EntityButton(buttonId, jumpSceneId);
						buttons[j] = button;
					}
					StoryButton story = new StoryButton(positionId, buttons);
					data.addStory(story);
					break;
				}
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
					// BGM再生
					int bgmid = i.next().asIntegerValue().getInt();
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
					StoryPlaySE s = new StoryPlaySE(seId);
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
				case Story.COMMAND_EXIT: {
					// 終了
					// boolean confirm
					boolean confirm = i.next().asBooleanValue().getBoolean();
					StoryExit story = new StoryExit(confirm);
					data.addStory(story);
					break;
				}
				case Story.COMMAND_WAIT: {
					// 待機
					// int waitTimeMs
					int waitTimeMs = i.next().asIntegerValue().getInt();
					StoryWait story = new StoryWait(waitTimeMs);
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
			resourceLoader.start();
			try {
				cis.close();
			} catch (IOException e) {
				throw new NovelEngineException(e, String.valueOf(id));
			}
		}
		return data;
	}
}
