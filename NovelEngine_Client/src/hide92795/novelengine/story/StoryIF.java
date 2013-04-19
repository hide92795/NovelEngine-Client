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
 * ２つの値を比較するストーリーデータです。<br>
 * 結果によってそれぞれ指定されたシーンIDへ移動します。
 * 
 * @author hide92795
 */
public class StoryIF extends Story {
	/**
	 * イコールを表します。
	 */
	public static final int IF_EQUAL = 0;
	/**
	 * 大なりを表します。
	 */
	public static final int IF_GREATER = 1;
	/**
	 * 小なりを表します。
	 */
	public static final int IF_LESS = 2;
	/**
	 * 大なりイコールを表します。
	 */
	public static final int IF_GREATER_EQUAL = 3;
	/**
	 * 小なりイコールを表します。
	 */
	public static final int IF_LESS_EQUAL = 4;
	/**
	 * 比較を行う演算子です。
	 */
	private final byte operator;
	/**
	 * 演算子の左側の変数の種類です。
	 */
	private final byte leftVarType;
	/**
	 * 演算子の左側の変数の名前です。
	 */
	private final String leftVarName;
	/**
	 * 演算子の右側の変数の種類です。
	 */
	private final byte rightVarType;
	/**
	 * 演算子の右側の変数の名前です。
	 */
	private final String rightVarName;
	/**
	 * 演算結果が真の場合に移動するシーンIDです。
	 */
	private final int trueGoto;
	/**
	 * 演算結果が偽の場合に移動するシーンIDです。
	 */
	private final int falseGoto;

	/**
	 * ２つの値を比較するストーリーデータを生成します。
	 * 
	 * @param line
	 *            このストーリーデータの行番号
	 * @param operator
	 *            ストーリーデータが行う演算子
	 * @param leftVarType
	 *            演算子の左側の変数の種類
	 * @param leftVarName
	 *            演算子の左側の変数の名前
	 * @param rightVarType
	 *            演算子の右側の変数の種類
	 * @param rightVarName
	 *            演算子の右側の変数の名前
	 * @param trueGoto
	 *            演算結果が真の場合に移動するシーンID
	 * @param falseGoto
	 *            演算結果が偽の場合に移動するシーンID
	 */
	public StoryIF(int line, byte operator, byte leftVarType, String leftVarName, byte rightVarType,
			String rightVarName, int trueGoto, int falseGoto) {
		super(line);
		this.operator = operator;
		this.leftVarType = leftVarType;
		this.leftVarName = leftVarName;
		this.rightVarType = rightVarType;
		this.rightVarName = rightVarName;
		this.trueGoto = trueGoto;
		this.falseGoto = falseGoto;
	}

	@Override
	public void init(PanelStory story) {
		resetFinish();
	}

	@Override
	public void update(PanelStory story, int delta) {
		if (!isFinish()) {
			int left = story.engine().getConfigurationManager().getValue(leftVarType, leftVarName);
			int right = story.engine().getConfigurationManager().getValue(rightVarType, rightVarName);
			boolean b = evaluation(left, right);
			if (b) {
				story.moveScene(trueGoto);
			} else {
				story.moveScene(falseGoto);
			}
			finish();
		}
	}

	/**
	 * 比較を行います。
	 * 
	 * @param left
	 *            演算子の左側の変数
	 * @param right
	 *            演算子の右側の変数
	 * @return 比較結果
	 */
	private boolean evaluation(int left, int right) {
		switch (operator) {
		case IF_EQUAL:
			return left == right;
		case IF_GREATER:
			return left > right;
		case IF_LESS:
			return left < right;
		case IF_GREATER_EQUAL:
			return left >= right;
		case IF_LESS_EQUAL:
			return left <= right;
		default:
			break;
		}
		return false;
	}
}
