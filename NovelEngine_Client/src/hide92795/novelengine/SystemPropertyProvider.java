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

import java.util.Calendar;
import java.util.Locale;

/**
 * システム変数を取得するためのクラスです。
 * 
 * @author hide92795
 */
public final class SystemPropertyProvider {
	/**
	 * このクラスはユーティリティクラスのためオブジェクト化できません。
	 */
	private SystemPropertyProvider() {
	}

	/**
	 * システム変数を取得します。
	 * 
	 * @param name
	 *            取得する変数の名前
	 * @return 取得したシステム変数
	 */
	public static int getSystemProperty(String name) {
		switch (name) {
		case SystemSettings.SYSTEM_YEAR:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.YEAR);
		case SystemSettings.SYSTEM_MONTH:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.MONTH) + 1;
		case SystemSettings.SYSTEM_DATE:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.DATE);
		case SystemSettings.SYSTEM_DAY:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.DAY_OF_WEEK);
		case SystemSettings.SYSTEM_HOUR:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.HOUR_OF_DAY);
		case SystemSettings.SYSTEM_MINUTE:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.MINUTE);
		case SystemSettings.SYSTEM_SECOND:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.SECOND);
		case SystemSettings.SYSTEM_MILLISECOND:
			return Calendar.getInstance(Locale.JAPAN).get(Calendar.MILLISECOND);
		default:
			return 0;
		}
	}
}
