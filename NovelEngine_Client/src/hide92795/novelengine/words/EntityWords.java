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
package hide92795.novelengine.words;

import hide92795.novelengine.Renderer;
import hide92795.novelengine.box.Box;
import hide92795.novelengine.character.DataCharacter;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.manager.ConfigurationManager;
import hide92795.novelengine.manager.ConfigurationManager.Setting;
import hide92795.novelengine.panel.PanelStory;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

/**
 * 複数行の文章データを保管し、描画などを行うクラスです。
 * 
 * @author hide92795
 */
public class EntityWords {
	/**
	 * この文章の名前に使用するキャラクターのIDです。
	 */
	private final int characterId;
	/**
	 * 一時的に文章データを保存するリストです。
	 */
	private ArrayList<LineWords> list;
	/**
	 * 表示する名前のイメージIDです。
	 */
	private int nameImageId;
	/**
	 * 名前を表示するX座標です。
	 */
	private float nameX;
	/**
	 * 名前を表示するY座標です。
	 */
	private float nameY;
	/**
	 * 文章データを保存する配列です。
	 */
	private LineWords[] lineWords;
	/**
	 * 各行の文章データの表示割合を表します。
	 */
	private float[] ratios;
	/**
	 * 各行の文字送りに必要な時間を表します。
	 */
	private int[] times;
	/**
	 * この文章データの表示処理が始まってから経過した時間です。
	 */
	private int elapsedTime;
	/**
	 * 全ての文字送りが終わったかどうかを表します。
	 */
	private boolean finish;
	/**
	 * 全ての文字送りが終わった際に通知を受け取るリスナーです。
	 */
	private WordsProcessListener listener;

	/**
	 * 文章データを作成します。<br>
	 * この時点ではまだ画像データの作成はされていません。
	 * 
	 * @param characterId
	 *            この文章の名前に使用するキャラクターのID
	 */
	public EntityWords(int characterId) {
		this.characterId = characterId;
		list = new ArrayList<LineWords>();
	}

	/**
	 * 文章データを初期化します。
	 * 
	 * @param story
	 *            この文章データを表示している {@link hide92795.novelengine.panel.PanelStory PanelStory} オブジェクト
	 */
	public void init(PanelStory story) {
		elapsedTime = 0;
		finish = false;
		int num = lineWords.length;
		ratios = new float[num];
		times = new int[num];

		int speed = story.engine().getConfigurationManager()
				.getValue(ConfigurationManager.VARIABLE_RENDER, Setting.RENDER_WORDS_SPEED);
		for (int i = 0; i < num; i++) {
			ratios[i] = 0.0f;
			int time = Math.round((float) lineWords[i].width / speed * 1000);
			times[i] = time;
		}

		DataCharacter character = story.engine().getCharacterManager().getCharacter(characterId);
		nameImageId = character.getNameImageId();
	}

	/**
	 * 文章データを更新します。
	 * 
	 * @param story
	 *            この文章データを表示している {@link hide92795.novelengine.panel.PanelStory PanelStory} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public void update(PanelStory story, int delta) {
		elapsedTime += delta;
		// 残り時間
		int time = elapsedTime;
		Box box = story.engine().getBoxManager().getCurrentBox();
		float x = box.getX() + box.getAreaLightUpX();
		float y = box.getY() + box.getAreaLightUpY();
		int num = lineWords.length;
		// 行数分繰り返す
		for (int i = 0; i < num; i++) {
			// 文字送りが終わっていない場合はそちらの計算を行う
			if (!finish) {
				// 残り時間がまだある時に処理続行
				if (time > 0) {
					// まだ処理が終わっていないなら続行
					if (ratios[i] != 1.0f) {
						// 現在の残り時間から更新割合を算出
						float ratio = (float) time / times[i];
						// この行の表示が終わったなら割合を1に
						if (ratio >= 1.0f) {
							ratio = 1.0f;
							// 最後の行なら終了を通知
							if (i + 1 == num) {
								finish = true;
								if (listener != null) {
									listener.onProcessFinish();
								}
							}
						}
						// この行の更新割合を更新
						ratios[i] = ratio;
					}
					// 残り時間から使用した時間を引く
					time -= times[i];
				}
			}
			// 位置の更新
			LineWords w = lineWords[i];
			w.x = x;
			w.y = y;
			y += w.height;
		}
		int nameType = box.getNameType();
		if (nameType == Box.NAME_LEFT) {
			nameX = box.getX() + box.getNameX();
			nameY = box.getY() + box.getNameY();
		} else if (nameType == Box.NAME_CENTER) {
			Texture texture = story.engine().getImageManager().getImage(nameImageId);
			int imageWidth = texture.getImageWidth();
			int imageHeight = texture.getImageHeight();
			float deltaX = (float) imageWidth / 2;
			float deltaY = (float) imageHeight / 2;
			nameX = box.getX() + box.getNameX() - deltaX;
			nameY = box.getY() + box.getNameY() - deltaY;
		}
	}

	/**
	 * メッセージボックスに文章を描画します。
	 * 
	 * @param engine
	 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
	 */
	public void render(NovelEngine engine) {
		int num = lineWords.length;
		for (int i = 0; i < num; i++) {
			lineWords[i].render(engine, ratios[i]);
		}
		Texture texture = engine.getImageManager().getImage(nameImageId);
		Renderer.renderImage(texture, 1.0f, nameX, nameY);
	}

	/**
	 * 行を追加します。
	 * 
	 * @param imageId
	 *            この行の文章のイメージID
	 * @param width
	 *            この行の文章の画像の横幅
	 * @param height
	 *            この行の文章の画像の高さ
	 */
	public void addLine(int imageId, int width, int height) {
		LineWords lineWords = new LineWords(imageId, width, height);
		list.add(lineWords);
	}

	/**
	 * 全ての行を追加し、これ以上追加できないようにします。
	 */
	public void addFinish() {
		lineWords = list.toArray(new LineWords[0]);
		list = null;
	}

	/**
	 * 文字送り完了時に呼ばれるリスナーを設定します。
	 * 
	 * @param listener
	 *            文字送りが終わった際に通知を受け取るリスナー
	 */
	public void setListener(WordsProcessListener listener) {
		this.listener = listener;
	}

	/**
	 * この文章データを文字送りの完了を待たずにすべて表示します。
	 */
	public void forceFinish() {
		int num = lineWords.length;
		for (int i = 0; i < num; i++) {
			ratios[i] = 1.0f;
		}
		finish = true;
		if (listener != null) {
			listener.onProcessFinish();
		}
	}

	/**
	 * 文章データの１行のデータを表します。
	 * 
	 * @author hide92795
	 */
	private class LineWords {
		/**
		 * 表示するイメージIDです。
		 */
		private int imageId;
		/**
		 * 表示する画像の横幅です。
		 */
		private int width;
		/**
		 * 表示する画像の縦幅です。
		 */
		private int height;
		/**
		 * この行を表示するX座標です。
		 */
		private float x;
		/**
		 * この行を表示するY座標です。
		 */
		private float y;

		/**
		 * 文章データ内の１行分のデータを作成します。
		 * 
		 * @param imageId
		 *            表示するイメージID
		 * @param width
		 *            表示する画像の横幅
		 * @param height
		 *            表示する画像の縦幅
		 */
		public LineWords(int imageId, int width, int height) {
			this.imageId = imageId;
			this.width = width;
			this.height = height;
		}

		/**
		 * この行を描画します。
		 * 
		 * @param engine
		 *            実行中の {@link hide92795.novelengine.client.NovelEngine NovelEngine} オブジェクト
		 * @param ratio
		 *            この行の表示割合
		 */
		public void render(NovelEngine engine, float ratio) {
			Texture t = engine.getImageManager().getImage(imageId);
			if (t != null) {
				float renderWidth = width * ratio;
				float renderRatio = renderWidth / t.getTextureWidth();

				float x1 = x + renderWidth;
				float y1 = y + t.getTextureHeight();

				Renderer.renderImage(t, 1.0f, x, y, x1, y1, 0.0f, 0.0f, renderRatio, 1.0f);
			}
		}

	}

}
