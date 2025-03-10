package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonInfoService {

    private final DataService dataService;
    private final MedicalRecordService medicalRecordService;

    public PersonInfoService(DataService dataService, MedicalRecordService medicalRecordService) {
        this.dataService = dataService;
        this.medicalRecordService = medicalRecordService;
    }

    public List<PersonInfoDTO> getPersonInfoByLastName(String lastName) {
        return dataService.getPeople().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {
                    // Retrieve medical record
                    Optional<MedicalRecord> medicalRecordOpt = medicalRecordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

                    // Extract age & medical details
                    int age = medicalRecordOpt.map(mr -> medicalRecordService.calculateAge(mr.getBirthdate())).orElse(-1);
                    List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(Collections.emptyList());
                    List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(Collections.emptyList());

                    return new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), age, person.getEmail(), medications, allergies);
                })
                .collect(Collectors.toList());
    }
}
