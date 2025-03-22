package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
import com.safetynet.broudin_pierce_alerts.service.PersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * REST controller that provides personal and medical information for individuals based on last name.
 *
 * <p>This endpoint returns detailed information for each person with the given last name,
 * including address, age, email, medications, and allergies.
 *
 * <p>Endpoint: <code>GET /personInfo?lastName={lastName}</code>
 */

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoController.class);

    private final PersonInfoService personInfoService;

    /**
     * Constructs a {@code PersonInfoController} with the provided service.
     *
     * @param personInfoService the service responsible for retrieving person info by last name
     */

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    /**
     * Retrieves personal and medical information for all individuals with the specified last name.
     *
     * @param lastName the last name to filter by
     * @return a {@link ResponseEntity} containing:
     * <ul>
     *   <li>a list of {@link PersonInfoDTO} if matches are found</li>
     *   <li>an empty list if no person matches the last name</li>
     * </ul>
     */

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

