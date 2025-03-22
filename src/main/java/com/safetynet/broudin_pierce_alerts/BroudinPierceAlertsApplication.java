package com.safetynet.broudin_pierce_alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BroudinPierceAlertsApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BroudinPierceAlertsApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting broudin_pierce_alerts application...");
		SpringApplication.run(BroudinPierceAlertsApplication.class, args);
		LOGGER.info("broudin_pierce_alerts  application started successfully.");
	}
}
