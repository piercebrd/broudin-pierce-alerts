package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhoneAlertService {

    private final DataService dataService;

    public PhoneAlertService(DataService dataService) {
        this.dataService = dataService;
    }

    public Set<String> getPhoneNumberByFireStation(String fireStationNumber) {
        List<String> coveredAddresses = dataService.getFireStations().stream()
                .filter(fs -> fs.getStation().equals(fireStationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        return dataService.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .filter(phone -> phone != null && !phone.isEmpty())
                .collect(Collectors.toSet());
    }
}
