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
package hide92795.novelengine.panel;

import hide92795.novelengine.Properties;
import hide92795.novelengine.Renderer;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.loader.item.DataSavedBackGround;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;

import java.util.Map;

/**
 * {@link hide92795.novelengine.panel.PanelStory PanelStory} が初期化及びテクスチャ待機中に画面の描画を行うクラスです。
 * 
 * @author hide92795
 */
public class PanelPrestartStory extends Panel {
	/**
	 * ロード待機を行うチャプターのIDです。
	 */
	private final int chapterId;
	/**
	 * ストーリーを開始する行番号です。
	 */
	private final int line;
	/**
	 * ロードする際に必要なデータです。
	 */
	private final Map<String, Integer> internalData;
	/**
	 * 読み込み済みの背景データです。
	 */
	private final DataSavedBackGround savedBackGround;

	/**
	 * 指定されたチャプターのロードを待機する {@link hide92795.novelengine.panel.PanelStory PanelStory} を作成します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 * @param chapterid
	 *            ロード待機を行うチャプターのID
	 * @param line
	 *            ストーリーを開始する行番号
	 * @param internalData
	 *            ロードする際に必要なデータ
	 * @param savedBackGround
	 *            読み込み済みの背景データ
	 */
	public PanelPrestartStory(NovelEngine engine, int chapterid, int line, Map<String, Integer> internalData,
			DataSavedBackGround savedBackGround) {
		super(engine);
		this.chapterId = chapterid;
		this.line = line;
		this.internalData = internalData;
		this.savedBackGround = savedBackGround;
	}

	@Override
	public void init() {
	}

	@Override
	public void update(int delta) {
		if (engine().getStoryManager().isLoaded(chapterId)) {
			engine().startStory(chapterId, line, internalData, savedBackGround);
		}
	}

	@Override
	public void render(NovelEngine engine) {
		Properties prop = engine.getConfigurationManager().getProperties(ConfigurationManager.VARIABLE_RENDER);
		int rgb = prop.getProperty(Setting.RENDER_PRESTART_BACKGROUND_COLOR);
		int r = (rgb & 0x00FF0000) >> 16;
		int g = (rgb & 0x0000FF00) >> 8;
		int b = (rgb & 0x000000FF);
		Renderer.renderColor(r / 255.0f, g / 255.0f, b / 255.0f);
	}
}
