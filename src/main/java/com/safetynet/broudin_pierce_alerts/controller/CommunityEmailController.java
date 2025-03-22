package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.CommunityEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityEmailController.class);

    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

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
