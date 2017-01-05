package com.innominds.itaf.loggers;

import org.apache.log4j.Logger;

/**
 * The Class Logs.
 */
public class Logs {
	
	/** The logs object. */
	private static Logs logsObject;
	
	/** The logger. */
	private static Logger logger;
	
	/** The logger initialized. */
	private static boolean loggerInitialized = false;

	/**
	 * Gets the logs obj and initialize.
	 *
	 * @return the logs obj and initialize
	 */
	public static Logs getLogsObjAndInitialize() {
		if (logsObject == null) {
			logsObject = new Logs();
			initializeLogger();
		}
		return logsObject;
	}

	/**
	 * Initialize logger.
	 *
	 * @return the logger
	 */
	@SuppressWarnings("null")
	private static Logger initializeLogger() {
		Logger APPLOGS = null;
		if (!(loggerInitialized)) {
			APPLOGS = Logger.getLogger("devpinoyLogger");
			APPLOGS.debug("Loaded Logger..");
			logger = APPLOGS;
		} else {
			APPLOGS.debug("Already Logger Loaded..");
		}

		return APPLOGS;
	}

	/**
	 * Log.
	 *
	 * @param message the message
	 */
	public void log(String message) {
		logger.debug(message);
	}

	/**
	 * Log info.
	 *
	 * @param message the message
	 */
	public void logInfo(String message) {
		logger.info(message);
	}

	/**
	 * Log error.
	 *
	 * @param message the message
	 * @param t the t
	 */
	public void logError(String message, Throwable t) {
		logger.error(message, t);
	}
}