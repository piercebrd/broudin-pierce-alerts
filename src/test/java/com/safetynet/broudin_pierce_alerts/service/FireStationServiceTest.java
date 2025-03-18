package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
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
public class FireStationServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FireStationService fireStationService;

    private FireStation testStation;
    private Person testPerson;
    private MedicalRecord testMedicalRecord;

    @BeforeEach
    void setUp() {
        testStation = new FireStation("1509 Culver St", "3");
        testPerson = new Person("John", "Doe", "johndoe@email.com", "Culver", "12345", "555-1234", "1509 Culver St");
        testMedicalRecord = new MedicalRecord("John", "Doe", "03/06/1983", Arrays.asList("Med1"), Arrays.asList("Allergy1"));

        when(dataRepository.getFireStations()).thenReturn(new ArrayList<>(List.of(testStation)));
        lenient().when(dataRepository.getPeople()).thenReturn(Collections.singletonList(testPerson));
        lenient().when(medicalRecordService.getMedicalRecordForPerson("John", "Doe")).thenReturn(Optional.of(testMedicalRecord));
        lenient().when(medicalRecordService.calculateAge("03/06/1983")).thenReturn(41);
    }

    @Test
    void getPeopleByStation_ValidStation_ShouldReturnPeople() {
        FireStationResponseDTO response = fireStationService.getPeopleByStation("3");

        assertNotNull(response);
        assertEquals(1, response.getPeople().size());
        assertEquals("John", response.getPeople().get(0).getFirstName());
        assertEquals(1, response.getAdultCount());
        assertEquals(0, response.getChildrenCount());
    }

    @Test
    void getPeopleByStation_InvalidStation_ShouldReturnEmptyResponse() {
        FireStationResponseDTO response = fireStationService.getPeopleByStation("99");

        assertNotNull(response);
        assertTrue(response.getPeople().isEmpty());
        assertEquals(0, response.getAdultCount());
        assertEquals(0, response.getChildrenCount());
    }

    @Test
    void getPeopleByStation_ChildPresent_ShouldCountCorrectly() {
        when(medicalRecordService.calculateAge("03/06/2015")).thenReturn(9); // Mock child age
        when(medicalRecordService.getMedicalRecordForPerson("John", "Doe"))
                .thenReturn(Optional.of(new MedicalRecord("John", "Doe", "03/06/2015", Arrays.asList(), Arrays.asList())));

        FireStationResponseDTO response = fireStationService.getPeopleByStation("3");

        assertEquals(0, response.getAdultCount());
        assertEquals(1, response.getChildrenCount());
    }

    @Test
    void addFireStation_ShouldAddStation() {
        FireStation newStation = new FireStation("29 Baker St", "4");

        FireStation result = fireStationService.addFireStation(newStation);

        assertNotNull(result);
        assertEquals("29 Baker St", result.getAddress());
        assertEquals("4", result.getStation());
        verify(dataRepository, times(1)).getFireStations();
    }

    @Test
    void updateFireStation_ShouldUpdateExistingStation() {
        FireStation updatedStation = new FireStation("1509 Culver St", "5");

        FireStation result = fireStationService.updateFireStation("1509 Culver St", "5");

        assertNotNull(result);
        assertEquals("1509 Culver St", result.getAddress());
        assertEquals("5", result.getStation());
    }

    @Test
    void updateFireStation_NonExistent_ShouldReturnNull() {
        FireStation result = fireStationService.updateFireStation("Unknown Address", "5");

        assertNull(result);
    }

    @Test
    void deleteFireStation_NonExistent_ShouldReturnFalse() {
        boolean result = fireStationService.deleteFireStation("Unknown Address");

        assertFalse(result);
    }
}

