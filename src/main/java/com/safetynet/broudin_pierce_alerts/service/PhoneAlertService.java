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

@Service
public class PhoneAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertService.class);

    private final DataRepository dataRepository;

    public PhoneAlertService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

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
