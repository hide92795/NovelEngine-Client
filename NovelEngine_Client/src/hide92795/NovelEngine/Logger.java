package hide92795.novelengine;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.LoggerFactory;

/**
 * ログに関係するクラスです。
 *
 * @author hide92795
 */
public class Logger {
	/**
	 * このクラスはユーティリティクラスのためオブジェクト化できません。
	 */
	private Logger() {
	}

	/**
	 * 使用するロガーです。
	 */
	private static org.slf4j.Logger logger;
	/**
	 * システム「標準」出力ストリームからのログを受け取るロガーです。
	 */
	private static org.slf4j.Logger logger_stdout;
	/**
	 * システム「標準」エラー出力ストリームからのログを受け取るロガーです。
	 */
	private static org.slf4j.Logger logger_stderr;
	/**
	 * 元のシステム「標準」出力ストリームです。
	 */
	private static PrintStream stdout;
	/**
	 * 元のシステム「標準」エラー出力ストリームです。
	 */
	private static PrintStream stderr;

	/**
	 * ロガーを初期化します。
	 */
	public static void init() {
		logger = LoggerFactory.getLogger("NovelEngine");
		logger_stdout = LoggerFactory.getLogger("STDOUT");
		logger_stderr = LoggerFactory.getLogger("STDERR");
		stdout = System.out;
		stderr = System.err;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}) {
			public void println(String s) {
				logger_stdout.info(s);
				stdout.println(s);
			}

			public void print(String s) {
				logger_stdout.info(s);
				stdout.println(s);
			}
		});
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}) {
			public void println(String s) {
				logger_stderr.info(s);
				stderr.println(s);
			}

			public void print(String s) {
				logger_stderr.info(s);
				stderr.println(s);
			}
		});
	}

	/**
	 * ログレベルINFOでログを出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void info(Object msg) {
		logger.info(msg.toString());
	}

	/**
	 * ログレベルERRORでログを出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void error(Object msg) {
		logger.error(msg.toString());
	}

	/**
	 * ログレベルWARNでログを出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void warn(Object msg) {
		logger.warn(msg.toString());
	}

	/**
	 * ログレベルDEBUGでログを出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void debug(Object msg) {
		logger.debug(msg.toString());
	}

	/**
	 * 元のシステム「標準」出力ストリームのみへ出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void stdout(Object msg) {
		stdout.println(msg);
	}

	/**
	 * 元のシステム「標準」エラー出力ストリームのみへ出力します。
	 *
	 * @param msg
	 *            出力するメッセージ
	 */
	public static void stderr(Object msg) {
		stderr.println(msg);
	}

}
