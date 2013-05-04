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
import hide92795.novelengine.story.StoryVoice;

import java.util.Map;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * ボイスの再生を行うストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandVoice implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			Map<String, Integer> initVariable, int line) throws Exception {
		// int キャラクターID, int ボイスID
		int characterId = iterator.next().asIntegerValue().getInt();
		int voiceId = iterator.next().asIntegerValue().getInt();
		StoryVoice story = new StoryVoice(line, characterId, voiceId);
		return story;
	}
}
