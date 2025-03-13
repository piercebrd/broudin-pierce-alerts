package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private DataRepository dataRepository;

    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", "01/01/2000", List.of("Aspirin"), List.of("Peanuts")));

        lenient().when(dataRepository.getMedicalRecords()).thenReturn(medicalRecords);

        medicalRecordService = new MedicalRecordService(dataRepository);
    }

    @Test
    void getMedicalRecordForPerson_ShouldReturnMedicalRecord_WhenFound() {

        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordForPerson("John", "Doe");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Aspirin", result.get().getMedications().get(0));
    }

    @Test
    void getMedicalRecordForPerson_ShouldReturnEmpty_WhenNotFound() {
        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordForPerson("Jane", "Doe");

        assertFalse(result.isPresent());
    }

    @Test
    void calculateAge_ShouldReturnCorrectAge() {
        int age = medicalRecordService.calculateAge("01/01/2000");
        assertTrue(age > 20);
    }

    @Test
    void calculateAge_ShouldReturnDefault_WhenInvalidDate() {
        int age = medicalRecordService.calculateAge("invalid-date");

        assertEquals(18, age);
    }
}
