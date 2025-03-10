package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.service.CommunityEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getEmails(@RequestParam String city) {
        Set<String> emails = communityEmailService.getEmailsByCity(city);
        return emails.isEmpty() ? ResponseEntity.ok(Collections.emptySet()) : ResponseEntity.ok(emails);
    }
}
