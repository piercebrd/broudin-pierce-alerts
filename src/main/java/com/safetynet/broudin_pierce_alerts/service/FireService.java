package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
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
 * Service that retrieves fire emergency information for a specific address.
 *
 * <p>Used by the <code>/fire</code> endpoint to return the fire station number assigned to the address,
 * along with residents' personal and medical details (age, medications, allergies).
 */

@Service
public class FireService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireService.class);

    private final DataRepository dataRepository;
    private final MedicalRecordService recordService;

    /**
     * Constructs a {@code FireService} with access to the data repository and medical records.
     *
     * @param dataRepository the in-memory data store for persons and fire stations
     * @param recordService  the service used to retrieve and calculate medical data
     */

    public FireService(DataRepository dataRepository, MedicalRecordService recordService) {
        this.dataRepository = dataRepository;
        this.recordService = recordService;
    }

    /**
     * Retrieves fire station number and detailed resident information for the given address.
     *
     * @param address the address to look up
     * @return a {@link FireResponseDTO} containing the fire station number and a list of residents,
     *         or an empty list if no residents are found
     */

    public FireResponseDTO getInfoByAddress(String address) {
        LOGGER.info("Fetching fire alert info for address: {}", address);

        String fireStationNumber = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse(null);

        if (fireStationNumber == null) {
            LOGGER.warn("No fire station assigned to address: {}", address);
        } else {
            LOGGER.info("Address {} is covered by fire station: {}", address, fireStationNumber);
        }

        List<ResidentDTO> residents = dataRepository.getPeople().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> {
                    Optional<MedicalRecord> medicalRecordOpt = recordService.getMedicalRecordForPerson(
                            person.getFirstName(), person.getLastName());

                    int age = medicalRecordOpt.map(mr -> recordService.calculateAge(mr.getBirthdate())).orElse(-1);
                    List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                    List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                    LOGGER.debug("Resident: {} {}, age {}, meds={}, allergies={}",
                            person.getFirstName(), person.getLastName(), age, medications, allergies);

                    return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
                })
                .collect(Collectors.toList());

        LOGGER.info("Found {} resident(s) at address: {}", residents.size(), address);

        return new FireResponseDTO(fireStationNumber, residents);
    }
}
