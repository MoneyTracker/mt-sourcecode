package com.maqs.moneytracker.common.logging;

public class LogbackConfigurator {

	/*private static final Logger logger = Logger
			.getLogger(LogbackConfigurator.class);

	private static LoggerContext loggerContext;

	public static void configure(String filePath) throws IOException,
			JoranException {
		String resolvedLocation = SystemPropertyUtils
				.resolvePlaceholders(filePath);

		URL fileUrl = ResourceUtils.getURL(resolvedLocation);
		if (fileUrl == null)
			return;
		if (loggerContext == null) {
			loggerContext = new LoggerContext();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(loggerContext);
			loggerContext.reset();
			configurator.doConfigure(fileUrl);
			logger.info("configured Logback's logging mechanism, configuration is done through " + filePath);
		}
	}*/
}
