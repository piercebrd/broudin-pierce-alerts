package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.CommunityEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

/**
 * REST controller that provides email addresses of residents based on their city.
 *
 * <p>This endpoint allows retrieving all email addresses of people living
 * in a specified city, useful for sending community-wide alerts or information.
 *
 * <p>Endpoint: <code>GET /communityEmail?city={city}</code>
 */

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityEmailController.class);

    private final CommunityEmailService communityEmailService;

    /**
     * Constructs a {@code CommunityEmailController} with the given service dependency.
     *
     * @param communityEmailService the service used to retrieve emails by city
     */
    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    /**
     * Retrieves email addresses of all residents living in the specified city.
     *
     * @param city the name of the city to search for
     * @return a {@link ResponseEntity} containing:
     * <ul>
     *   <li>a set of email addresses if found</li>
     *   <li>an empty set if no residents are found</li>
     * </ul>
     */

    @GetMapping
    public ResponseEntity<Set<String>> getEmails(@RequestParam String city) {
        LOGGER.info("GET /communityEmail called with city={}", city);

        Set<String> emails = communityEmailService.getEmailsByCity(city);

        if (emails.isEmpty()) {
            LOGGER.info("No emails found for city: {}", city);
            return ResponseEntity.ok(Collections.emptySet());
        } else {
            LOGGER.info("Found {} email(s) for city: {}", emails.size(), city);
            return ResponseEntity.ok(emails);
        }
    }
}

