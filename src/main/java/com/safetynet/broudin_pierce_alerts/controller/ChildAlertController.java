package com.safetynet.broudin_pierce_alerts.controller;


import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/childAlert")

public class ChildAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertController.class);

    private final ChildAlertService childAlertService;

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    @GetMapping
    public ResponseEntity<?> getChildrenAddress(@RequestParam String address) {
        LOGGER.info("GET /childAlert called with address={}", address);

        List<ChildAlertResponseDTO> children = childAlertService.getChildrenAddress(address);

        if (children.isEmpty()) {
            LOGGER.info("No children found at address: {}", address);
            return ResponseEntity.ok("");
        } else {
            LOGGER.info("{} child(ren) found at address: {}", children.size(), address);
            return ResponseEntity.ok(children);
        }
    }
}
