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
package hide92795.novelengine.story;

import hide92795.novelengine.panel.PanelStory;

/**
 * ブロックを表すストーリーデータです。<br>
 * このストーリーデータの終了確認が行われることはありません。
 * 
 * @author hide92795
 */
public class StoryBlock extends Story {
	/**
	 * 開始ブロックを表すストーリーデータです。
	 */
	public static final StoryBlock BLOCKSTART;
	/**
	 * 終了ブロックを表すストーリーデータです。
	 */
	public static final StoryBlock BLOCKEND;

	static {
		BLOCKSTART = new StoryBlock(-1, true);
		BLOCKEND = new StoryBlock(-2, false);
	}

	/**
	 * このブロックが開始ブロックかどうかを表します。
	 */
	private final boolean startBlock;

	/**
	 * ブロックを表すストーリーデータを生成します。<br>
	 * このストーリーデータはマーカーとしての機能を行うだけなので必要以上に生成されるべきではありません。<br>
	 * 行番号は常に-1となります。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
	 * @param start
	 *            このブロックが開始ブロックかどうか
	 * 
	 * @see StoryBlock#BLOCKSTART
	 * @see StoryBlock#BLOCKEND
	 */
	private StoryBlock(int line, boolean start) {
		super(line);
		this.startBlock = start;
	}

	@Override
	public void init(PanelStory story) {
	}

	/**
	 * このブロックが開始ブロックかどうかを返します。
	 * 
	 * @return このブロックが開始ブロックの場合は <code>true</code>
	 */
	public boolean isStartBlock() {
		return startBlock;
	}
}
