package com.safetynet.broudin_pierce_alerts.controller;


import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.service.ChildAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/childAlert")

public class ChildAlertController {

    private final ChildAlertService childAlertService;

    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    @GetMapping
    public ResponseEntity<?> getChildrenAddress(@RequestParam String address) {
        List<ChildAlertResponseDTO> children = childAlertService.getChildrenAddress(address);
        return children.isEmpty() ? ResponseEntity.ok(""): ResponseEntity.ok(children);
    }
}
