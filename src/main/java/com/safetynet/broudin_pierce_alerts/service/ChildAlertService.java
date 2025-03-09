package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {

    private final DataService dataService;
    private final MedicalRecordService medicalRecordService;

    public ChildAlertService(DataService dataService, MedicalRecordService medicalRecordService) {
        this.dataService = dataService;
        this.medicalRecordService = medicalRecordService;
    }

    public List<ChildAlertResponseDTO> getChildrenAddress(String address) {
        List<Person> residents = dataService.getPeople().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        if (residents.isEmpty()) {
            return Collections.emptyList();
        }

        List<ChildAlertResponseDTO> children = new ArrayList<>();

        for (Person resident : residents) {
            medicalRecordService.getMedicalRecordForPerson(resident.getFirstName(), resident.getLastName())
                    .ifPresent(medicalRecord -> {
                        int age = medicalRecordService.calculateAge(medicalRecord.getBirthdate());
                        if (age <= 18) {
                            List<PersonDTO> householdMembers = residents.stream()
                                    .filter(person -> !person.equals(resident))
                                    .map(person -> new PersonDTO(person.getFirstName(), person.getLastName()))
                                    .collect(Collectors.toList());

                            children.add(new ChildAlertResponseDTO(resident.getFirstName(), resident.getLastName(), age, householdMembers));
                        }
                    });
        }
        return children;
    }
}
