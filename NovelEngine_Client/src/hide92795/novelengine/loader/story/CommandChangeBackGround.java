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
import hide92795.novelengine.story.StoryChangeBackGround;

import java.util.Map;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * バックグラウンドの画像を変更ストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandChangeBackGround implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			Map<String, Integer> initVariable, int line) throws Exception {
		// byte 対象, int 遅延（ms）, int 画像ID, int 左上X座標, int 左上Y座標, int 拡大率
		byte target = iterator.next().asIntegerValue().getByte();
		int delay = iterator.next().asIntegerValue().getInt();
		int bgId = iterator.next().asIntegerValue().getInt();
		int xPos = iterator.next().asIntegerValue().getInt();
		int yPos = iterator.next().asIntegerValue().getInt();
		int magnification = iterator.next().asIntegerValue().getInt();
		resourceLoader.loadImage(bgId);
		StoryChangeBackGround story = new StoryChangeBackGround(line, bgId, target, xPos, yPos, magnification, delay);
		return story;
	}
}
