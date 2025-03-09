package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final DataService dataService;
    private final MedicalRecordService medicalRecordService;

    public FireStationService(DataService dataService, MedicalRecordService medicalRecordService) {
        this.dataService = dataService;
        this.medicalRecordService = medicalRecordService;
    }

    public FireStationResponseDTO getPeopleByStation(String stationNumber) {
        List<String> coveredAddresses = dataService.getFireStations().stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(fs -> fs.getAddress())
                .collect(Collectors.toList());

        List<Person> peopleCovered = dataService.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        List<PersonDTO> personDTOList = peopleCovered.stream()
                .map(person -> new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .collect(Collectors.toList());

        int adultCount = 0;
        int childCount = 0;

        for (Person person : peopleCovered) {
            Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

            if (medicalRecord.isPresent()) {
                int age = medicalRecordService.calculateAge(medicalRecord.get().getBirthdate());
                if (age > 18) {
                    adultCount++;
                } else {
                    childCount++;
                }
            }
        }
        return new FireStationResponseDTO(personDTOList, adultCount, childCount);
    }
}
