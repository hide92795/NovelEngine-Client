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

/**
 * システムが続行不可のエラーを出した際に投げられる例外です。
 *
 * @author hide92795
 */
public class NovelEngineException extends RuntimeException {
	/**
	 * 直列化の際に使用するUIDです。
	 */
	private static final long serialVersionUID = 3466365560863296487L;

	/**
	 * 新しく {@link hide92795.novelengine.NovelEngineException NovelEngineException} オブジェクトを生成します。
	 *
	 * @param cause
	 *            発生した例外
	 * @param chapterId
	 *            このエラーが発生したチャプターID
	 */
	public NovelEngineException(Throwable cause, String chapterId) {
		super(cause);
	}

	/**
	 * 新しく {@link hide92795.novelengine.NovelEngineException NovelEngineException} オブジェクトを生成します。
	 *
	 * @param cause
	 *            発生した例外
	 * @param message
	 *            この例外の詳細メッセージ
	 * @param chapterId
	 *            このエラーが発生したチャプターID
	 */
	public NovelEngineException(String message, Throwable cause, String chapterId) {
		super(message, cause);
	}

}