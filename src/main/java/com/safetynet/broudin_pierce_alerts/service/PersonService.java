package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    private final DataRepository dataRepository;

    public PersonService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public Person addPerson(Person person) {
        dataRepository.getPeople().add(person);
        dataRepository.saveData();
        LOGGER.info("Added new person: {} {}", person.getFirstName(), person.getLastName());
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();
        List<Person> people = dataRepository.getPeople();

        LOGGER.info("Attempting to update person: {} {}", trimmedFirstName, trimmedLastName);

        for (int i = 0; i < people.size(); i++) {
            Person existingPerson = people.get(i);
            if (existingPerson.getFirstName().equalsIgnoreCase(trimmedFirstName) &&
                    existingPerson.getLastName().equalsIgnoreCase(trimmedLastName)) {

                updatedPerson.setFirstName(trimmedFirstName);
                updatedPerson.setLastName(trimmedLastName);
                people.set(i, updatedPerson);
                dataRepository.saveData();
                LOGGER.info("Successfully updated person: {} {}", trimmedFirstName, trimmedLastName);
                return updatedPerson;
            }
        }

        LOGGER.warn("Update failed - person not found: {} {}", trimmedFirstName, trimmedLastName);
        return null;
    }

    public boolean deletePerson(String firstName, String lastName) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();

        LOGGER.info("Attempting to delete person: {} {}", trimmedFirstName, trimmedLastName);

        boolean removed = dataRepository.getPeople().removeIf(person ->
                person.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        person.getLastName().trim().equalsIgnoreCase(trimmedLastName));

        if (removed) {
            dataRepository.saveData();
            LOGGER.info("Successfully deleted person: {} {}", trimmedFirstName, trimmedLastName);
        } else {
            LOGGER.warn("Delete failed - person not found: {} {}", trimmedFirstName, trimmedLastName);
        }

        return removed;
    }
}
