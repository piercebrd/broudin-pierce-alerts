package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityEmailServiceTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @BeforeEach
    void setUp() {
        when(dataRepository.getPeople()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "john.doe@email.com", "Culver", "12345", "555-1234", "123 Main St"),
                new Person("Jane", "Smith", "jane.smith@email.com", "Culver", "12345", "555-5678", "456 Oak St"),
                new Person("Mike", "Brown", "mike.brown@email.com", "Culver", "12345", "555-7890", "789 Pine St"),
                new Person("Anna", "White", null, "Springfield", "67890", "555-1111", "111 Maple St"),
                new Person("Tom", "Black", "", "Springfield", "67890", "555-2222", "222 Birch St")
        ));
    }

    @Test
    void getEmailsByCity_ShouldReturnEmails_WhenCityHasPeople() {
        Set<String> emails = communityEmailService.getEmailsByCity("Culver");

        assertNotNull(emails);
        assertEquals(3, emails.size());
        assertTrue(emails.contains("john.doe@email.com"));
        assertTrue(emails.contains("jane.smith@email.com"));
        assertTrue(emails.contains("mike.brown@email.com"));
    }

    @Test
    void getEmailsByCity_ShouldReturnEmptySet_WhenNoEmailsExist() {
        Set<String> emails = communityEmailService.getEmailsByCity("Springfield");

        assertNotNull(emails);
        assertTrue(emails.isEmpty());
    }

    @Test
    void getEmailsByCity_ShouldReturnEmptySet_WhenCityNotFound() {
        Set<String> emails = communityEmailService.getEmailsByCity("UnknownCity");

        assertNotNull(emails);
        assertTrue(emails.isEmpty());
    }

    @Test
    void getEmailsByCity_ShouldNotIncludeNullOrEmptyEmails() {
        Set<String> emails = communityEmailService.getEmailsByCity("Springfield");

        assertFalse(emails.contains(null));
        assertFalse(emails.contains(""));
    }
}
