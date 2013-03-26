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
import hide92795.novelengine.story.StoryMoveChapter;

import java.util.concurrent.ConcurrentHashMap;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * チャプターを移動するためのストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandMoveChapter implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			ConcurrentHashMap<String, Integer> initVariable) throws Exception {
		int chapterId = iterator.next().asIntegerValue().getInt();
		StoryMoveChapter story = new StoryMoveChapter(chapterId);
		return story;
	}
}
