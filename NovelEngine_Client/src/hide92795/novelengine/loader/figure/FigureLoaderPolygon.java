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
package hide92795.novelengine.loader.figure;

import hide92795.novelengine.background.figure.Figure;
import hide92795.novelengine.background.figure.Figure.Line;
import hide92795.novelengine.background.figure.FigurePolygon;

import org.msgpack.unpacker.Unpacker;

/**
 * 多角形のフィギュアデータをロードするためのフィギュアローダーです。
 * 
 * @author hide92795
 */
public class FigureLoaderPolygon implements FigureLoader {
	@Override
	public Figure load(int id, Unpacker unpacker) throws Exception {
		int apexNum = unpacker.readInt();
		int[][] apexes = new int[apexNum][2];
		for (int i = 0; i < apexNum; i++) {
			int x = unpacker.readInt();
			int y = unpacker.readInt();
			apexes[i][0] = x;
			apexes[i][1] = y;
		}
		int lineNum = unpacker.readInt();
		Line[] lines = new Line[lineNum];
		for (int i = 0; i < lineNum; i++) {
			int start = unpacker.readInt();
			int end = unpacker.readInt();
			int color = unpacker.readInt();
			byte red = (byte) ((color & 0x00FF0000) >> 16);
			byte green = (byte) ((color & 0x0000FF00) >> 8);
			byte blue = (byte) (color & 0x000000FF);
			byte alpha = unpacker.readByte();
			int width = unpacker.readInt();
			Line line = new Line(start, end, red, green, blue, alpha, width);
			lines[i] = line;
		}
		FigurePolygon figure = new FigurePolygon(id, apexes, lines);
		return figure;
	}
}
