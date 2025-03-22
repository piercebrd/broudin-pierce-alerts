package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that provides phone numbers of residents based on fire station coverage.
 *
 * <p>Used by the <code>/phoneAlert</code> endpoint to notify residents of emergencies
 * by retrieving their phone numbers via fire station number.
 */

@Service
public class PhoneAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertService.class);

    private final DataRepository dataRepository;

    /**
     * Constructs a {@code PhoneAlertService} with the data repository.
     *
     * @param dataRepository the repository containing fire station and person data
     */

    public PhoneAlertService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Retrieves phone numbers of all people served by the specified fire station.
     *
     * @param fireStationNumber the fire station number to look up
     * @return a set of unique phone numbers of covered residents
     */

    public Set<String> getPhoneNumberByFireStation(String fireStationNumber) {
        LOGGER.info("Fetching phone numbers for fire station number: {}", fireStationNumber);

        List<String> coveredAddresses = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getStation().equals(fireStationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        LOGGER.info("Fire station {} covers {} address(es): {}", fireStationNumber, coveredAddresses.size(), coveredAddresses);

        Set<String> phoneNumbers = dataRepository.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .filter(phone -> phone != null && !phone.isEmpty())
                .collect(Collectors.toSet());

        LOGGER.info("Found {} unique phone number(s) for fire station {}", phoneNumbers.size(), fireStationNumber);

        return phoneNumbers;
    }
}
