package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.PhoneAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

/**
 * REST controller that provides phone numbers of residents based on fire station coverage.
 *
 * <p>This endpoint is used to retrieve the set of phone numbers of people covered by a given fire station.
 * It is useful for alerting individuals in case of emergencies such as fires or disasters.
 *
 * <p>Endpoint: <code>GET /phoneAlert?firestation={firestation}</code>
 */

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertController.class);

    private final PhoneAlertService phoneAlertService;

    /**
     * Constructs a {@code PhoneAlertController} with the required {@link PhoneAlertService}.
     *
     * @param phoneAlertService the service used to retrieve phone numbers by fire station
     */

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    /**
     * Retrieves a set of phone numbers for residents covered by the specified fire station number.
     *
     * @param firestation the fire station number
     * @return a {@link ResponseEntity} containing:
     * <ul>
     *   <li>a set of phone numbers if residents are found</li>
     *   <li>an empty set if no residents are assigned to the station</li>
     * </ul>
     */

    @GetMapping
    public ResponseEntity<Set<String>> getPhoneNumbers(@RequestParam String firestation) {
        LOGGER.info("GET /phoneAlert called with firestation={}", firestation);

        Set<String> phoneNumbers = phoneAlertService.getPhoneNumberByFireStation(firestation);

        if (phoneNumbers.isEmpty()) {
            LOGGER.info("No phone numbers found for firestation: {}", firestation);
            return ResponseEntity.ok(Collections.emptySet());
        } else {
            LOGGER.info("Found {} phone number(s) for firestation: {}", phoneNumbers.size(), firestation);
            return ResponseEntity.ok(phoneNumbers);
        }
    }
}

