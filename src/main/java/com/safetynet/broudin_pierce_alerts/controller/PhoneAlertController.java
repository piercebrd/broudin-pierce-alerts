package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.PhoneAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertController.class);

    private final PhoneAlertService phoneAlertService;

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

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
