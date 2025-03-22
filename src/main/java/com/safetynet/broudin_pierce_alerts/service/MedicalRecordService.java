package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final DataRepository dataRepository;

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public Optional<MedicalRecord> getMedicalRecordForPerson(String firstName, String lastName) {


        List<MedicalRecord> records = dataRepository.getMedicalRecords();

        return records.stream()
                .filter(record -> record.getFirstName().trim().equalsIgnoreCase(firstName.trim()) &&
                        record.getLastName().trim().equalsIgnoreCase(lastName.trim()))
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
        dataRepository.getMedicalRecords().add(medicalRecord);
        dataRepository.saveData();
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        for (int i = 0; i < dataRepository.getMedicalRecords().size(); i++) {
            MedicalRecord existingRecord = dataRepository.getMedicalRecords().get(i);
            if (existingRecord.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                    existingRecord.getLastName().trim().equalsIgnoreCase(trimmedLastName)) {

                updatedRecord.setFirstName(trimmedFirstName);
                updatedRecord.setLastName(trimmedLastName);
                dataRepository.getMedicalRecords().set(i, updatedRecord);
                dataRepository.saveData();
                return updatedRecord;
            }
        }
        return null;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        boolean removed = dataRepository.getMedicalRecords().removeIf(record ->
                record.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        record.getLastName().trim().equalsIgnoreCase(trimmedLastName));

        if (removed) {
            dataRepository.saveData();
        }
        return removed;
    }
}
