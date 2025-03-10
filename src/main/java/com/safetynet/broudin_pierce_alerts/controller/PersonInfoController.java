package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
import com.safetynet.broudin_pierce_alerts.service.PersonInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private final PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(@RequestParam String lastName) {
        List<PersonInfoDTO> response = personInfoService.getPersonInfoByLastName(lastName);
        return response.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(response);
    }

}
