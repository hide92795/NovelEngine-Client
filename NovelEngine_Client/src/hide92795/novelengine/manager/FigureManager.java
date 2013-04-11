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
package hide92795.novelengine.manager;

import hide92795.novelengine.background.figure.Figure;
import hide92795.novelengine.loader.LoaderFigure;

import java.util.HashMap;

/**
 * フィギュアデータを管理するマネージャーです。
 * 
 * @author hide92795
 */
public class FigureManager {
	/**
	 * フィギュアデータを格納するマップです。
	 */
	private HashMap<Integer, Figure> figures;

	/**
	 * {@link hide92795.novelengine.manager.FigureManager FigureManager} のオブジェクトを生成します。
	 */
	public FigureManager() {
		this.figures = new HashMap<Integer, Figure>();
		load();
	}

	/**
	 * 外部データからフィギュアデータを読み込みます。<br>
	 */
	private void load() {
		LoaderFigure.load(this);
	}

	/**
	 * フィギュアデータを登録します。
	 * 
	 * @param id
	 *            登録するフィギュアデータのID
	 * @param figure
	 *            登録するフィギュアデータ
	 */
	public void addFigure(int id, Figure figure) {
		figures.put(id, figure);
	}

	/**
	 * 登録されているフィギュアデータを取得します。
	 * 
	 * @param figureId
	 *            取得するフィギュアデータのID
	 * @return フィギュアデータ
	 */
	public Figure getFigure(int figureId) {
		return figures.get(figureId);
	}

}
