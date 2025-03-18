package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.PersonInfoDTO;
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
class PersonInfoServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private PersonInfoService personInfoService;

    @BeforeEach
    void setUp() {
        lenient().when(dataRepository.getPeople()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "john.doe@email.com", "Culver", "12345", "555-1234", "123 Main St"),
                new Person("Jane", "Doe", "jane.doe@email.com", "Culver", "12345", "555-5678", "456 Oak St"),
                new Person("Mike", "Brown", "mike.brown@email.com", "Culver", "12345", "555-7890", "mike.brown@email.com")
        ));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.of(
                new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"))));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("Jane", "Doe")).thenReturn(Optional.of(
                new MedicalRecord("Jane", "Doe", "03/15/1990", Arrays.asList("Ibuprofen"), Arrays.asList("Pollen"))));

        lenient().when(medicalRecordService.calculateAge("01/01/1980")).thenReturn(44); // Adjust based on current year
        lenient().when(medicalRecordService.calculateAge("03/15/1990")).thenReturn(34);
    }

    @Test
    void getPersonInfoByLastName_ShouldReturnPersonInfo() {
        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName("Doe");

        assertNotNull(result);
        assertEquals(2, result.size());

        PersonInfoDTO johnInfo = result.get(0);
        assertEquals("John", johnInfo.getFirstName());
        assertEquals(44, johnInfo.getAge());
        assertTrue(johnInfo.getMedications().contains("Aspirin"));
        assertTrue(johnInfo.getAllergies().contains("Peanuts"));

        PersonInfoDTO janeInfo = result.get(1);
        assertEquals("Jane", janeInfo.getFirstName());
        assertEquals(34, janeInfo.getAge());
        assertTrue(janeInfo.getMedications().contains("Ibuprofen"));
    }

    @Test
    void getPersonInfoByLastName_ShouldReturnEmptyList_WhenNoMatch() {
        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName("Smith");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getPersonInfoByLastName_ShouldReturnDefaultAge_WhenNoMedicalRecord() {
        when(medicalRecordService.getMedicalRecordForPerson("Mike", "Brown")).thenReturn(Optional.empty());

        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName("Brown");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mike", result.get(0).getFirstName());
        assertEquals(-1, result.get(0).getAge()); // Default when medical record is missing
    }
}
