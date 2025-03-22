package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for managing person records in the system.
 *
 * <p>This service allows for adding, updating, and deleting individuals,
 * and persists changes via the {@link DataRepository}.
 */

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    private final DataRepository dataRepository;

    /**
     * Constructs a {@code PersonService} with access to the data repository.
     *
     * @param dataRepository the in-memory data store
     */

    public PersonService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Adds a new person to the data repository.
     *
     * @param person the person to add
     * @return the added {@link Person}
     */

    public Person addPerson(Person person) {
        dataRepository.getPeople().add(person);
        dataRepository.saveData();
        LOGGER.info("Added new person: {} {}", person.getFirstName(), person.getLastName());
        return person;
    }

    /**
     * Updates an existing person's record based on their first and last name.
     *
     * @param firstName     the person's current first name
     * @param lastName      the person's current last name
     * @param updatedPerson the updated {@link Person} object
     * @return the updated person, or {@code null} if no match was found
     */

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

    /**
     * Deletes a person from the data repository based on their name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return {@code true} if the person was deleted, {@code false} if no match was found
     */

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
