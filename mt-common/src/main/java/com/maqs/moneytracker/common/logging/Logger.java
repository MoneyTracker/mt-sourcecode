package com.maqs.moneytracker.common.logging;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Application specific wrapper for logging. By default, this uses SLF logging
 * as a Facade.
 * 
 * @author maqbool.ahmed
 * 
 */
public final class Logger implements Log {

	private final Class targetClass;

	private final org.slf4j.Logger actualLogger;
	
	private static final Hashtable<Class, Logger> tempCache = new Hashtable<Class, Logger>();

	private static final Marker fatal = MarkerFactory.getMarker("FATAL");

	private Logger() {
		this(Logger.class); // this constructor will never be called
									// from the application, the reason of
									// having it is to have no-arg constructor
	}

	private Logger(final Class targetClass) {
		this.targetClass = targetClass;
		this.actualLogger = LoggerFactory.getLogger(targetClass);
	}

	/**
	 * Logs the debug statement
	 */
	public void debug(Object message) {
		debug(message, null);
	}

	/**
	 * Logs the debug statement along with the cause
	 */
	public void debug(Object message, Throwable cause) {
		if (isDebugEnabled()) {
			actualLogger.debug(getMessage(message), cause);
		}
	}

	/**
	 * Logs the info statement
	 */
	public void info(Object message) {
		info(message, null);
	}

	/**
	 * Logs the info statement along with the cause
	 */
	public void info(Object message, Throwable cause) {
		if (isInfoEnabled()) {
			actualLogger.info(getMessage(message), cause);
		}
	}

	/**
	 * Logs the error statement
	 */
	public void error(Object message) {
		error(message, null);
	}

	/**
	 * Logs the error statement along with the cause
	 */
	public void error(Object message, Throwable cause) {
		if (isErrorEnabled()) {
			actualLogger.error(getMessage(message), cause);
		}
	}

	/**
	 * Logs the fatal statement
	 */
	public void fatal(Object message) {
		fatal(message, null);
	}

	/**
	 * Logs the fatal statement along with the cause
	 */
	public void fatal(Object message, Throwable cause) {
		if (isErrorEnabled()) {
			actualLogger.error(fatal, getMessage(message), cause);
		}
	}

	/**
	 * Logs the trace statement
	 */
	public void trace(Object message) {
		trace(message, null);
	}

	/**
	 * Logs the trace statement along with the cause
	 */
	public void trace(Object message, Throwable cause) {
		if (isTraceEnabled()) {
			actualLogger.trace(getMessage(message), cause);
		}
	}

	/**
	 * Logs the warn statement
	 */
	public void warn(Object message) {
		warn(message, null);
	}

	/**
	 * Logs the warn statement along with the cause
	 */
	public void warn(Object message, Throwable cause) {
		if (isWarnEnabled()) {
			actualLogger.warn(getMessage(message), cause);
		}
	}

	/**
	 * Checks whether error log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isErrorEnabled() {
		return actualLogger.isErrorEnabled();
	}

	/**
	 * Checks whether debug log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isDebugEnabled() {
		return actualLogger.isDebugEnabled();
	}

	/**
	 * Checks whether error log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isInfoEnabled() {
		return actualLogger.isInfoEnabled();
	}

	/**
	 * Checks whether error log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isFatalEnabled() {
		return actualLogger.isErrorEnabled();
	}

	/**
	 * Checks whether error log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isTraceEnabled() {
		return actualLogger.isTraceEnabled();
	}

	/**
	 * Checks whether error log is enabled
	 * @return true if enabled, otherwise false.
	 */
	public boolean isWarnEnabled() {
		return actualLogger.isWarnEnabled();
	}

	/**
	 * Returns the target class
	 * @return the target class
	 */
	public Class getTargetClass() {
		return targetClass;
	}

	/**
	 * Retrieves the targetClass logger from the cache if available, 
	 * otherwise creates a new instance of the logger and puts in the cache.
	 * @param targetClass Target Class
	 * @return Logger for the targetClass.
	 */
	public static Logger getLogger(Class targetClass) {
		Logger logger = null;
		if ((logger = tempCache.get(targetClass)) != null) {
			return logger;
		} else {
			logger = newInstance(targetClass);
			tempCache.put(targetClass, logger);
		}
		return logger;
	}

	/**
	 * Create a new instance of logger for a targetClass.
	 * @param clazz targetClass
	 * @return Logger
	 */
	private static Logger newInstance(Class clazz) {
		synchronized (Logger.class) {
			Logger logger = tempCache.get(clazz);
			if (logger == null)
				return new Logger(clazz);
			else
				return logger;
		}
	}

	/**
	 * Generates the String message
	 * @param o message objects, the target class sends.
	 * @return String message
	 */
	protected String getMessage(Object o) {
		return o.toString();
	}
}
