package com.safetynet.broudin_pierce_alerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class DataRepository {
    private List<Person> people;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    public DataRepository() {
        loadData();
    }

    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new ClassPathResource("data.json").getInputStream()) {
            Map<String, List<?>> data = objectMapper.readValue(inputStream, Map.class);
            this.people = objectMapper.convertValue(data.get("persons"),objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            this.fireStations = objectMapper.convertValue(data.get("firestations"), objectMapper.getTypeFactory().constructCollectionType(List.class, FireStation.class));
            this.medicalRecords = objectMapper.convertValue(data.get("medicalrecords"), objectMapper.getTypeFactory().constructCollectionType(List.class, MedicalRecord.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> data = Map.of(
                    "persons", people,
                    "firestations", fireStations,
                    "medicalrecords", medicalRecords
            );
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/data.json"), data);
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
