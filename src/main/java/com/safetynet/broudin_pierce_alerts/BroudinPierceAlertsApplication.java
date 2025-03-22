package com.safetynet.broudin_pierce_alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the SafetyNet Alerts application.
 *
 * <p>This class bootstraps the Spring Boot application using {@link SpringApplication}.
 * It logs startup and successful launch messages to assist with application monitoring.
 */

@SpringBootApplication
public class BroudinPierceAlertsApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BroudinPierceAlertsApplication.class);

	/**
	 * Launches the Spring Boot application.
	 *
	 * @param args optional command-line arguments
	 */

	public static void main(String[] args) {
		LOGGER.info("Starting broudin_pierce_alerts application...");
		SpringApplication.run(BroudinPierceAlertsApplication.class, args);
		LOGGER.info("broudin_pierce_alerts application started successfully.");
	}
}
