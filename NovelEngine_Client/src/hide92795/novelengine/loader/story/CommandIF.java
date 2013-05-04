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
import hide92795.novelengine.story.StoryIF;

import java.util.Map;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * ２つの値を比較するストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandIF implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			Map<String, Integer> initVariable, int line) throws Exception {
		// byte 演算子, byte 左変数タイプ ,int 左数字, byte 右変数タイプ ,int 右数字, int 真, int 偽
		byte operator = iterator.next().asIntegerValue().getByte();
		byte leftVarType = iterator.next().asIntegerValue().getByte();
		String leftVarName = iterator.next().asRawValue().getString();
		byte rightVarType = iterator.next().asIntegerValue().getByte();
		String rightVarName = iterator.next().asRawValue().getString();
		int trueGoto = iterator.next().asIntegerValue().getInt();
		int falseGoto = iterator.next().asIntegerValue().getInt();
		StoryIF story = new StoryIF(line, operator, leftVarType, leftVarName, rightVarType, rightVarName, trueGoto,
				falseGoto);
		return story;
	}
}
