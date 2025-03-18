package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.ResidentDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FloodServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FloodService floodService;

    @BeforeEach
    void setUp() {
        lenient().when(dataRepository.getFireStations()).thenReturn(Arrays.asList(
                new FireStation("1509 Culver St", "3"),
                new FireStation("456 Oak St", "2")
        ));

        lenient().when(dataRepository.getPeople()).thenReturn(Arrays.asList(
                new Person("John", "Doe", "john.doe@email.com", "Culver", "12345", "555-1234", "1509 Culver St"),
                new Person("Jane", "Smith", "jane.smith@email.com", "Culver", "12345", "555-5678", "1509 Culver St"),
                new Person("Mike", "Brown", "mike.brown@email.com", "Culver", "12345", "555-7890", "456 Oak St")
        ));

        lenient().when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.of(
                new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"))));
        lenient().when(medicalRecordService.getMedicalRecordForPerson("Jane", "Smith")).thenReturn(Optional.of(
                new MedicalRecord("Jane", "Smith", "03/15/1990", Arrays.asList("Ibuprofen"), Arrays.asList("Pollen"))));
        lenient().when(medicalRecordService.getMedicalRecordForPerson("Mike", "Brown")).thenReturn(Optional.empty());

        lenient().when(medicalRecordService.calculateAge("01/01/1980")).thenReturn(44);
        lenient().when(medicalRecordService.calculateAge("03/15/1990")).thenReturn(34);
    }

    @Test
    void getFloodInfoByStations_ShouldReturnResidentInfo() {
        Map<String, List<ResidentDTO>> result = floodService.getFloodInfoByStations(Arrays.asList("3"));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("1509 Culver St"));

        List<ResidentDTO> residents = result.get("1509 Culver St");
        assertEquals(2, residents.size());

        ResidentDTO johnInfo = residents.get(0);
        assertEquals("John", johnInfo.getFirstName());
        assertEquals(44, johnInfo.getAge());
        System.out.println("medications : " + johnInfo.getMedications());
        assertTrue(johnInfo.getMedications().contains("Aspirin"));
        assertTrue(johnInfo.getAllergies().contains("Peanuts"));

        ResidentDTO janeInfo = residents.get(1);
        assertEquals("Jane", janeInfo.getFirstName());
        assertEquals(34, janeInfo.getAge());
        assertTrue(janeInfo.getMedications().contains("Ibuprofen"));
    }

    @Test
    void getFloodInfoByStations_ShouldReturnEmptyMap_WhenNoResidents() {
        Map<String, List<ResidentDTO>> result = floodService.getFloodInfoByStations(Arrays.asList("99"));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getFloodInfoByStations_ShouldReturnDefaultAge_WhenNoMedicalRecord() {
        Map<String, List<ResidentDTO>> result = floodService.getFloodInfoByStations(Arrays.asList("2"));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("456 Oak St"));

        ResidentDTO mikeInfo = result.get("456 Oak St").get(0);
        assertEquals("Mike", mikeInfo.getFirstName());
        assertEquals(-1, mikeInfo.getAge()); // Default when medical record is missing
    }
}
