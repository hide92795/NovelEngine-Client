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

import hide92795.novelengine.background.BackGroundEffect;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.manager.EffectManager.ClassData;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryBackGroundEffect;

import java.util.concurrent.ConcurrentHashMap;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * 背景エフェクトを実行するストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandBackGroundEffect implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			ConcurrentHashMap<String, Integer> initVariable, int line) throws Exception {
		// 数値 対象, 数値 遅延（ms）, 背景エフェクト エフェクト
		byte target = iterator.next().asIntegerValue().getByte();
		int delay = iterator.next().asIntegerValue().getInt();
		int effectId = iterator.next().asIntegerValue().getInt();
		ClassData<BackGroundEffect> cd = engine.getEffectManager().getBackgroundEffect(effectId);
		Object[] argClass = cd.getArgumentList();
		int argCount = argClass.length;
		Object[] c_obj = new Object[argCount];
		for (int j = 0; j < argCount; j++) {
			if (argClass[j].equals(int.class)) {
				// int
				c_obj[j] = iterator.next().asIntegerValue().getInt();
			}
		}
		BackGroundEffect backGroundEffect = cd.instantiation(c_obj);
		StoryBackGroundEffect story = new StoryBackGroundEffect(line, target, delay, backGroundEffect);
		return story;
	}
}
