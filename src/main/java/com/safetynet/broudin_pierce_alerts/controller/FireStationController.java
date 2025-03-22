package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles operations related to fire stations and their assigned addresses.
 *
 * <p>This controller provides endpoints to add, update, and delete fire stations, as well as
 * to retrieve people covered by a specific fire station.
 *
 * <p>Base endpoint: <code>/firestation</code>
 */

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    /**
     * Constructs a {@code FireStationController} with the required service dependency.
     *
     * @param fireStationService the service managing fire station data
     */

    private FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Adds a new fire station mapping.
     *
     * @param firestation the fire station to add (address + station number)
     * @return a {@link ResponseEntity} containing the created fire station and HTTP status 201 (Created)
     */

    @PostMapping
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation firestation) {
        LOGGER.info("POST /firestation - Adding FireStation: {}", firestation);
        FireStation created = fireStationService.addFireStation(firestation);
        LOGGER.info("FireStation added successfully: {}", created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Updates the station number for a given address.
     *
     * @param address the address to update
     * @param stationNumber the new station number to assign
     * @return a {@link ResponseEntity} with the updated fire station if found, or 404 if not found
     */

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

    /**
     * Deletes a fire station mapping by address.
     *
     * @param address the address to delete
     * @return a {@link ResponseEntity} indicating whether the deletion was successful
     */

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

    /**
     * Retrieves a list of people covered by the specified fire station number.
     * Also returns counts of adults and children.
     *
     * @param stationNumber the fire station number
     * @return a {@link FireStationResponseDTO} containing the list of residents and demographics
     */

    @GetMapping
    public FireStationResponseDTO getPeopleByStation(@RequestParam String stationNumber) {
        LOGGER.info("GET /firestation - Fetching people for fire station: {}", stationNumber);
        return fireStationService.getPeopleByStation(stationNumber);
    }
}
