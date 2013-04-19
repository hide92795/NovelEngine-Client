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
import hide92795.novelengine.story.StoryCalculation;

import java.util.concurrent.ConcurrentHashMap;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * 計算を行うストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandCalculation implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			ConcurrentHashMap<String, Integer> initVariable, int line) throws Exception {
		// byte 代入先変数タイプ, String 代入先変数名, byte 演算子, byte 左辺の変数タイプ,
		// String 左辺の変数名, byte 右辺の変数タイプ, String 右辺の変数名,
		byte varType = iterator.next().asIntegerValue().getByte();
		String varName = iterator.next().asRawValue().getString();
		byte operator = iterator.next().asIntegerValue().getByte();
		byte leftVarType = iterator.next().asIntegerValue().getByte();
		String leftVarName = iterator.next().asRawValue().getString();
		byte rightVarType = iterator.next().asIntegerValue().getByte();
		String rightVarName = iterator.next().asRawValue().getString();
		StoryCalculation story = new StoryCalculation(line, varType, varName, operator, leftVarType, leftVarName,
				rightVarType, rightVarName);
		return story;
	}
}
