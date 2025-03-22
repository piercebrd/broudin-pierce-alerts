package com.safetynet.broudin_pierce_alerts.controller;

import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        LOGGER.info("POST /medicalRecord - Adding medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord created = medicalRecordService.addMedicalRecord(medicalRecord);
        LOGGER.info("Medical record added successfully for: {} {}", created.getFirstName(), created.getLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<?> updateMedicalRecord(@RequestParam String firstName,
                                                 @RequestParam String lastName,
                                                 @RequestBody MedicalRecord updatedRecord) {
        LOGGER.info("PUT /medicalRecord - Attempting to update record for: {} {}", firstName, lastName);
        MedicalRecord result = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord);
        if (result != null) {
            LOGGER.info("Medical record updated successfully for: {} {}", firstName, lastName);
            return ResponseEntity.ok(result);
        } else {
            LOGGER.warn("Medical record not found for update: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found.");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMedicalRecord(@RequestParam String firstName,
                                                 @RequestParam String lastName) {
        LOGGER.info("DELETE /medicalRecord - Attempting to delete record for: {} {}", firstName, lastName);
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            LOGGER.info("Medical record deleted successfully for: {} {}", firstName, lastName);
            return ResponseEntity.ok("Medical record deleted successfully.");
        } else {
            LOGGER.warn("Medical record not found for deletion: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found.");
        }
    }
}
