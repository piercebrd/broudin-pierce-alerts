package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.PhoneAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getPhoneNumbers(@RequestParam String firestation) {
        Set<String> phoneNumbers = phoneAlertService.getPhoneNumberByFireStation(firestation);
        return phoneNumbers.isEmpty() ? ResponseEntity.ok(Collections.emptySet()) : ResponseEntity.ok(phoneNumbers);
    }
}
