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

@RestController
@RequestMapping("/flood/stations")
public class FloodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FloodController.class);

    private final FloodService floodService;

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

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
