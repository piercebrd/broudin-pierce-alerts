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

    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    private FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }
    @PostMapping
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation firestation) {
        logger.info("Adding FireStation: {}", firestation);
        FireStation created = fireStationService.addFireStation(firestation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateFireStation(
            @RequestParam String address,
            @RequestParam String stationNumber) {
        FireStation updated = fireStationService.updateFireStation(address, stationNumber);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFireStation(@RequestParam String address) {
        boolean deleted = fireStationService.deleteFireStation(address);
        return deleted
                ? ResponseEntity.ok("Deleted FireStation")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
    }

    @GetMapping
    public FireStationResponseDTO getPeopleByStation(@RequestParam String stationNumber) {
        logger.info("Fetching people for fire station: {}", stationNumber);
        return fireStationService.getPeopleByStation(stationNumber);
    }






}
