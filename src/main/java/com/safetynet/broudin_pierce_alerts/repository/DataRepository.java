package com.safetynet.broudin_pierce_alerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Repository responsible for loading and saving application data from/to a JSON file.
 *
 * <p>At application startup, the repository reads <code>data.json</code> from the classpath
 * and deserializes it into in-memory collections of {@link Person}, {@link FireStation}, and {@link MedicalRecord}.
 * It also provides persistence by writing changes back to the same file when requested.
 *
 * <p>This acts as a mock database layer.
 */

@Service
public class DataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRepository.class);

    private List<Person> people;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    /**
     * Constructs a {@code DataRepository} and loads data from <code>data.json</code>.
     */

    public DataRepository() {
        loadData();
    }

    /**
     * Loads data from <code>data.json</code> using Jackson and stores it in memory.
     */

    private void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new ClassPathResource("data.json").getInputStream()) {
            LOGGER.info("Loading data from data.json...");
            Map<String, List<?>> data = objectMapper.readValue(inputStream, Map.class);
            this.people = objectMapper.convertValue(data.get("persons"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class));
            this.fireStations = objectMapper.convertValue(data.get("firestations"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, FireStation.class));
            this.medicalRecords = objectMapper.convertValue(data.get("medicalrecords"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MedicalRecord.class));
            LOGGER.info("Data loaded successfully: {} persons, {} fire stations, {} medical records.",
                    people.size(), fireStations.size(), medicalRecords.size());
        } catch (IOException e) {
            LOGGER.error("Failed to load data from data.json: {}", e.getMessage());
            throw new RuntimeException("Error loading initial data", e);
        }
    }

    /**
     * Saves the current in-memory data back to <code>data.json</code>.
     */

    public void saveData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LOGGER.info("Saving data to data.json...");
            Map<String, Object> data = Map.of(
                    "persons", people,
                    "firestations", fireStations,
                    "medicalrecords", medicalRecords
            );
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/data.json"), data);
            LOGGER.info("Data saved successfully.");
        } catch (IOException e) {
            LOGGER.error("Failed to save data to data.json: {}", e.getMessage());
            throw new RuntimeException("Error saving data", e);
        }
    }

    /**
     * @return the list of all persons
     */

    public List<Person> getPeople() {
        return people;
    }

    /**
     * @return the list of all fire stations
     */

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    /**
     * @return the list of all medical records
     */

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}
