package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.service.FloodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flood/stations")
public class FloodController {

    private final FloodService floodService;

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<ResidentDTO>>> getFloodInfo(@RequestParam List<String> stations) {
        Map<String, List<ResidentDTO>> response = floodService.getFloodInfoByStations(stations);
        return response.isEmpty() ? ResponseEntity.ok(Collections.emptyMap()) : ResponseEntity.ok(response);
    }
}
