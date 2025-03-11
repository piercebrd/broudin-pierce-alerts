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

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        dataService.getMedicalRecords().add(medicalRecord);
        dataService.saveData();
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        for (int i = 0; i < dataService.getMedicalRecords().size(); i++) {
            MedicalRecord existingRecord = dataService.getMedicalRecords().get(i);
            if (existingRecord.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                    existingRecord.getLastName().trim().equalsIgnoreCase(trimmedLastName)) {

                updatedRecord.setFirstName(trimmedFirstName);
                updatedRecord.setLastName(trimmedLastName);
                dataService.getMedicalRecords().set(i, updatedRecord);
                dataService.saveData();
                System.out.println("Updated medical record for: " + trimmedFirstName + " " + trimmedLastName);
                return updatedRecord;
            }
        }

        System.out.println("Medical record not found: " + trimmedFirstName + " " + trimmedLastName);
        return null;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        boolean removed = dataService.getMedicalRecords().removeIf(record ->
                record.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        record.getLastName().trim().equalsIgnoreCase(trimmedLastName));

        if (removed) {
            dataService.saveData();
            System.out.println("Deleted medical record for: " + trimmedFirstName + " " + trimmedLastName);
        } else {
            System.out.println("Medical record not found: " + trimmedFirstName + " " + trimmedLastName);
        }

        return removed;
    }
}
