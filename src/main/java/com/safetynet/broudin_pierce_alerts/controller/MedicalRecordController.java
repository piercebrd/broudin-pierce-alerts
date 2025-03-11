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

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // POST: Add a new medical record
    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Adding new medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord created = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT: Update an existing medical record (firstName and lastName are identifiers)
    @PutMapping
    public ResponseEntity<?> updateMedicalRecord(@RequestParam String firstName,
                                                 @RequestParam String lastName,
                                                 @RequestBody MedicalRecord updatedRecord) {
        MedicalRecord result = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord);
        if (result != null) {
            logger.info("Updated medical record for: {} {}", firstName, lastName);
            return ResponseEntity.ok(result);
        } else {
            logger.warn("Medical record not found for: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found.");
        }
    }

    // DELETE: Delete a medical record using firstName and lastName as unique identifiers
    @DeleteMapping
    public ResponseEntity<?> deleteMedicalRecord(@RequestParam String firstName,
                                                 @RequestParam String lastName) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            logger.info("Deleted medical record for: {} {}", firstName, lastName);
            return ResponseEntity.ok("Medical record deleted successfully.");
        } else {
            logger.warn("Medical record not found for: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found.");
        }
    }
}
