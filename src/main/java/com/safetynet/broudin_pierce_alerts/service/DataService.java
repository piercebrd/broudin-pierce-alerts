package com.safetynet.broudin_pierce_alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    private List<Person> people;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    public DataService() {
        loadData();
    }

    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new ClassPathResource("data.json").getInputStream()) {
            Map<String, List<?>> data = objectMapper.readValue(inputStream, Map.class);
            this.people = objectMapper.convertValue(data.get("persons"),objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            this.fireStations = objectMapper.convertValue(data.get("firestations"), objectMapper.getTypeFactory().constructCollectionType(List.class, FireStation.class));
            this.medicalRecords = objectMapper.convertValue(data.get("medicalRecords"), objectMapper.getTypeFactory().constructCollectionType(List.class, MedicalRecord.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}
