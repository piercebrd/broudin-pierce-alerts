package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    private FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation firestation) {
        LOGGER.info("POST /firestation - Adding FireStation: {}", firestation);
        FireStation created = fireStationService.addFireStation(firestation);
        LOGGER.info("FireStation added successfully: {}", created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateFireStation(
            @RequestParam String address,
            @RequestParam String stationNumber) {
        LOGGER.info("PUT /firestation - Updating station at address: {} to stationNumber: {}", address, stationNumber);
        FireStation updated = fireStationService.updateFireStation(address, stationNumber);

        if (updated != null) {
            LOGGER.info("FireStation updated successfully: {}", updated);
            return ResponseEntity.ok(updated);
        } else {
            LOGGER.warn("Update failed - Address not found: {}", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFireStation(@RequestParam String address) {
        LOGGER.info("DELETE /firestation - Deleting FireStation at address: {}", address);
        boolean deleted = fireStationService.deleteFireStation(address);

        if (deleted) {
            LOGGER.info("FireStation deleted successfully at address: {}", address);
            return ResponseEntity.ok("Deleted FireStation");
        } else {
            LOGGER.warn("Delete failed - Address not found: {}", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }
    }

    @GetMapping
    public FireStationResponseDTO getPeopleByStation(@RequestParam String stationNumber) {
        LOGGER.info("GET /firestation - Fetching people for fire station: {}", stationNumber);
        return fireStationService.getPeopleByStation(stationNumber);
    }
}
