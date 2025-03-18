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

    @Test
    void addMedicalRecord_ShouldAddRecord() {
        MedicalRecord newRecord = new MedicalRecord("Alice", "Smith", "02/15/1995", List.of("Ibuprofen"), List.of("None"));

        medicalRecordService.addMedicalRecord(newRecord);

        verify(dataRepository, times(1)).getMedicalRecords();
        assertTrue(medicalRecords.contains(newRecord));
    }

    @Test
    void updateMedicalRecord_ShouldUpdateExistingRecord() {
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/2000", List.of("Paracetamol"), List.of("Peanuts"));

        MedicalRecord result = medicalRecordService.updateMedicalRecord("John", "Doe", updatedRecord);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getMedications().get(0));
    }

    @Test
    void updateMedicalRecord_ShouldReturnNull_WhenRecordNotFound() {
        MedicalRecord result = medicalRecordService.updateMedicalRecord("Unknown", "Person", new MedicalRecord("Unknown", "Person", "01/01/1990", List.of(), List.of()));

        assertNull(result);
    }

    @Test
    void deleteMedicalRecord_ShouldRemoveRecord() {
        boolean result = medicalRecordService.deleteMedicalRecord("John", "Doe");

        assertTrue(result);
        assertFalse(medicalRecords.stream().anyMatch(mr -> mr.getFirstName().equals("John") && mr.getLastName().equals("Doe")));
    }

    @Test
    void deleteMedicalRecord_ShouldReturnFalse_WhenNotFound() {
        boolean result = medicalRecordService.deleteMedicalRecord("Unknown", "Person");

        assertFalse(result);
    }
}
