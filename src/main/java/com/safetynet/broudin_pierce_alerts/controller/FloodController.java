package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.service.FloodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST controller that provides flood-related resident information based on fire station coverage.
 *
 * <p>This endpoint returns a list of residents grouped by address,
 * for all addresses covered by one or more specified fire station numbers.
 *
 * <p>Each resident includes personal and medical information, which can be used
 * in emergency flood response planning.
 *
 * <p>Endpoint: <code>GET /flood/stations?stations={station1,station2,...}</code>
 */

@RestController
@RequestMapping("/flood/stations")
public class FloodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FloodController.class);

    private final FloodService floodService;

    /**
     * Constructs a {@code FloodController} with the required {@link FloodService}.
     *
     * @param floodService the service responsible for retrieving flood-related resident data
     */

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    /**
     * Retrieves resident information for all addresses covered by the given fire station numbers.
     *
     * @param stations a list of fire station numbers
     * @return a {@link ResponseEntity} containing:
     * <ul>
     *   <li>a map of addresses to lists of {@link ResidentDTO}</li>
     *   <li>an empty map if no residents are found</li>
     * </ul>
     */

    @GetMapping
    public ResponseEntity<Map<String, List<ResidentDTO>>> getFloodInfo(@RequestParam List<String> stations) {
        LOGGER.info("GET /flood/stations called with station list: {}", stations);

        Map<String, List<ResidentDTO>> response = floodService.getFloodInfoByStations(stations);

        if (response.isEmpty()) {
            LOGGER.info("No residents found for station(s): {}", stations);
            return ResponseEntity.ok(Collections.emptyMap());
        } else {
            LOGGER.info("Found residents for {} address(es) covered by station(s): {}", response.size(), stations);
            return ResponseEntity.ok(response);
        }
    }
}
