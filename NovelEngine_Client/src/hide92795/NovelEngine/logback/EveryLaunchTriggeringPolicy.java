package hide92795.novelengine.logback;

import java.io.File;

import ch.qos.logback.core.rolling.TriggeringPolicyBase;

/**
 * 起動毎にログファイルのローリングを行います。
 *
 * @param <E>
 * @author hide92795
 */
public class EveryLaunchTriggeringPolicy<E> extends TriggeringPolicyBase<E> {
	/**
	 * ローリングを行ったかどうかを表します。
	 */
	private boolean launch = false;

	@Override
	public boolean isTriggeringEvent(File activeFile, E event) {
		if (!launch) {
			launch = true;
			return true;
		}
		return false;
	}

}
