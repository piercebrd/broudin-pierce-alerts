package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final DataRepository dataRepository;

    public PersonService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public Person addPerson(Person person) {
        dataRepository.getPeople().add(person);
        dataRepository.saveData();
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();
        List<Person> people = dataRepository.getPeople();

        for (int i = 0; i < people.size(); i++) {
            Person existingPerson = people.get(i);
            if (existingPerson.getFirstName().equalsIgnoreCase(trimmedFirstName) &&
                    existingPerson.getLastName().equalsIgnoreCase(trimmedLastName)) {

                updatedPerson.setFirstName(trimmedFirstName);
                updatedPerson.setLastName(trimmedLastName);
                people.set(i, updatedPerson);
                dataRepository.saveData();
                return updatedPerson;
            }
        }

        return null;
    }


    public boolean deletePerson(String firstName, String lastName) {
        final String trimmedFirstName = firstName.trim();
        final String trimmedLastName = lastName.trim();


        long beforeCount = dataRepository.getPeople().stream()
                .filter(person -> person.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        person.getLastName().trim().equalsIgnoreCase(trimmedLastName))
                .count();

        boolean removed = dataRepository.getPeople().removeIf(person ->
                person.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        person.getLastName().trim().equalsIgnoreCase(trimmedLastName));


        long afterCount = dataRepository.getPeople().stream()
                .filter(person -> person.getFirstName().trim().equalsIgnoreCase(trimmedFirstName) &&
                        person.getLastName().trim().equalsIgnoreCase(trimmedLastName))
                .count();

        if (removed) {
            dataRepository.saveData();
        }
        return removed;
    }
}
