package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    private FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public FireStationResponseDTO getPeopleByStation(@RequestParam String stationNumber) {
        logger.info("Fetching persons for fire station: {}", stationNumber);
        return fireStationService.getPeopleByStation(stationNumber);
    }






}
