package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneAlertServiceTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setUp() {
        when(dataRepository.getFireStations()).thenReturn(Arrays.asList(
                new FireStation("1509 Culver St", "3"),
                new FireStation("456 Oak St", "2")
        ));

        when(dataRepository.getPeople()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "john.doe@email.com", "Culver", "12345", "555-1234", "1509 Culver St"),
                new Person("Jane", "Smith", "jane.smith@email.com", "Culver", "12345", "555-5678", "1509 Culver St"),
                new Person("Mike", "Brown", "mike.brown@email.com", "Culver", "12345", "", "456 Oak St"),
                new Person("Anna", "White", "anna.white@email.com", "Culver", "12345", null, "789 Pine St")
        ));
    }

    @Test
    void getPhoneNumberByFireStation_ShouldReturnPhones_WhenStationHasPeople() {
        Set<String> phoneNumbers = phoneAlertService.getPhoneNumberByFireStation("3");

        assertNotNull(phoneNumbers);
        assertEquals(2, phoneNumbers.size());
        assertTrue(phoneNumbers.contains("555-1234"));
        assertTrue(phoneNumbers.contains("555-5678"));
    }

    @Test
    void getPhoneNumberByFireStation_ShouldReturnEmptySet_WhenNoPeopleAssigned() {
        Set<String> phoneNumbers = phoneAlertService.getPhoneNumberByFireStation("99");

        assertNotNull(phoneNumbers);
        assertTrue(phoneNumbers.isEmpty());
    }

    @Test
    void getPhoneNumberByFireStation_ShouldNotIncludeNullOrEmptyNumbers() {
        Set<String> phoneNumbers = phoneAlertService.getPhoneNumberByFireStation("2");

        assertFalse(phoneNumbers.contains(""));
        assertFalse(phoneNumbers.contains(null));
    }
}
