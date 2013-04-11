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
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataStory;
import hide92795.novelengine.loader.story.Command;
import hide92795.novelengine.loader.story.CommandAssignment;
import hide92795.novelengine.loader.story.CommandBackGroundEffect;
import hide92795.novelengine.loader.story.CommandButton;
import hide92795.novelengine.loader.story.CommandCalculation;
import hide92795.novelengine.loader.story.CommandChangeBackGround;
import hide92795.novelengine.loader.story.CommandChangeBackGroundColor;
import hide92795.novelengine.loader.story.CommandChangeBackGroundFigure;
import hide92795.novelengine.loader.story.CommandChangeCharacter;
import hide92795.novelengine.loader.story.CommandExit;
import hide92795.novelengine.loader.story.CommandHideBox;
import hide92795.novelengine.loader.story.CommandIF;
import hide92795.novelengine.loader.story.CommandLoadChapter;
import hide92795.novelengine.loader.story.CommandMoveChapter;
import hide92795.novelengine.loader.story.CommandPlayBGM;
import hide92795.novelengine.loader.story.CommandPlaySE;
import hide92795.novelengine.loader.story.CommandRandom;
import hide92795.novelengine.loader.story.CommandShowBox;
import hide92795.novelengine.loader.story.CommandShowWords;
import hide92795.novelengine.loader.story.CommandStopBGM;
import hide92795.novelengine.loader.story.CommandVoice;
import hide92795.novelengine.loader.story.CommandWait;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBlock;
import hide92795.novelengine.story.StoryScene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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
	 * 各コマンドのローダーを保存するマップです。
	 */
	private static HashMap<Byte, Command> commands;

	static {
		commands = new HashMap<Byte, Command>();
		commands.put(Story.COMMAND_LOAD_CHAPTER, new CommandLoadChapter());
		commands.put(Story.COMMAND_MOVE_CHAPTER, new CommandMoveChapter());
		commands.put(Story.COMMAND_SET_BACKGROUND, new CommandChangeBackGround());
		commands.put(Story.COMMAND_SET_CHARACTER, new CommandChangeCharacter());
		commands.put(Story.COMMAND_VOICE, new CommandVoice());
		// commands.put(Story.COMMAND_ACTION_CHARACTER, new Command());
		commands.put(Story.COMMAND_SET_BACKGROUND_FIGURE, new CommandChangeBackGroundFigure());
		commands.put(Story.COMMAND_SHOW_WORDS, new CommandShowWords());
		commands.put(Story.COMMAND_MAKE_BUTTON, new CommandButton());
		commands.put(Story.COMMAND_IF, new CommandIF());
		commands.put(Story.COMMAND_PLAY_BGM, new CommandPlayBGM());
		commands.put(Story.COMMAND_STOP_BGM, new CommandStopBGM());
		commands.put(Story.COMMAND_PLAY_SE, new CommandPlaySE());
		commands.put(Story.COMMAND_SHOW_BOX, new CommandShowBox());
		commands.put(Story.COMMAND_HIDE_BOX, new CommandHideBox());
		commands.put(Story.COMMAND_SET_VARIABLE, new CommandAssignment());
		commands.put(Story.COMMAND_SET_BACKGROUND_COLOR, new CommandChangeBackGroundColor());
		commands.put(Story.COMMAND_EFFECT_BACKGROUND, new CommandBackGroundEffect());
		commands.put(Story.COMMAND_RANDOM, new CommandRandom());
		commands.put(Story.COMMAND_CALCULATION, new CommandCalculation());
		commands.put(Story.COMMAND_EXIT, new CommandExit());
		commands.put(Story.COMMAND_WAIT, new CommandWait());
	}

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
		ConcurrentHashMap<String, Integer> initVariable = new ConcurrentHashMap<String, Integer>();
		CipherInputStream cis = null;
		try {
			cis = Loader.createCipherInputStream(file);

			MessagePack msgpack = new MessagePack();
			Unpacker unpacker = msgpack.createUnpacker(cis);

			unpacker.getNextType();
			UnpackerIterator iterator = unpacker.iterator();

			while (iterator.hasNext()) {
				Value v = iterator.next();
				byte command_b = v.asIntegerValue().getByte();
				switch (command_b) {
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
					int sceneId = iterator.next().asIntegerValue().getInt();
					StoryScene story = new StoryScene(sceneId);
					initVariable = new ConcurrentHashMap<String, Integer>();
					data.addStory(story);
					break;
				}
				default: {
					Command command = commands.get(command_b);
					Story story = command.load(engine, iterator, resourceLoader, initVariable);
					data.addStory(story);
				}
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
