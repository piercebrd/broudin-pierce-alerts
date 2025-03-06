package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final DataService dataService;

    public FireStationService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<PersonDTO> getPersonsByStation(String stationNumber) {
        return dataService.getPeople().stream()
                .filter(person -> dataService.getFireStations().stream()
                        .anyMatch(fs -> fs.getStation().equals(stationNumber) && fs.getAddress().equals(person.getAddress())))
                .map(person -> new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .collect(Collectors.toList());
    }
}
