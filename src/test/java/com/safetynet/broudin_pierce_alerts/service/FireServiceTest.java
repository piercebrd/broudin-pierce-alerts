package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireResponseDTO;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FireService fireService;

    private Person testPerson;
    private FireStation testFireStation;
    private MedicalRecord testMedicalRecord;

    @BeforeEach
    void setUp() {
        testPerson = new Person("John", "Doe", "johndoe@email.com", "Culver", "12345", "555-1234","1509 Culver St" );
        testFireStation = new FireStation("1509 Culver St", "3");
        testMedicalRecord = new MedicalRecord("John", "Doe", "03/06/1983", Arrays.asList("Med1", "Med2"), Arrays.asList("Allergy1"));

        lenient().when(dataRepository.getPeople()).thenReturn(Collections.singletonList(testPerson));
        lenient().when(dataRepository.getFireStations()).thenReturn(Collections.singletonList(testFireStation));
        lenient().when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.of(testMedicalRecord));
        lenient().when(medicalRecordService.calculateAge("03/06/1983")).thenReturn(41);
    }

    @Test
    void getInfoByAddress_ShouldReturnResidentsAndFireStation() {
        FireResponseDTO response = fireService.getInfoByAddress("1509 Culver St");
        assertNotNull(response);
        assertEquals("3", response.getFireStationNumber());
        assertEquals(1, response.getResidents().size());

        ResidentDTO resident = response.getResidents().get(0);
        assertEquals("John", resident.getFirstName());
        assertEquals(41, resident.getAge());
        assertEquals("555-1234", resident.getPhone());
        assertTrue(resident.getMedications().contains("Med1"));
        assertTrue(resident.getAllergies().contains("Allergy1"));


    }

    @Test
    void getInfoByAddress_InvalidAddress_ShouldReturnEmptyResponse() {
        FireResponseDTO response = fireService.getInfoByAddress("NonExistent Address");

        assertNotNull(response);
        assertNull(response.getFireStationNumber());
        assertTrue(response.getResidents().isEmpty());
    }

    @Test
    void getInfoByAddress_NoMedicalRecord_ShouldReturnResidentWithDefaultAge() {
        when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.empty());

        FireResponseDTO response = fireService.getInfoByAddress("1509 Culver St");

        assertNotNull(response);
        assertEquals("3", response.getFireStationNumber());
        assertEquals(1, response.getResidents().size());

        ResidentDTO resident = response.getResidents().get(0);
        assertEquals("John", resident.getFirstName());
        assertEquals(-1, resident.getAge());
        assertTrue(resident.getMedications().isEmpty());
        assertTrue(resident.getAllergies().isEmpty());
    }
}
