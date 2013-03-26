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

import hide92795.novelengine.character.DataCharacter;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.LoaderResource;
import hide92795.novelengine.manager.FontManager.DataFont;
import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryShowWords;
import hide92795.novelengine.words.EntityWords;

import java.util.concurrent.ConcurrentHashMap;

import org.msgpack.unpacker.UnpackerIterator;

/**
 * 文章の表示を行うストーリーデータをロードします。
 * 
 * @author hide92795
 */
public class CommandShowWords implements Command {
	@Override
	public Story load(NovelEngine engine, UnpackerIterator iterator, LoaderResource resourceLoader,
			ConcurrentHashMap<String, Integer> initVariable) throws Exception {
		// int キャラID, String 文章
		int characterId = iterator.next().asIntegerValue().getInt();
		String words_str = iterator.next().asRawValue().getString();
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
		return story;
	}
}
