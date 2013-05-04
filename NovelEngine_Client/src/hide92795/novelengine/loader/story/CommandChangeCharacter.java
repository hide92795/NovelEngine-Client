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
package hide92795.novelengine.loader.story;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryChangeCharacter;

import java.util.Map;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * 指定された背景にキャラクターを配置するストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandChangeCharacter implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			Map<String, Integer> initVariable, int line) throws Exception {
		// int キャラID, int 遅延（ms）, byte 対象, int 位置ID, int 表情ID
		byte target = iterator.next().asIntegerValue().getByte();
		int delay = iterator.next().asIntegerValue().getInt();
		int characterId = iterator.next().asIntegerValue().getInt();
		int positionId = iterator.next().asIntegerValue().getInt();
		int faceId = iterator.next().asIntegerValue().getInt();
		int[] faceImages = engine.getCharacterManager().getCharacter(characterId).getFace(faceId).getFaceDiff();
		resourceLoader.loadImages(faceImages);
		StoryChangeCharacter story = new StoryChangeCharacter(line, characterId, target, delay, positionId, faceId);
		return story;
	}
}
