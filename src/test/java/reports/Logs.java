package reports;

import org.apache.log4j.Logger;

public class Logs {

	private static Logger log = Logger.getLogger(Logs.class);

	public static void info(String message) {
		log.info(message);
	}

	public static void warning(String message) {
		log.warn(message);
	}

	public static void debug(String message) {
		log.debug(message);
	}

	public static void fatal(String message) {
		log.fatal(message);
	}

	public static void error(String message) {
		log.error(message);
	}

}
