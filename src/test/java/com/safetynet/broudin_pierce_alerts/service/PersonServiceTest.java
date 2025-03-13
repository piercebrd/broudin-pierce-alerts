package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private DataRepository dataRepository;

    private List<Person> people;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        people = new ArrayList<>();
        people.add(new Person("John","Doe","john@example.com","City","1262","5555-555", "123 Street" ));
        when(dataRepository.getPeople()).thenReturn(people);
    }

    @Test
    void addPerson_ShouldAddPersonSuccessfully() {
        Person newPerson = new Person("Jane", "Doe", "jane@example.com","City","1262","5555-554", "124 Street" );

        Person addedPerson = personService.addPerson(newPerson);

        assertNotNull(addedPerson);
        assertEquals("Jane", addedPerson.getFirstName());
        Mockito.verify(dataRepository, times(1)).getPeople();

    }

    @Test
    void updatePerson_ShouldUpdateExistingPerson() {
        Person updatedPerson = new Person("John", "Doe", "johnny@example.com", "City", "54321", "555-9999", "789 Road");

        Person result = personService.updatePerson("John", "Doe", updatedPerson);

        assertNotNull(result);
        assertEquals("789 Road", result.getAddress());
        assertEquals("johnny@example.com", result.getEmail());
    }

    @Test
    void deletePerson_ShouldRemovePerson() {
        boolean result = personService.deletePerson("John", "Doe");

        assertTrue(result);
        Mockito.verify(dataRepository, atLeastOnce()).getPeople();
    }
}
