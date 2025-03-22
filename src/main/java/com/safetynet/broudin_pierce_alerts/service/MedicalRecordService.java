package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing medical records and calculating age based on birthdate.
 *
 * <p>This service provides functionality to add, update, delete, and retrieve medical records,
 * and to compute a person's age for use in other services such as emergency and risk evaluations.
 */

@Service
public class MedicalRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordService.class);

    private final DataRepository dataRepository;

    /**
     * Constructs a {@code MedicalRecordService} with the provided data repository.
     *
     * @param dataRepository the in-memory data repository
     */

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Retrieves a medical record for the person matching the given name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return an {@link Optional} containing the medical record if found, or empty if not found
     */

    public Optional<MedicalRecord> getMedicalRecordForPerson(String firstName, String lastName) {
        LOGGER.info("Looking up medical record for: {} {}", firstName, lastName);

        List<MedicalRecord> records = dataRepository.getMedicalRecords();

        Optional<MedicalRecord> result = records.stream()
                .filter(record -> record.getFirstName().trim().equalsIgnoreCase(firstName.trim()) &&
                        record.getLastName().trim().equalsIgnoreCase(lastName.trim()))
                .findFirst();

        if (result.isPresent()) {
            LOGGER.info("Medical record found for: {} {}", firstName, lastName);
        } else {
            LOGGER.warn("No medical record found for: {} {}", firstName, lastName);
        }

        return result;
    }

    /**
     * Calculates a person's age based on their birthdate string.
     *
     * @param birthdate the birthdate string in <code>MM/dd/yyyy</code> format
     * @return the calculated age in years, or 18 if the date is invalid
     */

    public int calculateAge(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            LOGGER.debug("Calculated age for birthdate {}: {}", birthdate, age);
            return age;
        } catch (DateTimeParseException e) {
            LOGGER.error("Error parsing birthdate '{}': {}", birthdate, e.getMessage());
            return 18; // Default fallback age
        }
    }

    /**
     * Adds a new medical record to the data repository.
     *
     * @param medicalRecord the medical record to add
     * @return the added medical record
     */

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        dataRepository.getMedicalRecords().add(medicalRecord);
        dataRepository.saveData();
        LOGGER.info("Added new medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecord;
    }

    /**
     * Updates an existing medical record for the specified person.
     *
     * @param firstName     the person's first name
     * @param lastName      the person's last name
     * @param updatedRecord the updated medical record to replace the existing one
     * @return the updated medical record, or {@code null} if not found
     */

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        LOGGER.info("Updating medical record for: {} {}", firstName, lastName);

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
                LOGGER.info("Successfully updated medical record for: {} {}", firstName, lastName);
                return updatedRecord;
            }
        }

        LOGGER.warn("Update failed - no record found for: {} {}", firstName, lastName);
        return null;
    }

    /**
     * Deletes a medical record for the specified person.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return {@code true} if the record was deleted, {@code false} if not found
     */

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        LOGGER.info("Attempting to delete medical record for: {} {}", firstName, lastName);

        boolean removed = dataRepository.getMedicalRecords().removeIf(record ->
                record.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        record.getLastName().trim().equalsIgnoreCase(trimmedLastName));

        if (removed) {
            dataRepository.saveData();
            LOGGER.info("Successfully deleted medical record for: {} {}", firstName, lastName);
        } else {
            LOGGER.warn("Delete failed - no record found for: {} {}", firstName, lastName);
        }

        return removed;
    }
}
