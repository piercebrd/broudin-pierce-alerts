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

/**
 * Service responsible for retrieving information about children living at a given address.
 *
 * <p>Used by the <code>/childAlert</code> endpoint to return a list of children (age ≤ 18)
 * and their household members, if any.
 */

@Service
public class ChildAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertService.class);

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    /**
     * Constructs a {@code ChildAlertService} with the necessary dependencies.
     *
     * @param dataRepository        the in-memory data repository
     * @param medicalRecordService  the service used to retrieve medical records and calculate age
     */

    public ChildAlertService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Retrieves children living at the specified address along with their household members.
     *
     * @param address the address to search for children
     * @return a list of {@link ChildAlertResponseDTO} containing children and their household members,
     *         or an empty list if no children are found
     */

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

                            children.add(new ChildAlertResponseDTO(
                                    resident.getFirstName(),
                                    resident.getLastName(),
                                    age,
                                    householdMembers
                            ));
                        }
                    });
        }

        LOGGER.info("Total children found at address {}: {}", address, children.size());
        return children;
    }
}
