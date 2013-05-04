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
import hide92795.novelengine.story.StoryChangeBackGroundColor;

import java.util.Map;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * バックグラウンドの背景色を変更ストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandChangeBackGroundColor implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			Map<String, Integer> initVariable, int line) throws Exception {
		// byte 対象, int 色, int アルファ値
		byte target = iterator.next().asIntegerValue().getByte();
		int rgb = iterator.next().asIntegerValue().getInt();
		byte alpha = iterator.next().asIntegerValue().getByte();
		byte red = (byte) ((rgb & 0x00FF0000) >> 16);
		byte green = (byte) ((rgb & 0x0000FF00) >> 8);
		byte blue = (byte) (rgb & 0x000000FF);
		StoryChangeBackGroundColor story = new StoryChangeBackGroundColor(line, target, red, green, blue, alpha);
		return story;
	}
}
