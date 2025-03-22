package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsible for retrieving detailed personal and medical information
 * for individuals based on their last name.
 *
 * <p>Used by the <code>/personInfo</code> endpoint to return enriched identity data
 * such as address, age, email, medications, and allergies.
 */

@Service
public class PersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoService.class);

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    /**
     * Constructs a {@code PersonInfoService} with required dependencies.
     *
     * @param dataRepository        the in-memory data source
     * @param medicalRecordService  the service used to retrieve medical records
     */

    public PersonInfoService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Retrieves personal and medical information for all individuals with the given last name.
     *
     * @param lastName the last name to search for
     * @return a list of {@link PersonInfoDTO} with matching personal and medical data,
     *         or an empty list if no match is found
     */

    public List<PersonInfoDTO> getPersonInfoByLastName(String lastName) {
        LOGGER.info("Fetching person info for lastName: {}", lastName);

        List<PersonInfoDTO> personInfoList = dataRepository.getPeople().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {
                    Optional<MedicalRecord> medicalRecordOpt = medicalRecordService.getMedicalRecordForPerson(
                            person.getFirstName(), person.getLastName());

                    int age = medicalRecordOpt.map(mr -> medicalRecordService.calculateAge(mr.getBirthdate())).orElse(-1);
                    List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                    List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                    return new PersonInfoDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            age,
                            person.getEmail(),
                            medications,
                            allergies
                    );
                })
                .collect(Collectors.toList());

        if (personInfoList.isEmpty()) {
            LOGGER.warn("No person info found for lastName: {}", lastName);
        } else {
            LOGGER.info("Found {} person(s) for lastName: {}", personInfoList.size(), lastName);
        }

        return personInfoList;
    }
}
