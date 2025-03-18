package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FloodService {

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    public FloodService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    public Map<String, List<ResidentDTO>> getFloodInfoByStations(List<String> stationNumbers) {

        Set<String> coveredAddresses = dataRepository.getFireStations().stream()
                .filter(fs -> stationNumbers.contains(fs.getStation()))
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        return dataRepository.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .collect(Collectors.groupingBy(Person::getAddress,
                        Collectors.mapping(person ->  {
                            Optional<MedicalRecord> medicalRecordOpt = medicalRecordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

                            int age = medicalRecordOpt.map(mr -> medicalRecordService.calculateAge(mr.getBirthdate())).orElse(-1);
                            List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                            List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                            return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
                        }, Collectors.toList())));
    }
}
