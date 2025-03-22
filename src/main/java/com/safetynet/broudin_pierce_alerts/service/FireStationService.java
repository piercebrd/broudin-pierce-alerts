package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service that manages fire station data and handles operations
 * related to station-to-address assignments and resident lookups.
 *
 * <p>This service supports creation, update, and deletion of fire station mappings,
 * and provides resident coverage information based on fire station numbers.
 */

@Service
public class FireStationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationService.class);

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    /**
     * Constructs a {@code FireStationService} with the required dependencies.
     *
     * @param dataRepository        the data repository
     * @param medicalRecordService  the service used to access medical data
     */

    public FireStationService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Adds a new fire station mapping (address → station number).
     *
     * @param fireStation the fire station to add
     * @return the added fire station
     */

    public FireStation addFireStation(FireStation fireStation) {
        fireStation.setStation(fireStation.getStation().trim());
        dataRepository.getFireStations().add(fireStation);
        dataRepository.saveData();
        LOGGER.info("Added new fire station: address={}, station={}", fireStation.getAddress(), fireStation.getStation());
        return fireStation;
    }

    /**
     * Updates the station number assigned to a given address.
     *
     * @param address          the address to update
     * @param newStationNumber the new station number to assign
     * @return the updated fire station, or {@code null} if not found
     */

    public FireStation updateFireStation(String address, String newStationNumber) {
        final String trimmedAddress = address.trim();
        final String trimmedStation = newStationNumber.trim();

        LOGGER.info("Attempting to update fire station at address={} to new station={}", trimmedAddress, trimmedStation);

        Optional<FireStation> fireStationOpt = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(trimmedAddress))
                .findFirst();

        if (fireStationOpt.isPresent()) {
            FireStation fireStation = fireStationOpt.get();
            fireStation.setStation(trimmedStation);
            dataRepository.saveData();
            LOGGER.info("Successfully updated station at address={} to station={}", trimmedAddress, trimmedStation);
            return fireStation;
        }

        LOGGER.warn("No fire station found at address={} to update", trimmedAddress);
        return null;
    }

    /**
     * Deletes a fire station mapping based on address.
     *
     * @param address the address to delete the mapping for
     * @return {@code true} if the station was deleted, {@code false} if not found
     */

    public boolean deleteFireStation(String address) {
        final String normalizedAddress = address.trim().toLowerCase();

        LOGGER.info("Attempting to delete fire station at address={}", normalizedAddress);

        boolean removed = dataRepository.getFireStations().removeIf(fs ->
                fs.getAddress().trim().toLowerCase().equals(normalizedAddress));

        if (removed) {
            dataRepository.saveData();
            LOGGER.info("Successfully deleted fire station at address={}", normalizedAddress);
        } else {
            LOGGER.warn("No fire station found at address={} to delete", normalizedAddress);
        }

        return removed;
    }

    /**
     * Retrieves a list of people covered by the given fire station number,
     * including counts of adults and children.
     *
     * @param stationNumber the station number to look up
     * @return a {@link FireStationResponseDTO} containing the list of people and demographic counts
     */

    public FireStationResponseDTO getPeopleByStation(String stationNumber) {
        LOGGER.info("Fetching people covered by fire station number: {}", stationNumber);

        List<String> coveredAddresses = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<Person> peopleCovered = dataRepository.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        List<PersonDTO> personDTOList = peopleCovered.stream()
                .map(person -> new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .collect(Collectors.toList());

        int adultCount = 0;
        int childCount = 0;

        for (Person person : peopleCovered) {
            Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordForPerson(
                    person.getFirstName(), person.getLastName());
            if (medicalRecord.isPresent()) {
                int age = medicalRecordService.calculateAge(medicalRecord.get().getBirthdate());
                if (age > 18) {
                    adultCount++;
                } else {
                    childCount++;
                }
            }
        }

        LOGGER.info("Fire station {} covers {} person(s): {} adults, {} children",
                stationNumber, personDTOList.size(), adultCount, childCount);

        return new FireStationResponseDTO(personDTOList, adultCount, childCount);
    }
}
