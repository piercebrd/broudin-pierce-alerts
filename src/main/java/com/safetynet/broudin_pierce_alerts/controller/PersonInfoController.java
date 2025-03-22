package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
import com.safetynet.broudin_pierce_alerts.service.PersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoController.class);

    private final PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(@RequestParam String lastName) {
        LOGGER.info("GET /personInfo called with lastName={}", lastName);

        List<PersonInfoDTO> response = personInfoService.getPersonInfoByLastName(lastName);

        if (response.isEmpty()) {
            LOGGER.info("No person info found for lastName: {}", lastName);
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            LOGGER.info("Found {} person(s) for lastName: {}", response.size(), lastName);
            return ResponseEntity.ok(response);
        }
    }
}
