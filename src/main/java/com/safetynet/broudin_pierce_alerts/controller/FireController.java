package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
import com.safetynet.broudin_pierce_alerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * REST controller that provides fire emergency information for a specific address.
 *
 * <p>This endpoint returns a list of residents living at the specified address,
 * along with their medical information and the fire station number that serves them.
 *
 * <p>Endpoint: <code>GET /fire?address={address}</code>
 */

@RestController
@RequestMapping("/fire")
public class FireController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireController.class);

    private final FireService fireService;

    /**
     * Constructs a {@code FireController} with the required {@link FireService}.
     *
     * @param fireService the service responsible for retrieving fire-related data
     */

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    /**
     * Retrieves fire station number and resident information for the given address.
     *
     * @param address the address to retrieve fire response data for
     * @return a {@link ResponseEntity} containing:
     * <ul>
     *   <li>a {@link FireResponseDTO} if residents are found</li>
     *   <li>an empty list if no residents are present at the address</li>
     * </ul>
     */

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

