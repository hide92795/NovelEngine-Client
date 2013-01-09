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
	public NovelEngineException(final Throwable cause, final String chapterId) {
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
	public NovelEngineException(final String message, final Throwable cause, final String chapterId) {
		super(message, cause);
	}

}
