package com.safetynet.broudin_pierce_alerts.service;

import com.safetynet.broudin_pierce_alerts.dto.FireStationResponseDTO;
import com.safetynet.broudin_pierce_alerts.dto.PersonDTO;
import com.safetynet.broudin_pierce_alerts.model.FireStation;
import com.safetynet.broudin_pierce_alerts.model.MedicalRecord;
import com.safetynet.broudin_pierce_alerts.model.Person;
import com.safetynet.broudin_pierce_alerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final DataRepository dataRepository;
    private final MedicalRecordService medicalRecordService;

    public FireStationService(DataRepository dataRepository, MedicalRecordService medicalRecordService) {
        this.dataRepository = dataRepository;
        this.medicalRecordService = medicalRecordService;
    }

    public FireStation addFireStation(FireStation fireStation) {
        fireStation.setStation(fireStation.getStation().trim());
        dataRepository.getFireStations().add(fireStation);
        dataRepository.saveData();
        return fireStation;
    }

    public FireStation updateFireStation(String address, String newStationNumber) {
        final String trimmedAddress = address.trim();
        final String trimmedStation = newStationNumber.trim(); // Trim \n

        Optional<FireStation> fireStationOpt = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(trimmedAddress))
                .findFirst();

        if (fireStationOpt.isPresent()) {
            FireStation fireStation = fireStationOpt.get();
            fireStation.setStation(trimmedStation); // Save without \n
            dataRepository.saveData();
            return fireStation;
        }

        return null;
    }


    public boolean deleteFireStation(String address) {
        final String normalizedAddress = address.trim().toLowerCase();

        boolean removed = dataRepository.getFireStations().removeIf(fs ->
                fs.getAddress().trim().toLowerCase().equals(normalizedAddress));

        if (removed) {
            dataRepository.saveData();
        }
        return removed;
    }

    public FireStationResponseDTO getPeopleByStation(String stationNumber) {
        List<String> coveredAddresses = dataRepository.getFireStations().stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(fs -> fs.getAddress())
                .collect(Collectors.toList());

        List<Person> peopleCovered = dataRepository.getPeople().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        List<PersonDTO> personDTOList = peopleCovered.stream()
                .map(person -> new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .collect(Collectors.toList());

        int adultCount = 0;
        int childCount = 0;

        for (Person person : peopleCovered) {
            Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordForPerson(person.getFirstName(), person.getLastName());

            if (medicalRecord.isPresent()) {
                int age = medicalRecordService.calculateAge(medicalRecord.get().getBirthdate());
                if (age > 18) {
                    adultCount++;
                } else {
                    childCount++;
                }
            }
        }
        return new FireStationResponseDTO(personDTOList, adultCount, childCount);
    }
}
