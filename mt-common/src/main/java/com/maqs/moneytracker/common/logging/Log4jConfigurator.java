package com.maqs.moneytracker.common.logging;

import java.io.IOException;

import org.springframework.util.Log4jConfigurer;

/**
 * This class initiates the Log4J configuration.
 * 
 * @author maqbool.ahmed
 *
 */
public class Log4jConfigurator {

	private static final Logger logger = Logger
			.getLogger(Log4jConfigurator.class);
	
	/**
	 * Configures the given file to the Log4J instance.
	 * @param filePath .properties or xml
	 * @throws IOException if the given file is not found.
	 */
	public static void configure(String filePath) throws IOException {
		Log4jConfigurer.initLogging(filePath);
		logger.info("Configured Log4j with config file from: " + filePath);
	}
}
