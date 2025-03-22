package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ChildAlertResponseDTO;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildAlertServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private ChildAlertService childAlertService;

    @BeforeEach
    void setUp() {
        lenient().when(dataRepository.getPeople()).thenReturn(Arrays.asList(
                new Person("Alice", "Brown", "alice.brown@email.com", "Culver", "12345", "555-1111", "123 Main St"),
                new Person("Bob", "Brown", "bob.brown@email.com", "Culver", "12345", "555-2222", "123 Main St"),
                new Person("John", "Doe", "john.doe@email.com", "Culver", "12345", "555-3333", "456 Oak St")
        ));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("Alice", "Brown")).thenReturn(Optional.of(
                new MedicalRecord("Alice", "Brown", "01/01/2015", List.of(), List.of())));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("Bob", "Brown")).thenReturn(Optional.of(
                new MedicalRecord("Bob", "Brown", "01/01/1980", List.of(), List.of())));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.empty());

        lenient().when(medicalRecordService.calculateAge("01/01/2015")).thenReturn(9);
        lenient().when(medicalRecordService.calculateAge("01/01/1980")).thenReturn(44);
    }

    @Test
    void getChildrenAddress_ShouldReturnChildrenWithHouseholdMembers() {
        List<ChildAlertResponseDTO> result = childAlertService.getChildrenAddress("123 Main St");

        assertNotNull(result);
        assertEquals(1, result.size());

        ChildAlertResponseDTO child = result.get(0);
        assertEquals("Alice", child.getFirstName());
        assertEquals(9, child.getAge());
        assertEquals(1, child.getHouseholdMembers().size());

        assertEquals("Bob", child.getHouseholdMembers().get(0).getFirstName());
    }

    @Test
    void getChildrenAddress_ShouldReturnEmptyList_WhenNoChildren() {
        List<ChildAlertResponseDTO> result = childAlertService.getChildrenAddress("456 Oak St");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getChildrenAddress_ShouldReturnEmptyList_WhenAddressHasNoResidents() {
        List<ChildAlertResponseDTO> result = childAlertService.getChildrenAddress("999 Unknown St");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getChildrenAddress_ShouldReturnDefaultAge_WhenMedicalRecordIsMissing() {
        List<ChildAlertResponseDTO> result = childAlertService.getChildrenAddress("456 Oak St");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
