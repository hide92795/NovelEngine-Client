package hide92795.novelengine;

import org.slf4j.LoggerFactory;

public class Logger {
	protected static org.slf4j.Logger logger;

	public static void init() {
		logger = LoggerFactory.getLogger("NovelEngine");
	}
	
	public static void info(Object msg) {
		logger.info(msg.toString());
	}

}
