package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.FireService;
import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/fire")
public class FireController {

    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<?> getFireInfo(@RequestParam String address) {
        FireResponseDTO response = fireService.getInfoByAddress(address);
        return response.getResidents().isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(response);
    }
}
