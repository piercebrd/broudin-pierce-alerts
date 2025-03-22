package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
import com.safetynet.broudin_pierce_alerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/fire")
public class FireController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireController.class);

    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<?> getFireInfo(@RequestParam String address) {
        LOGGER.info("GET /fire called with address={}", address);

        FireResponseDTO response = fireService.getInfoByAddress(address);

        if (response.getResidents().isEmpty()) {
            LOGGER.info("No residents found at address: {}", address);
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            LOGGER.info("Found {} resident(s) at address: {}, station number: {}",
                    response.getResidents().size(), address, response.getFireStationNumber());
            return ResponseEntity.ok(response);
        }
    }
}
