package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final DataService dataService;

    public MedicalRecordService(DataService dataService) {
        this.dataService = dataService;
    }

    public Optional<MedicalRecord> getMedicalRecordForPerson(String firstName, String lastName) {
        return dataService.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(firstName) &&
                        record.getLastName().equals(lastName))
                .findFirst();
    }

    public int calculateAge(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing birthdate: " + birthdate + " - " + e.getMessage());
            return 18;
        }
    }
}
