package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FloodService {

    private final DataService dataService;
    private final MedicalRecordService medicalRecordService;

    public FloodService(DataService dataService, MedicalRecordService medicalRecordService) {
        this.dataService = dataService;
        this.medicalRecordService = medicalRecordService;
    }

    public Map<String, List<ResidentDTO>> getFloodInfoByStations(List<String> stationNumbers) {

        Set<String> coveredAddresses = dataService.getFireStations().stream()
                .filter(fs -> stationNumbers.contains(fs.getStation()))
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        return dataService.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .collect(Collectors.groupingBy(Person::getAddress,
                        Collectors.mapping(person ->  {
                            Optional<MedicalRecord> medicalRecordOpt = medicalRecordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

                            int age = medicalRecordOpt.map(mr -> medicalRecordService.calculateAge(mr.getBirthdate())).orElse(-1);
                            List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                            List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                            return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, allergies, medications);
                        }, Collectors.toList())));
    }
}
