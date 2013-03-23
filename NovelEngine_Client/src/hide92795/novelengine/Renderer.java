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
package hide92795.novelengine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glColor4ub;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import hide92795.novelengine.client.NovelEngine;

import org.newdawn.slick.opengl.Texture;

/**
 * 描画に関する処理を行います。
 * 
 * @author hide92795
 */
public class Renderer {
	/**
	 * このクラスはユーティリティクラスのためオブジェクト化できません。
	 */
	private Renderer() {
	}

	/**
	 * 指定したテクスチャを左上を起点に描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 */
	public static void renderImage(Texture texture) {
		renderImage(texture, 1.0f);
	}

	/**
	 * 指定したテクスチャを左上を起点に指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 */
	public static void renderImage(Texture texture, float alpha) {
		renderImage(texture, alpha, 0.0f, 0.0f);
	}

	/**
	 * 指定したテクスチャを指定された点を起点に指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y) {
		renderImage(texture, alpha, x, y, 1.0f);
	}

	/**
	 * 指定したテクスチャを指定された点を起点に指定した拡大率で拡大縮小し、指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param magnificartion
	 *            拡大率
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float magnificartion) {
		renderImage(texture, alpha, x, y, x + texture.getTextureWidth() * magnificartion,
				y + texture.getTextureHeight() * magnificartion);
	}

	/**
	 * 指定したテクスチャを指定した範囲内に収まるように拡大縮小し、指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param x1
	 *            画像を描画する右下の点のX座標
	 * @param y1
	 *            画像を描画する右下の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float x1, float y1) {
		renderImage(texture, alpha, x, y, x1, y1, 0.0f, 0.0f, 1.0f, 1.0f);
	}

	/**
	 * テクスチャの指定範囲内の領域を、指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param cx
	 *            描画する画像の左上の点のX座標
	 * @param cy
	 *            描画する画像の左上の点のY座標
	 * @param cx1
	 *            描画する画像の右下の点のX座標
	 * @param cy1
	 *            描画する画像の右下の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float cx, float cy, float cx1,
			float cy1) {
		renderImage(texture, alpha, x, y, 1.0f, cx, cy, cx1, cy1);
	}

	/**
	 * テクスチャの指定範囲内の領域を指定した拡大率で拡大縮小し、指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param magnificartion
	 *            拡大率
	 * @param cx
	 *            描画する画像の左上の点のX座標
	 * @param cy
	 *            描画する画像の左上の点のY座標
	 * @param cx1
	 *            描画する画像の右下の点のX座標
	 * @param cy1
	 *            描画する画像の右下の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float magnificartion, float cx,
			float cy, float cx1, float cy1) {
		renderImage(texture, alpha, x, y, x + texture.getTextureWidth() * magnificartion,
				y + texture.getTextureHeight() * magnificartion, cx, cy, cx1, cy1);

	}

	/**
	 * テクスチャの指定範囲内の領域を指定された描画領域に収まるように拡大縮小し、指定したアルファ値で描画します。
	 * 
	 * @param texture
	 *            描画するテクスチャ
	 * @param alpha
	 *            アルファ値
	 * @param x
	 *            画像を描画する左上の点のX座標
	 * @param y
	 *            画像を描画する左上の点のY座標
	 * @param x1
	 *            画像を描画する右下の点のX座標
	 * @param y1
	 *            画像を描画する右下の点のY座標
	 * @param cx
	 *            描画する画像の左上の点のX座標
	 * @param cy
	 *            描画する画像の左上の点のY座標
	 * @param cx1
	 *            描画する画像の右下の点のX座標
	 * @param cy1
	 *            描画する画像の右下の点のY座標
	 */
	public static void renderImage(Texture texture, float alpha, float x, float y, float x1, float y1, float cx,
			float cy, float cx1, float cy1) {
		texture.bind();
		glColor4d(1.0f, 1.0f, 1.0f, alpha);
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(cx, cy);
			glVertex2f(x, y);
			glTexCoord2f(cx1, cy);
			glVertex2f(x1, y);
			glTexCoord2f(cx1, cy1);
			glVertex2f(x1, y1);
			glTexCoord2f(cx, cy1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	/**
	 * 画面全体を指定した色で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 */
	public static void renderColor(float red, float green, float blue) {
		renderColor(red, green, blue, 1.0f);
	}

	/**
	 * 画面全体を指定した色とアルファ値で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 * @param alpha
	 *            アルファ値(0.0f～1.0fの範囲内)
	 */
	public static void renderColor(float red, float green, float blue, float alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.getEngine().getDefaultWidth(), NovelEngine.getEngine()
				.getDefaultHeight());
	}

	/**
	 * 指定した範囲内を指定した色とアルファ値で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0.0f～1.0fの範囲内)
	 * @param green
	 *            緑成分(0.0f～1.0fの範囲内)
	 * @param blue
	 *            青成分(0.0f～1.0fの範囲内)
	 * @param alpha
	 *            アルファ値(0.0f～1.0fの範囲内)
	 * @param x
	 *            塗りつぶす範囲の左上の点のX座標
	 * @param y
	 *            塗りつぶす範囲の左上の点のY座標
	 * @param x1
	 *            塗りつぶす範囲の右下の点のX座標
	 * @param y1
	 *            塗りつぶす範囲の右下の点のY座標
	 */
	public static void renderColor(float red, float green, float blue, float alpha, float x, float y, float x1, float y1) {
		glColor4d(red, green, blue, alpha);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x1, y);
			glVertex2f(x1, y1);
			glVertex2f(x, y1);
		}
		glEnd();
	}

	/**
	 * 画面全体を指定した色で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 */
	public static void renderColor(byte red, byte green, byte blue) {
		renderColor(red, green, blue, (byte) 0xFF);
	}

	/**
	 * 画面全体を指定した色とアルファ値で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 * @param alpha
	 *            アルファ値(0～255の範囲内)
	 */
	public static void renderColor(byte red, byte green, byte blue, byte alpha) {
		renderColor(red, green, blue, alpha, 0, 0, NovelEngine.getEngine().getDefaultWidth(), NovelEngine.getEngine()
				.getDefaultHeight());
	}

	/**
	 * 指定した範囲内を指定した色とアルファ値で塗りつぶします。
	 * 
	 * @param red
	 *            赤成分(0～255の範囲内)
	 * @param green
	 *            緑成分(0～255の範囲内)
	 * @param blue
	 *            青成分(0～255の範囲内)
	 * @param alpha
	 *            アルファ値(0～255の範囲内)
	 * @param x
	 *            塗りつぶす範囲の左上の点のX座標
	 * @param y
	 *            塗りつぶす範囲の左上の点のY座標
	 * @param x1
	 *            塗りつぶす範囲の右下の点のX座標
	 * @param y1
	 *            塗りつぶす範囲の右下の点のY座標
	 */
	public static void renderColor(byte red, byte green, byte blue, byte alpha, float x, float y, float x1, float y1) {
		glColor4ub(red, green, blue, alpha);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glVertex2f(x, y);
			glVertex2f(x1, y);
			glVertex2f(x1, y1);
			glVertex2f(x, y1);
		}
		glEnd();
	}
}
