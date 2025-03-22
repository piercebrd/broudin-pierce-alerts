package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertService.class);

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    public ChildAlertService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    public List<ChildAlertResponseDTO> getChildrenAddress(String address) {
        LOGGER.info("Looking for children at address: {}", address);

        List<Person> residents = dataRepository.getPeople().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        if (residents.isEmpty()) {
            LOGGER.info("No residents found at address: {}", address);
            return Collections.emptyList();
        }

        List<ChildAlertResponseDTO> children = new ArrayList<>();

        for (Person resident : residents) {
            medicalRecordService.getMedicalRecordForPerson(resident.getFirstName(), resident.getLastName())
                    .ifPresent(medicalRecord -> {
                        int age = medicalRecordService.calculateAge(medicalRecord.getBirthdate());
                        if (age <= 18) {
                            LOGGER.info("Child found: {} {}, age {}", resident.getFirstName(), resident.getLastName(), age);

                            List<PersonDTO> householdMembers = residents.stream()
                                    .filter(person -> !person.equals(resident))
                                    .map(person -> new PersonDTO(person.getFirstName(), person.getLastName()))
                                    .collect(Collectors.toList());

                            children.add(new ChildAlertResponseDTO(resident.getFirstName(), resident.getLastName(), age, householdMembers));
                        }
                    });
        }

        LOGGER.info("Total children found at address {}: {}", address, children.size());
        return children;
    }
}
